package com.github.onlynight.chatim.server.data.protocol;

import com.github.onlynight.chatim.server.data.internal.Internal;

public class ProtocolMapRegistry {

    public static final int PROTOCOL_TYPE_HAND_SHAKE = 100;

    public static void initRegistry() {
        ProtocolMap.register(PROTOCOL_TYPE_HAND_SHAKE, Internal.Handshake::parseFrom, Internal.Handshake.class);
    }

}
