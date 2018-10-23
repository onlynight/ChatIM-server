package com.github.onlynight.chatim.server.auth;

import com.github.onlynight.chatim.server.auth.config.ConfigReader;
import com.github.onlynight.chatim.server.data.parse.MessageMapRegistry;

public class Main {

    public static void main(String[] args) {
        ConfigReader config = new ConfigReader(Main.class.getClassLoader()
                .getResourceAsStream("config.xml"));

        MessageMapRegistry.initRegistry();

        new Thread(() -> {
            new AuthServer(config.getAuthPort()).run();
        }).start();
    }

}
