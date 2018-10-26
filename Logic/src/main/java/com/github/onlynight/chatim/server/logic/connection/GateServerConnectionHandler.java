package com.github.onlynight.chatim.server.logic.connection;

import io.netty.channel.ChannelHandlerContext;

public class GateServerConnectionHandler {

    private ChannelHandlerContext channelHandlerContext;

    private static GateServerConnectionHandler instance;

    public static GateServerConnectionHandler getInstance() {
        if (instance == null) {
            instance = new GateServerConnectionHandler();
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
