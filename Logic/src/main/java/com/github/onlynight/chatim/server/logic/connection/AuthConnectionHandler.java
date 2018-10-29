package com.github.onlynight.chatim.server.logic.connection;

import io.netty.channel.ChannelHandlerContext;

public class AuthConnectionHandler {

    private ChannelHandlerContext channelHandlerContext;

    private static AuthConnectionHandler instance;

    public static AuthConnectionHandler getInstance() {
        if (instance == null) {
            instance = new AuthConnectionHandler();
        }
        return instance;
    }

    public ChannelHandlerContext getChannelHandlerContext() {
        return channelHandlerContext;
    }

    public void setChannelHandlerContext(ChannelHandlerContext channelHandlerContext) {
        this.channelHandlerContext = channelHandlerContext;
    }
    
}
