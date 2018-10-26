package com.github.onlynight.chatim.server.auth;

import com.github.onlynight.chatim.server.auth.config.ConfigReader;
import com.github.onlynight.chatim.server.auth.connection.LogicConnection;
import com.github.onlynight.chatim.server.auth.connection.LogicConnectionHandler;
import com.github.onlynight.chatim.server.data.internal.Internal;
import com.github.onlynight.chatim.server.data.protocol.ProtocolMapRegistry;
import io.netty.channel.ChannelHandlerContext;

public class Main {

    public static void main(String[] args) {
        ConfigReader config = new ConfigReader(Main.class.getClassLoader()
                .getResourceAsStream("config.xml"));

        ProtocolMapRegistry.initRegistry();

        new Thread(() -> {
            new AuthServer(config.getAuthPort()).run();
        }).start();

        new Thread(() -> {
            LogicConnection connection = LogicConnection.getInstance();
            connection.setServerAddress(config.getLogicIp(), config.getLogicPort());
            connection.connect();
        }).start();

    }

}
