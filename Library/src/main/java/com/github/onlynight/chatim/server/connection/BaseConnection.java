package com.github.onlynight.chatim.server.connection;

import com.github.onlynight.chatim.server.handler.DecodeHandler;
import com.github.onlynight.chatim.server.handler.EncodeHandler;
import com.github.onlynight.chatim.server.utils.TextUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public abstract class BaseConnection {

    private String host;
    private int port;

    public BaseConnection() {
    }

    public BaseConnection(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void setServerAddress(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect() {
        if (TextUtils.isEmpty(host) || port == 0) {
            throw new RuntimeException("connection host or port is null");
        }
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new EncodeHandler())
                                    .addLast(new DecodeHandler())
                                    .addLast(getChannelHandler());
                        }
                    });

            ChannelFuture f = bootstrap.connect(host, port).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workGroup.shutdownGracefully();
        }
    }

    protected abstract ChannelHandlerAdapter getChannelHandler();

}
