package com.github.onlynight.chatim.server.handler;

import com.github.onlynight.chatim.server.data.parse.MessageMap;
import com.google.protobuf.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DecodeHandler extends ByteToMessageDecoder {

    private static final Logger logger = LoggerFactory.getLogger(DecodeHandler.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        in.markReaderIndex();

        if (in.readableBytes() < 4) {
            logger.info("in buf readablebytes less than 4 bytes, ignored.");
            in.resetReaderIndex();
            return;
        }

        int length = in.readInt();

        if (length < 0) {
            // TODO: 2018/10/23 handle length 0
        }

        if (length > in.readableBytes() - 4) {
            //注意！编解码器加这种in.readInt()日志，在大并发的情况下很可能会抛数组越界异常！
            in.resetReaderIndex();
            return;
        }

        int messageType = in.readInt();
        ByteBuf byteBuf = Unpooled.buffer(length);
        in.readBytes(byteBuf);

        try {
            byte[] body = byteBuf.array();
            Message message = MessageMap.getMessage(messageType, body);
            out.add(message);
            logger.info("Gate server receive message: length {} , messageType {}", length, messageType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
