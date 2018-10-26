package com.github.onlynight.chatim.server.logic.connection;

import com.github.onlynight.chatim.server.connection.BaseConnection;
import io.netty.channel.ChannelHandlerAdapter;

public class AuthServerConnection extends BaseConnection {

    private static AuthServerConnection instance;

    public static AuthServerConnection getInstance() {
        if (instance == null) {
            instance = new AuthServerConnection();
        }
        return instance;
    }

    private AuthServerConnection() {
    }

    @Override
    protected ChannelHandlerAdapter getChannelHandler() {
        return AuthServerConnectionHandler.getInstance();
    }

}
