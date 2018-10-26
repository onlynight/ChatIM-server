package com.github.onlynight.chatim.server.gate;

import com.github.onlynight.chatim.server.data.external.External;
import com.github.onlynight.chatim.server.gate.connection.ClientConnections;
import com.google.protobuf.Message;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

public class GateServerHandler extends ChannelHandlerAdapter {

    private Logger logger = Logger.getLogger(GateServerHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ClientConnections.addConnection(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ClientConnections.removeConnection(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message) msg;
        if (message instanceof External.TextMessage) {
            logger.info(((External.TextMessage) message).getMsg());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
