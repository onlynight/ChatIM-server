package com.github.onlynight.chatim.server.auth;

import com.github.onlynight.chatim.server.data.internal.Internal;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

public class AuthServerHandler extends ChannelHandlerAdapter {

    private Logger logger = Logger.getLogger(AuthServerHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Internal.Handshake handshake = (Internal.Handshake) msg;
        logger.info("HANDSHAKE from " + handshake.getFrom());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
