package com.github.onlynight.chatim.server.data.parse;

import com.google.protobuf.Message;

import java.io.IOException;
import java.util.HashMap;

public class ParseMap {

    @FunctionalInterface
    public interface Parser {
        Message process(byte[] bytes) throws IOException;
    }

    private static HashMap<Integer, Parser> type2ParserMap = new HashMap<>();
    private static HashMap<Class<?>, Integer> class2TypeMap = new HashMap<>();

    public static void register(int messageType, Parser parser, Class messageClass) {
        type2ParserMap.putIfAbsent(messageType, parser);
        class2TypeMap.putIfAbsent(messageClass, messageType);
    }

    public static Message getMessage(int messageType, byte[] bytes) throws IOException {
        Parser parser = type2ParserMap.get(messageType);
        if (parser == null) {
            return null;
        }

        return parser.process(bytes);
    }

    public static Integer getMessageType(Message message) {
        return getMessageType(message.getClass());
    }

    public static Integer getMessageType(Class<?> clazz) {
        return class2TypeMap.get(clazz);
    }
}
