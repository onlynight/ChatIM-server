package com.github.onlynight.chatim.server.logic;

import com.github.onlynight.chatim.server.data.internal.Internal;
import com.github.onlynight.chatim.server.logic.connection.AuthServerConnectionHandler;
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
            if (((Internal.Handshake) message).getFrom() == Internal.ServerType.GATE) {
                GateServerConnectionHandler.getInstance().setChannelHandlerContext(ctx);
                handShakeWithServer(GateServerConnectionHandler.getInstance().getChannelHandlerContext(),
                        Internal.ServerType.GATE);
            } else if (((Internal.Handshake) message).getFrom() == Internal.ServerType.AUTH) {
                AuthServerConnectionHandler.getInstance().setChannelHandlerContext(ctx);
                handShakeWithServer(AuthServerConnectionHandler.getInstance().getChannelHandlerContext(),
                        Internal.ServerType.AUTH);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private void handShakeWithServer(ChannelHandlerContext ctx, Internal.ServerType to) {
        Internal.Handshake handshake = Internal.Handshake.newBuilder()
                .setFrom(Internal.ServerType.LOGIC)
                .setTo(to).build();
        ctx.writeAndFlush(handshake);
    }

}
