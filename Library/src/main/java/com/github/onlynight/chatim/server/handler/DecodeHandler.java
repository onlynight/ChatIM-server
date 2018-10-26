package com.github.onlynight.chatim.server.handler;

import com.github.onlynight.chatim.server.data.protocol.Protocol;
import com.github.onlynight.chatim.server.data.protocol.ProtocolMap;
import com.google.protobuf.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.apache.log4j.Logger;

import java.util.List;

public class DecodeHandler extends ByteToMessageDecoder {

    private static final Logger logger = Logger.getLogger(DecodeHandler.class);

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

        int protocolType = in.readInt();
        ByteBuf byteBuf = Unpooled.buffer(length);
        in.readBytes(byteBuf);

        try {
            byte[] body = byteBuf.array();
            Message message = ProtocolMap.getMessage(Protocol.ProtocolType.valueOf(protocolType), body);
            out.add(message);
            logger.info("Gate server receive message: length " + length + " , protocolType " + protocolType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
