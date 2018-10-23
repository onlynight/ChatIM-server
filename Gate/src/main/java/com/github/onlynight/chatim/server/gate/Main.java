package com.github.onlynight.chatim.server.gate;

import com.github.onlynight.chatim.server.gate.config.ConfigReader;
import com.github.onlynight.chatim.server.gate.connection.AuthConnection;

public class Main {

    public static void main(String[] args) {
        ConfigReader config = new ConfigReader(Main.class.getClassLoader()
                .getResourceAsStream("config.xml"));

        new Thread(() -> {
            GateServer server = new GateServer(config.getGatePort());
            server.run();
        }).start();

        new Thread(() -> {
            AuthConnection authConnection = new AuthConnection(config.getAuthIp(), config.getAuthPort());
            authConnection.connect();
        }).start();
    }

}
