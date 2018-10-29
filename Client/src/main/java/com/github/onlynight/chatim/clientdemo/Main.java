package com.github.onlynight.chatim.clientdemo;

import com.github.onlynight.chatim.clientdemo.config.ConfigReader;
import com.github.onlynight.chatim.server.data.external.External;
import com.github.onlynight.chatim.server.data.protocol.ProtocolMapRegistry;
import io.netty.channel.ChannelHandlerContext;

import java.util.Date;

public class Main {

    public static void main(String[] args) {
        ConfigReader configReader = new ConfigReader(
                Main.class.getClassLoader()
                        .getResourceAsStream("config.xml")
        );

        ProtocolMapRegistry.initRegistry();

        Client client1 = new Client(configReader.getGateIp(), configReader.getGatePort());
        Client client2 = new Client(configReader.getGateIp(), configReader.getGatePort());

        // start client
        new Thread(client1::run).start();
        new Thread(client2::run).start();

        // client1 login and send text message
        new Thread(() -> {
            ChannelHandlerContext ctx1, ctx2;
            while ((ctx1 = client1.getClientHandler().getChannelHandlerContext()) == null) ;
            login(ctx1, "1");

            while ((ctx2 = client2.getClientHandler().getChannelHandlerContext()) == null) ;
            login(ctx2, "2");

            sleep(2000);

            sendTextMsg(ctx1, "1", "2", "hello2");
            sendTextMsg(ctx2, "2", "1", "hello1");
        }).start();
    }

    private static void login(ChannelHandlerContext ctx, String userId) {
        External.Login login = External.Login.newBuilder()
                .setUserId(userId)
                .build();
        ctx.writeAndFlush(login);
    }

    private static void sleep(long time) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void sendTextMsg(ChannelHandlerContext ctx, String from, String to, String msg) {
        External.TextMessage message = External.TextMessage.newBuilder()
                .setFrom(from)
                .setTo(to)
                .setMsg(msg)
                .setTimestamp(new Date().getTime())
                .build();
        ctx.writeAndFlush(message);
    }

}
