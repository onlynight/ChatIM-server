package com.github.onlynight.chatim.clientdemo;

import com.github.onlynight.chatim.server.data.external.External;
import com.google.protobuf.Message;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

public class ClientHandler extends ChannelHandlerAdapter {

    private Logger logger = Logger.getLogger(ClientHandler.class);

    private ChannelHandlerContext channelHandlerContext;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channelHandlerContext = ctx;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message) msg;
        if (message instanceof External.TextMessage) {
            logger.info("RECEIVE USER <" + ((External.TextMessage) message).getFrom()
                    + "> MSG: " + ((External.TextMessage) message).getMsg());
        }
    }

    public ChannelHandlerContext getChannelHandlerContext() {
        return channelHandlerContext;
    }

}
