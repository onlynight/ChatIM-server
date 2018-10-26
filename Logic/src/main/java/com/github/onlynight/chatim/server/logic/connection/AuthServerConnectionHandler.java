package com.github.onlynight.chatim.server.logic.connection;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class AuthServerConnectionHandler extends ChannelHandlerAdapter {

    private ChannelHandlerContext channelHandlerContext;

    private static AuthServerConnectionHandler instance;

    public static AuthServerConnectionHandler getInstance() {
        if (instance == null) {
            instance = new AuthServerConnectionHandler();
        }
        return instance;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channelHandlerContext = ctx;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    public ChannelHandlerContext getChannelHandlerContext() {
        return channelHandlerContext;
    }
}
