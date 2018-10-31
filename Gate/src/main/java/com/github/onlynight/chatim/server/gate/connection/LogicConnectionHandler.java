package com.github.onlynight.chatim.server.gate.connection;

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

    private LogicConnectionHandler() {
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channelHandlerContext = ctx;
        handShakeWithLogicServer(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message) msg;
        if (message instanceof Internal.IHandshake) {
            logger.info("HANDSHAKE from " + ((Internal.IHandshake) message).getFrom()
                    + " to " + ((Internal.IHandshake) message).getTo());
        } else {
            // TODO: 2018/10/24 handle message
        }
    }

    private void handShakeWithLogicServer(ChannelHandlerContext ctx) {
        Internal.IHandshake handshake = Internal.IHandshake.newBuilder()
                .setFrom(Internal.ServerType.GATE)
                .setTo(Internal.ServerType.LOGIC)
                .build();
        ctx.writeAndFlush(handshake);
    }

    public ChannelHandlerContext getChannelHandlerContext() {
        return channelHandlerContext;
    }
}
