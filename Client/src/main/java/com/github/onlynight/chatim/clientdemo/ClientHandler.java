package com.github.onlynight.chatim.clientdemo;

import com.github.onlynight.chatim.server.data.external.External;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ClientHandler extends ChannelHandlerAdapter {

    private ChannelHandlerContext channelHandlerContext;

    private static ClientHandler instance;

    public static ClientHandler getInstance() {
        if (instance == null) {
            instance = new ClientHandler();
        }
        return instance;
    }

    private ClientHandler() {
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channelHandlerContext = ctx;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    }

    public ChannelHandlerContext getChannelHandlerContext() {
        return channelHandlerContext;
    }

}
