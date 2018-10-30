package com.github.onlynight.chatim.clientdemo;

import com.github.onlynight.chatim.clientdemo.config.ConfigReader;
import com.github.onlynight.chatim.server.data.external.External;
import com.github.onlynight.chatim.server.data.protocol.ProtocolMapRegistry;
import io.netty.channel.ChannelHandlerContext;

import java.util.Date;

public class MainBroad {

    public static void main(String[] args) {
        ConfigReader configReader = new ConfigReader(
                Main.class.getClassLoader()
                        .getResourceAsStream("config.xml")
        );

        ProtocolMapRegistry.initRegistry();

        Client client = new Client(configReader.getGateIp(), configReader.getGatePort());

        new Thread(client::run).start();
        new Thread(() -> {
            ChannelHandlerContext ctx;
            while ((ctx = client.getClientHandler().getChannelHandlerContext()) == null) ;

            External.Login login = External.Login.newBuilder()
                    .setUserId("3")
                    .build();
            ctx.writeAndFlush(login);

            External.BroadTextMessage broadTextMessage = External.BroadTextMessage.newBuilder()
                    .setFrom("3")
                    .setMsg("broad cast message")
                    .setTimestamp(new Date().getTime())
                    .build();
            ctx.writeAndFlush(broadTextMessage);
        }).start();
    }

}
