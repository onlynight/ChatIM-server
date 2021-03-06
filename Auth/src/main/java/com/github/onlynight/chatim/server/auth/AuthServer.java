package com.github.onlynight.chatim.server.auth;

import com.github.onlynight.chatim.server.server.CommonServer;
import io.netty.channel.ChannelHandlerAdapter;

public class AuthServer extends CommonServer {

    public AuthServer(int port) {
        super(port);
    }

    @Override
    protected ChannelHandlerAdapter createChannelHandlerAdapter() {
        return new AuthServerHandler();
    }

}
