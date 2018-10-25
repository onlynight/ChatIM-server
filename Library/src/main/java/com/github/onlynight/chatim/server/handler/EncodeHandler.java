package com.github.onlynight.chatim.server.handler;

import com.github.onlynight.chatim.server.data.protocol.ProtocolMap;
import com.google.protobuf.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.apache.log4j.Logger;

public class EncodeHandler extends MessageToByteEncoder<Message> {
    private static final Logger logger = Logger.getLogger(EncodeHandler.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        byte[] bytes = msg.toByteArray();

        int protocolType = ProtocolMap.getProtocolType(msg);
        int length = bytes.length;

        ByteBuf byteBuf = Unpooled.buffer(8 + length);
        byteBuf.writeInt(length);
        byteBuf.writeInt(protocolType);
        byteBuf.writeBytes(bytes);

        out.writeBytes(byteBuf);

        logger.info("GateServer Send Message, remoteAddress: " + ctx.channel().remoteAddress() +
                ", content length " + length + ", ptoNum: " + protocolType);
    }

}
