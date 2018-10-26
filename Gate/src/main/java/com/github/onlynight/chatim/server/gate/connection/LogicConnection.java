package com.github.onlynight.chatim.server.gate.connection;

import com.github.onlynight.chatim.server.connection.BaseConnection;
import io.netty.channel.ChannelHandlerAdapter;

public class LogicConnection extends BaseConnection {

    private static LogicConnection instance;

    public static LogicConnection getInstance() {
        if (instance == null) {
            instance = new LogicConnection();
        }
        return instance;
    }

    private LogicConnection() {
        super();
    }

    @Override
    protected ChannelHandlerAdapter getChannelHandler() {
        return LogicConnectionHandler.getInstance();
    }

}
