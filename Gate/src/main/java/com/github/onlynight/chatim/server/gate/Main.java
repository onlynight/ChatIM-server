package com.github.onlynight.chatim.server.gate;

import com.github.onlynight.chatim.server.gate.config.ConfigReader;

public class Main {

    public static void main(String[] args) {
        System.out.println(args[0]);
        ConfigReader config = new ConfigReader(Main.class.getClassLoader()
                .getResourceAsStream("config.xml"));

        GateServer server = new GateServer(config.getGatePort());
        server.run();
    }

}
