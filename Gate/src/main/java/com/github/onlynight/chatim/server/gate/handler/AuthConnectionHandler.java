package com.github.onlynight.chatim.server.gate.handler;

import com.github.onlynight.chatim.server.data.internal.Internal;
import com.google.protobuf.Message;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

public class AuthConnectionHandler extends ChannelHandlerAdapter {

    private Logger logger = Logger.getLogger(AuthConnectionHandler.class);

    private ChannelHandlerContext authChannelHandlerContext;

    private static AuthConnectionHandler instance;

    public static AuthConnectionHandler getInstance() {
        if (instance == null) {
            instance = new AuthConnectionHandler();
        }
        return instance;
    }

    private AuthConnectionHandler() {
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        authChannelHandlerContext = ctx;
        handShakeWithAuthServer(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message) msg;
        if (message instanceof Internal.Handshake) {
            logger.info("HANDSHAKE from " + ((Internal.Handshake) message).getFrom()
                    + " to " + ((Internal.Handshake) message).getTo());
        } else {
            // TODO: 2018/10/24 handle message
        }
    }

    private void handShakeWithAuthServer(ChannelHandlerContext ctx) {
        Internal.Handshake handshake = Internal.Handshake.newBuilder()
                .setFrom(Internal.ServerType.GATE)
                .setTo(Internal.ServerType.AUTH).build();
        ctx.writeAndFlush(handshake);
    }

    public ChannelHandlerContext getAuthChannelHandlerContext() {
        return authChannelHandlerContext;
    }
}
