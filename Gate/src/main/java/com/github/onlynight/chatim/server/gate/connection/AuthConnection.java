package com.github.onlynight.chatim.server.gate.connection;

import com.github.onlynight.chatim.server.gate.handler.AuthConnectionHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class AuthConnection {

    private String authIp;
    private int authPort;

    public AuthConnection(String authIp, int authPort) {
        this.authIp = authIp;
        this.authPort = authPort;
    }

    public void connect() {
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(AuthConnectionHandler.getInstance());

            ChannelFuture f = bootstrap.connect(authIp, authPort).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workGroup.shutdownGracefully();
        }
    }

}