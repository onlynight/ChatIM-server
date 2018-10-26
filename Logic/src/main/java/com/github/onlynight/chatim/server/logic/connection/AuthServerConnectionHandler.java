package com.github.onlynight.chatim.server.logic.connection;

import io.netty.channel.ChannelHandlerContext;

public class AuthServerConnectionHandler {

    private ChannelHandlerContext channelHandlerContext;

    private static AuthServerConnectionHandler instance;

    public static AuthServerConnectionHandler getInstance() {
        if (instance == null) {
            instance = new AuthServerConnectionHandler();
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
