package com.github.onlynight.chatim.server.data.parse;

import com.github.onlynight.chatim.server.data.internal.Internal;

public class ParseMapRegistry {

    public static final int MESSAGE_TYPE_HAND_SHAKE = 100;

    public static void initRegistry() {
        ParseMap.register(MESSAGE_TYPE_HAND_SHAKE, Internal.Handshake::parseFrom, Internal.Handshake.class);
    }

}
