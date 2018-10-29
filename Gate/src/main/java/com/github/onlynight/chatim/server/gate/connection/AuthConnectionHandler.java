package com.github.onlynight.chatim.server.gate.connection;

import com.github.onlynight.chatim.server.data.external.External;
import com.github.onlynight.chatim.server.data.internal.Internal;
import com.google.protobuf.Message;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

public class AuthConnectionHandler extends ChannelHandlerAdapter {

    private Logger logger = Logger.getLogger(AuthConnectionHandler.class);

    private ChannelHandlerContext channelHandlerContext;

    private static AuthConnectionHandler instance;

    public static AuthConnectionHandler getInstance() {
        if (instance == null) {
            instance = new AuthConnectionHandler();
        }
        return instance;
    }

    private AuthConnectionHandler() {
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
            logger.info("SEND msg to <" + ((Internal.ITextMessage) message).getToUserId() + ">");

            String to = ((Internal.ITextMessage) message).getToUserId();

            ChannelHandlerContext clientCtx = ClientConnections.getConnection(to);
            if (clientCtx != null) {
                External.TextMessage temp = External.TextMessage.newBuilder()
                        .setFrom(((Internal.ITextMessage) message).getFromUserId())
                        .setTo(((Internal.ITextMessage) message).getToUserId())
                        .setMsg(((Internal.ITextMessage) message).getMsg())
                        .setTimestamp(((Internal.ITextMessage) message).getTimestamp())
                        .build();
                clientCtx.writeAndFlush(temp);
            } else {
                logger.info("USER <" + ((Internal.ITextMessage) message).getToUserId() + "> not online");
            }
        }
    }

    private void handShakeWithAuthServer(ChannelHandlerContext ctx) {
        Internal.IHandshake handshake = Internal.IHandshake.newBuilder()
                .setFrom(Internal.ServerType.GATE)
                .setTo(Internal.ServerType.AUTH).build();
        ctx.writeAndFlush(handshake);
    }

    public ChannelHandlerContext getChannelHandlerContext() {
        return channelHandlerContext;
    }
}
