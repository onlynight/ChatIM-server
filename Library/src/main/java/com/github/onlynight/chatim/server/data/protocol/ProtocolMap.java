package com.github.onlynight.chatim.server.data.protocol;

import com.google.protobuf.Message;

import java.io.IOException;
import java.util.HashMap;

public class ProtocolMap {

    @FunctionalInterface
    public interface Parser {
        Message process(byte[] bytes) throws IOException;
    }

    private static HashMap<Protocol.ProtocolType, Parser> protocolType2ParserMap = new HashMap<>();
    private static HashMap<Class<?>, Protocol.ProtocolType> message2ProtocolTypeMap = new HashMap<>();

    public static void register(Protocol.ProtocolType protocolType, Parser parser, Class messageClass) {
        protocolType2ParserMap.putIfAbsent(protocolType, parser);
        message2ProtocolTypeMap.putIfAbsent(messageClass, protocolType);
    }

    public static Message getMessage(Protocol.ProtocolType protocolType, byte[] bytes) throws IOException {
        Parser parser = protocolType2ParserMap.get(protocolType);
        if (parser == null) {
            return null;
        }

        return parser.process(bytes);
    }

    public static Message getMessage(int protocolType, byte[] bytes) throws IOException {
        Parser parser = protocolType2ParserMap.get(Protocol.ProtocolType.valueOf(protocolType));
        if (parser == null) {
            return null;
        }

        return parser.process(bytes);
    }

    public static Protocol.ProtocolType getProtocolType(Message message) {
        return getProtocolType(message.getClass());
    }

    public static Protocol.ProtocolType getProtocolType(Class<?> clazz) {
        return message2ProtocolTypeMap.get(clazz);
    }
}
