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
            ChannelHandlerContext ctx = ClientHandler.getInstance().getChannelHandlerContext();
            while (ctx == null) {
                ctx = ClientHandler.getInstance().getChannelHandlerContext();
            }

            External.Login login = External.Login.newBuilder()
                    .setUserId("1")
                    .build();
            ctx.writeAndFlush(login);

            External.TextMessage message = External.TextMessage.newBuilder()
                    .setFrom("1")
                    .setTo("2")
                    .setMsg("hello world").build();
            ctx.writeAndFlush(message);
        }).start();
    }

}
