package com.github.onlynight.chatim.server.gate;

import com.github.onlynight.chatim.server.data.external.External;
import com.github.onlynight.chatim.server.data.internal.Internal;
import com.github.onlynight.chatim.server.gate.connection.AuthConnectionHandler;
import com.github.onlynight.chatim.server.gate.connection.ClientConnections;
import com.github.onlynight.chatim.server.gate.connection.LogicConnectionHandler;
import com.google.protobuf.Message;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

import java.util.concurrent.ConcurrentHashMap;

public class GateServerHandler extends ChannelHandlerAdapter {

    private Logger logger = Logger.getLogger(GateServerHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ClientConnections.addConnection(ctx);
        logger.info("client num is : " + ClientConnections.connectionLength());
        ctx.writeAndFlush('a');
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ClientConnections.removeConnection(ctx);
        logger.info("client num is : " + ClientConnections.connectionLength());

        String userId = ctx.attr(ClientConnections.USER_ID).get();
        if (userId != null) {
            ClientConnections.unbindUser2Connection(userId);
            ClientConnections.removeConnection(ctx);

            Internal.ILogout iLogout = Internal.ILogout.newBuilder()
                    .setFrom(Internal.ServerType.GATE)
                    .setTo(Internal.ServerType.LOGIC)
                    .setUserId(userId)
                    .setConnectionId(ctx.attr(ClientConnections.CONNECTION_ID).get())
                    .build();

            AuthConnectionHandler.getInstance()
                    .getChannelHandlerContext().writeAndFlush(iLogout);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message) msg;
        if (message instanceof External.Login) {
            logger.info("user : " + ((External.Login) message).getUserId() + " request login");

            ctx.attr(ClientConnections.USER_ID).set(((External.Login) message).getUserId());
            Internal.ILogin iLogin = Internal.ILogin.newBuilder()
                    .setFrom(Internal.ServerType.GATE)
                    .setTo(Internal.ServerType.LOGIC)
                    .setUserId(((External.Login) message).getUserId())
                    .setConnectionId(ctx.attr(ClientConnections.CONNECTION_ID).get())
                    .build();

            ClientConnections.bindUser2Connection(((External.Login) message).getUserId(),
                    ctx.attr(ClientConnections.CONNECTION_ID).get());

            AuthConnectionHandler.getInstance()
                    .getChannelHandlerContext().writeAndFlush(iLogin);
        } else if (message instanceof External.TextMessage) {
            logger.info("GateServer RECEIVE message from USER <" + ((External.TextMessage) message).getFrom() + ">");

            Internal.ITextMessage textMessage = Internal.ITextMessage.newBuilder()
                    .setFrom(Internal.ServerType.GATE)
                    .setTo(Internal.ServerType.LOGIC)
                    .setFromUserId(((External.TextMessage) message).getFrom())
                    .setToUserId(((External.TextMessage) message).getTo())
                    .setMsg(((External.TextMessage) message).getMsg())
                    .setTimestamp(((External.TextMessage) message).getTimestamp())
                    .build();

            LogicConnectionHandler.getInstance()
                    .getChannelHandlerContext().writeAndFlush(textMessage);
        } else if (message instanceof External.BroadTextMessage) {
//            Long connectionId = ctx.attr(ClientConnections.CONNECTION_ID).get();
            ConcurrentHashMap<Long, ChannelHandlerContext> connections =
                    ClientConnections.getConnections();
            for (ChannelHandlerContext context :
                    connections.values()) {
                context.writeAndFlush(message);
            }
        }
    }

}
