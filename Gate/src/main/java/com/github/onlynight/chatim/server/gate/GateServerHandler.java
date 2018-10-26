package com.github.onlynight.chatim.server.gate;

import com.github.onlynight.chatim.server.data.external.External;
import com.github.onlynight.chatim.server.data.internal.Internal;
import com.github.onlynight.chatim.server.gate.connection.AuthConnectionHandler;
import com.github.onlynight.chatim.server.gate.connection.ClientConnections;
import com.google.protobuf.Message;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

public class GateServerHandler extends ChannelHandlerAdapter {

    private Logger logger = Logger.getLogger(GateServerHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ClientConnections.addConnection(ctx);
        logger.info("client num is : " + ClientConnections.connectionLength());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ClientConnections.removeConnection(ctx);
        logger.info("client num is : " + ClientConnections.connectionLength());

        Internal.ILogout iLogout = Internal.ILogout.newBuilder()
                .setFrom(Internal.ServerType.GATE)
                .setTo(Internal.ServerType.LOGIC)
                .setUserId(ctx.attr(ClientConnections.USER_ID).get())
                .setConnectionId(ctx.attr(ClientConnections.CONNECTION_ID).get())
                .build();

        AuthConnectionHandler.getInstance()
                .getChannelHandlerContext().writeAndFlush(iLogout);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message) msg;
        if (message instanceof External.TextMessage) {
            logger.info(((External.TextMessage) message).getMsg());
        } else if (message instanceof External.Login) {
            logger.info("user : " + ((External.Login) message).getUserId() + " request login");

            ctx.attr(ClientConnections.USER_ID).set(((External.Login) message).getUserId());
            Internal.ILogin iLogin = Internal.ILogin.newBuilder()
                    .setFrom(Internal.ServerType.GATE)
                    .setTo(Internal.ServerType.LOGIC)
                    .setUserId(((External.Login) message).getUserId())
                    .setConnectionId(ctx.attr(ClientConnections.CONNECTION_ID).get())
                    .build();

            AuthConnectionHandler.getInstance()
                    .getChannelHandlerContext().writeAndFlush(iLogin);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
