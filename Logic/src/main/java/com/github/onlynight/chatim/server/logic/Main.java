package com.github.onlynight.chatim.server.logic;

import com.github.onlynight.chatim.server.data.protocol.ProtocolMapRegistry;
import com.github.onlynight.chatim.server.logic.config.ConfigReader;

public class Main {

    public static void main(String[] args) {
        ConfigReader configReader = new ConfigReader(Main.class.getClassLoader().getResourceAsStream("config.xml"));

        ProtocolMapRegistry.initRegistry();

        new Thread(() -> {
            LogicServer logicServer = new LogicServer(configReader.getLogicPort());
            logicServer.run();
        }).start();

    }

}
