package com.github.onlynight.chatim.server.data.parse;

import com.github.onlynight.chatim.server.data.internal.Internal;

public class MessageMapRegistry {

    public static final int PROTOCOL_TYPE_HAND_SHAKE = 100;

    public static void initRegistry() {
        MessageMap.register(PROTOCOL_TYPE_HAND_SHAKE, Internal.Handshake::parseFrom, Internal.Handshake.class);
    }

}
