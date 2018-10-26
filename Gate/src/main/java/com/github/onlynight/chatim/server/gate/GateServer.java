package com.github.onlynight.chatim.server.gate;

import com.github.onlynight.chatim.server.server.CommonServer;
import io.netty.channel.ChannelHandlerAdapter;

public class GateServer extends CommonServer {

    public GateServer(int port) {
        super(port);
    }

    @Override
    protected ChannelHandlerAdapter createChannelHandlerAdapter() {
        return new GateServerHandler();
    }

}
