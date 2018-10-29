package com.github.onlynight.chatim.server.logic.connection;

import io.netty.channel.ChannelHandlerContext;

public class GateConnectionHandler {

    private ChannelHandlerContext channelHandlerContext;

    private static GateConnectionHandler instance;

    public static GateConnectionHandler getInstance() {
        if (instance == null) {
            instance = new GateConnectionHandler();
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
