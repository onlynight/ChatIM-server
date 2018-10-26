package com.github.onlynight.chatim.server.logic;

import com.github.onlynight.chatim.server.server.CommonServer;
import io.netty.channel.ChannelHandlerAdapter;

public class LogicServer extends CommonServer {

    public LogicServer(int port) {
        super(port);
    }

    @Override
    protected ChannelHandlerAdapter createChannelHandlerAdapter() {
        return new LogicServerHandler();
    }

}
