package com.github.onlynight.chatim.clientdemo;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ClientHandler extends ChannelHandlerAdapter {

    private ChannelHandlerContext channelHandlerContext;

    private static ClientHandler instance;

    public static ClientHandler getInstance() {
        instance = new ClientHandler();
        return instance;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channelHandlerContext = ctx;
    }

    public ChannelHandlerContext getChannelHandlerContext() {
        return channelHandlerContext;
    }
}
