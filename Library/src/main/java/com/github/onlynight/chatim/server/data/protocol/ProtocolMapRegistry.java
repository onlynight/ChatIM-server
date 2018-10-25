package com.github.onlynight.chatim.server.data.protocol;

import com.github.onlynight.chatim.server.data.internal.Internal;

public class ProtocolMapRegistry {

    public static void initRegistry() {
        ProtocolMap.register(Internal.ProtocolType.HANDSHAKE, Internal.Handshake::parseFrom, Internal.Handshake.class);
    }

}
