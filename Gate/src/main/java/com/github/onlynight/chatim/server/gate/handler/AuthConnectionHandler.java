package com.github.onlynight.chatim.server.gate.handler;

import com.github.onlynight.chatim.server.data.internal.Internal;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class AuthConnectionHandler extends ChannelHandlerAdapter {

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
        handShakeWithGateServer(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Internal.Handshake handshake = (Internal.Handshake) msg;
        System.out.println(handshake);
    }

    private void handShakeWithGateServer(ChannelHandlerContext ctx) {
        Internal.Handshake handshake = Internal.Handshake.newBuilder()
                .setFrom(Internal.Handshake.From.GATE).build();
        ctx.writeAndFlush(handshake);
    }

    public ChannelHandlerContext getAuthChannelHandlerContext() {
        return authChannelHandlerContext;
    }
}
