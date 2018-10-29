package com.github.onlynight.chatim.clientdemo;

import com.github.onlynight.chatim.server.handler.DecodeHandler;
import com.github.onlynight.chatim.server.handler.EncodeHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {

    private String host;
    private int port;

    private ClientHandler clientHandler;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
        this.clientHandler = new ClientHandler();
    }

    public void run() {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new EncodeHandler())
                                    .addLast(new DecodeHandler())
                                    .addLast(clientHandler);
                        }
                    });
            ChannelFuture f = bootstrap.connect(host, port).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }
}
