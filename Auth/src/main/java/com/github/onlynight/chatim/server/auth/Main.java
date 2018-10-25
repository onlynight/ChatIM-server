package com.github.onlynight.chatim.server.auth;

import com.github.onlynight.chatim.server.auth.config.ConfigReader;
import com.github.onlynight.chatim.server.data.protocol.ProtocolMapRegistry;

public class Main {

    public static void main(String[] args) {
        ConfigReader config = new ConfigReader(Main.class.getClassLoader()
                .getResourceAsStream("config.xml"));

        ProtocolMapRegistry.initRegistry();

        new Thread(() -> {
            new AuthServer(config.getAuthPort()).run();
        }).start();
    }

}
