package com.github.onlynight.chatim.server.auth;

import com.github.onlynight.chatim.server.auth.handler.GateConnectionHandler;
import com.github.onlynight.chatim.server.data.internal.Internal;
import com.google.protobuf.Message;
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
        Message message = (Message) msg;
        if (message instanceof Internal.Handshake) {
            logger.info("HANDSHAKE from " + ((Internal.Handshake) message).getFrom()
                    + " to " + ((Internal.Handshake) message).getTo());
            GateConnectionHandler.getInstance().setGateChannelHandlerContext(ctx);
            handShakeWithGateServer(GateConnectionHandler.getInstance().getGateChannelHandlerContext());
        } else {
            // TODO: 2018/10/24 handle message
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private void handShakeWithGateServer(ChannelHandlerContext ctx) {
        Internal.Handshake handshake = Internal.Handshake.newBuilder()
                .setFrom(Internal.Handshake.ServerType.AUTH)
                .setTo(Internal.Handshake.ServerType.GATE).build();
        ctx.writeAndFlush(handshake);
    }

}
