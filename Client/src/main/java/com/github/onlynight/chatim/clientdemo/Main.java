package com.github.onlynight.chatim.clientdemo;

import com.github.onlynight.chatim.clientdemo.config.ConfigReader;
import com.github.onlynight.chatim.server.data.external.External;
import com.github.onlynight.chatim.server.data.protocol.ProtocolMapRegistry;
import io.netty.channel.ChannelHandlerContext;

public class Main {

    public static void main(String[] args) {
        ConfigReader configReader = new ConfigReader(
                Main.class.getClassLoader()
                        .getResourceAsStream("config.xml")
        );

        ProtocolMapRegistry.initRegistry();

        new Thread(() -> {
            Client client = new Client(configReader.getGateIp(), configReader.getGatePort());
            client.run();
        }).start();

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                External.TextMessage message = External.TextMessage.newBuilder()
                        .setFrom("1")
                        .setTo("2")
                        .setMsg("hello world").build();
                ChannelHandlerContext ctx = ClientHandler.getInstance().getChannelHandlerContext();
                if (ctx != null) {
                    ctx.writeAndFlush(message);
                }
            }
        }).start();
    }

}
