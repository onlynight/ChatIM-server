package com.github.onlynight.chatim.server.gate.connection;

import com.github.onlynight.chatim.server.connection.BaseConnection;
import io.netty.channel.ChannelHandlerAdapter;

public class AuthConnection extends BaseConnection {

    private static AuthConnection instance;

    public static AuthConnection getInstance() {
        if (instance == null) {
            instance = new AuthConnection();
        }
        return instance;
    }

    private AuthConnection() {
        super();
    }

    @Override
    protected ChannelHandlerAdapter getChannelHandler() {
        return AuthConnectionHandler.getInstance();
    }

}
