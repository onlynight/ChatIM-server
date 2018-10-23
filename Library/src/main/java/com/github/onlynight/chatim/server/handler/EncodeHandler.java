package com.github.onlynight.chatim.server.handler;

import com.github.onlynight.chatim.server.data.parse.MessageMap;
import com.google.protobuf.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncodeHandler extends MessageToByteEncoder<Message> {
    private static final Logger logger = LoggerFactory.getLogger(EncodeHandler.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        byte[] bytes = msg.toByteArray();

        int messageType = MessageMap.getMessageType(msg);
        int length = bytes.length;

        ByteBuf byteBuf = Unpooled.buffer(8 + length);
        byteBuf.writeInt(length);
        byteBuf.writeInt(messageType);
        byteBuf.writeBytes(bytes);

        out.writeBytes(byteBuf);

        logger.info("GateServer Send Message, remoteAddress: {}, content length {}, ptoNum: {}",
                ctx.channel().remoteAddress(), length, messageType);
    }

}
