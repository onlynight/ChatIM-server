package com.github.onlynight.chatim.server.auth;

import com.github.onlynight.chatim.server.auth.connection.GateConnectionHandler;
import com.github.onlynight.chatim.server.data.internal.Internal;
import com.google.protobuf.Message;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

import java.util.concurrent.ConcurrentHashMap;

public class AuthServerHandler extends ChannelHandlerAdapter {

    private ConcurrentHashMap<String, Long> onlineClients = new ConcurrentHashMap<>();

    private Logger logger = Logger.getLogger(AuthServerHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message) msg;
        logger.info("message class is " + message.getClass());
        if (message instanceof Internal.Handshake) {
            logger.info("HANDSHAKE from " + ((Internal.Handshake) message).getFrom()
                    + " to " + ((Internal.Handshake) message).getTo());
            if (((Internal.Handshake) message).getFrom() == Internal.ServerType.GATE) {
                GateConnectionHandler.getInstance().setChannelHandlerContext(ctx);
                handShakeWithServer(GateConnectionHandler.getInstance().getChannelHandlerContext(),
                        Internal.ServerType.GATE);
            }
        } else if (message instanceof Internal.ILogin) {
            logger.info("<" + ((Internal.ILogin) message).getUserId() + ","
                    + ((Internal.ILogin) message).getConnectionId() + ">" + " user login pass from GATE");
            onlineClients.put(((Internal.ILogin) message).getUserId(), ((Internal.ILogin) message).getConnectionId());
        } else if (message instanceof Internal.ILogout) {
            onlineClients.remove(((Internal.ILogout) message).getUserId());
            logger.info("<" + ((Internal.ILogout) message).getUserId() + ","
                    + ((Internal.ILogout) message).getConnectionId() + ">" + " user logout pass from GATE");
        } else {
            // TODO: 2018/10/24 handle message
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private void handShakeWithServer(ChannelHandlerContext ctx, Internal.ServerType to) {
        Internal.Handshake handshake = Internal.Handshake.newBuilder()
                .setFrom(Internal.ServerType.AUTH)
                .setTo(to).build();
        ctx.writeAndFlush(handshake);
    }

}
