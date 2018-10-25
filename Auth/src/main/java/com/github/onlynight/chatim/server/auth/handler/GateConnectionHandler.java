package com.github.onlynight.chatim.server.auth.handler;

import io.netty.channel.ChannelHandlerContext;

public class GateConnectionHandler {

    private ChannelHandlerContext gateChannelHandlerContext;

    private static GateConnectionHandler instance;

    public static GateConnectionHandler getInstance() {
        if (instance == null) {
            instance = new GateConnectionHandler();
        }
        return instance;
    }

    public ChannelHandlerContext getGateChannelHandlerContext() {
        return gateChannelHandlerContext;
    }

    public void setGateChannelHandlerContext(ChannelHandlerContext gateChannelHandlerContext) {
        this.gateChannelHandlerContext = gateChannelHandlerContext;
    }

}
