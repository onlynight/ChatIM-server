package com.github.onlynight.chatim.server.logic;

import com.github.onlynight.chatim.server.data.internal.Internal;
import com.github.onlynight.chatim.server.logic.connection.GateServerConnectionHandler;
import com.google.protobuf.Message;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

public class LogicServerHandler extends ChannelHandlerAdapter {

    private Logger logger = Logger.getLogger(LogicServerHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message) msg;
        if (message instanceof Internal.Handshake) {
            logger.info("HANDSHAKE from " + ((Internal.Handshake) message).getFrom()
                    + " to " + ((Internal.Handshake) message).getTo());
            GateServerConnectionHandler.getInstance().setChannelHandlerContext(ctx);
            handShakeWithGateServer(GateServerConnectionHandler.getInstance().getChannelHandlerContext());
        } else {
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private void handShakeWithGateServer(ChannelHandlerContext ctx) {
        Internal.Handshake handshake = Internal.Handshake.newBuilder()
                .setFrom(Internal.ServerType.LOGIC)
                .setTo(Internal.ServerType.GATE).build();
        ctx.writeAndFlush(handshake);
    }

}
