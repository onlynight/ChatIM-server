package com.github.onlynight.chatim.server.data.protocol;

import com.github.onlynight.chatim.server.data.internal.Internal;
import com.google.protobuf.Message;

import java.io.IOException;
import java.util.HashMap;

public class ProtocolMap {

    @FunctionalInterface
    public interface Parser {
        Message process(byte[] bytes) throws IOException;
    }

    private static HashMap<Internal.ProtocolType, Parser> protocolType2ParserMap = new HashMap<>();
    private static HashMap<Class<?>, Internal.ProtocolType> message2ProtocolTypeMap = new HashMap<>();

    public static void register(Internal.ProtocolType protocolType, Parser parser, Class messageClass) {
        protocolType2ParserMap.putIfAbsent(protocolType, parser);
        message2ProtocolTypeMap.putIfAbsent(messageClass, protocolType);
    }

    public static Message getMessage(Internal.ProtocolType protocolType, byte[] bytes) throws IOException {
        Parser parser = protocolType2ParserMap.get(protocolType);
        if (parser == null) {
            return null;
        }

        return parser.process(bytes);
    }

    public static Internal.ProtocolType getProtocolType(Message message) {
        return getProtocolType(message.getClass());
    }

    public static Internal.ProtocolType getProtocolType(Class<?> clazz) {
        return message2ProtocolTypeMap.get(clazz);
    }
}
