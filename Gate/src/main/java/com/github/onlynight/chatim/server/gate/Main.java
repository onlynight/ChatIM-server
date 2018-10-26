package com.github.onlynight.chatim.server.gate;

import com.github.onlynight.chatim.server.data.protocol.ProtocolMapRegistry;
import com.github.onlynight.chatim.server.gate.config.ConfigReader;
import com.github.onlynight.chatim.server.gate.connection.AuthConnection;
import com.github.onlynight.chatim.server.gate.connection.LogicConnection;

public class Main {

    public static void main(String[] args) {
        ConfigReader config = new ConfigReader(Main.class.getClassLoader()
                .getResourceAsStream("config.xml"));

        ProtocolMapRegistry.initRegistry();

        // start gate server
        new Thread(() -> {
            GateServer server = new GateServer(config.getGatePort());
            server.run();
        }).start();

        // connect to auth server
        new Thread(() -> {
            AuthConnection authConnection = AuthConnection.getInstance();
            authConnection.setServerAddress(config.getAuthIp(), config.getAuthPort());
            authConnection.connect();
        }).start();

        // connect to logic server
        new Thread(() -> {
            LogicConnection logicConnection = LogicConnection.getInstance();
            logicConnection.setServerAddress(config.getLogicIp(), config.getLogicPort());
            logicConnection.connect();
        }).start();
    }

}
