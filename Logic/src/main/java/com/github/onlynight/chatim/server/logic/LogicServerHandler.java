package com.github.onlynight.chatim.server.logic;

import com.github.onlynight.chatim.server.data.internal.Internal;
import com.github.onlynight.chatim.server.logic.connection.AuthConnectionHandler;
import com.github.onlynight.chatim.server.logic.connection.GateConnectionHandler;
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
        if (message instanceof Internal.IHandshake) {
            logger.info("HANDSHAKE from " + ((Internal.IHandshake) message).getFrom()
                    + " to " + ((Internal.IHandshake) message).getTo());
            if (((Internal.IHandshake) message).getFrom() == Internal.ServerType.GATE) {
                GateConnectionHandler.getInstance().setChannelHandlerContext(ctx);
                handShakeWithServer(GateConnectionHandler.getInstance().getChannelHandlerContext(),
                        Internal.ServerType.GATE);
            } else if (((Internal.IHandshake) message).getFrom() == Internal.ServerType.AUTH) {
                AuthConnectionHandler.getInstance().setChannelHandlerContext(ctx);
                handShakeWithServer(AuthConnectionHandler.getInstance().getChannelHandlerContext(),
                        Internal.ServerType.AUTH);
            }
        } else if (message instanceof Internal.ITextMessage) {
            logger.info("USER <" + ((Internal.ITextMessage) message).getFromUserId() + ">"
                    + " SEND message to USER <" + ((Internal.ITextMessage) message).getToUserId() + ">");

            Internal.ITextMessage temp = Internal.ITextMessage.newBuilder()
                    .setFrom(Internal.ServerType.LOGIC)
                    .setTo(Internal.ServerType.AUTH)
                    .setFromUserId(((Internal.ITextMessage) message).getFromUserId())
                    .setToUserId(((Internal.ITextMessage) message).getToUserId())
                    .setMsg(((Internal.ITextMessage) message).getMsg())
                    .setTimestamp(((Internal.ITextMessage) message).getTimestamp())
                    .build();
            AuthConnectionHandler.getInstance()
                    .getChannelHandlerContext().writeAndFlush(temp);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private void handShakeWithServer(ChannelHandlerContext ctx, Internal.ServerType to) {
        Internal.IHandshake handshake = Internal.IHandshake.newBuilder()
                .setFrom(Internal.ServerType.LOGIC)
                .setTo(to).build();
        ctx.writeAndFlush(handshake);
    }

}
