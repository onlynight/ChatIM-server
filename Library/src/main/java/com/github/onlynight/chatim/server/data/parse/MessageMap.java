package com.github.onlynight.chatim.server.data.parse;

import com.google.protobuf.Message;

import java.io.IOException;
import java.util.HashMap;

public class MessageMap {

    @FunctionalInterface
    public interface Parser {
        Message process(byte[] bytes) throws IOException;
    }

    private static HashMap<Integer, Parser> type2ParserMap = new HashMap<>();
    private static HashMap<Class<?>, Integer> class2TypeMap = new HashMap<>();

    public static void register(int protocolType, Parser parser, Class messageClass) {
        type2ParserMap.putIfAbsent(protocolType, parser);
        class2TypeMap.putIfAbsent(messageClass, protocolType);
    }

    public static Message getMessage(int protocolType, byte[] bytes) throws IOException {
        Parser parser = type2ParserMap.get(protocolType);
        if (parser == null) {
            return null;
        }

        return parser.process(bytes);
    }

    public static Integer getProtocolType(Message message) {
        return getProtocolType(message.getClass());
    }

    public static Integer getProtocolType(Class<?> clazz) {
        return class2TypeMap.get(clazz);
    }
}
