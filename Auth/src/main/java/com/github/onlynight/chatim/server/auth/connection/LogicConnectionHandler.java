package com.github.onlynight.chatim.server.auth.connection;

import com.github.onlynight.chatim.server.data.internal.Internal;
import com.google.protobuf.Message;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

public class LogicConnectionHandler extends ChannelHandlerAdapter {

    private Logger logger = Logger.getLogger(LogicConnectionHandler.class);

    private ChannelHandlerContext channelHandlerContext;

    private static LogicConnectionHandler instance;

    public static LogicConnectionHandler getInstance() {
        if (instance == null) {
            instance = new LogicConnectionHandler();
        }
        return instance;
    }

    public ChannelHandlerContext getChannelHandlerContext() {
        return channelHandlerContext;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channelHandlerContext = ctx;
        handShakeWithAuthServer(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message) msg;
        if (message instanceof Internal.IHandshake) {
            logger.info("HANDSHAKE from " + ((Internal.IHandshake) message).getFrom()
                    + " to " + ((Internal.IHandshake) message).getTo());
        } else if (message instanceof Internal.ITextMessage) {
            logger.info("USER <" + ((Internal.ITextMessage) message).getFromUserId() + ">"
                    + " SEND message to USER <" + ((Internal.ITextMessage) message).getToUserId() + ">");

            // check to user is online

            GateConnectionHandler.getInstance()
                    .getChannelHandlerContext().writeAndFlush(message);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private void handShakeWithAuthServer(ChannelHandlerContext ctx) {
        Internal.IHandshake handshake = Internal.IHandshake.newBuilder()
                .setFrom(Internal.ServerType.AUTH)
                .setTo(Internal.ServerType.LOGIC)
                .build();
        ctx.writeAndFlush(handshake);
    }

}
