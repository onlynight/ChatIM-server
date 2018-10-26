package com.github.onlynight.chatim.server.logic;

import com.github.onlynight.chatim.server.data.internal.Internal;
import com.github.onlynight.chatim.server.data.protocol.ProtocolMapRegistry;
import com.github.onlynight.chatim.server.logic.config.ConfigReader;
import com.github.onlynight.chatim.server.logic.connection.AuthServerConnection;
import com.github.onlynight.chatim.server.logic.connection.AuthServerConnectionHandler;
import io.netty.channel.ChannelHandlerContext;

public class Main {

    public static void main(String[] args) {
        ConfigReader configReader = new ConfigReader(Main.class.getClassLoader().getResourceAsStream("config.xml"));

        ProtocolMapRegistry.initRegistry();

        new Thread(() -> {
            LogicServer logicServer = new LogicServer(configReader.getLogicPort());
            logicServer.run();
        }).start();

        new Thread(() -> {
            AuthServerConnection connection = AuthServerConnection.getInstance();
            connection.setServerAddress(configReader.getAuthIp(), configReader.getAuthPort());
            connection.connect();
        }).start();

        new Thread(() -> {
            Internal.Handshake handshake = Internal.Handshake.newBuilder()
                    .setFrom(Internal.ServerType.LOGIC)
                    .setTo(Internal.ServerType.AUTH)
                    .build();
            while (true) {
                ChannelHandlerContext ctx = AuthServerConnectionHandler.getInstance().getChannelHandlerContext();
                if (ctx != null) {
                    ctx.writeAndFlush(handshake);
                    break;
                }
            }
        }).start();
    }

}
