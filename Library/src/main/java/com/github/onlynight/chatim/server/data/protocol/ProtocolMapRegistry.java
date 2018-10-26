package com.github.onlynight.chatim.server.data.protocol;

import com.github.onlynight.chatim.server.data.external.External;
import com.github.onlynight.chatim.server.data.internal.Internal;

public class ProtocolMapRegistry {

    public static void initRegistry() {
        ProtocolMap.register(Protocol.ProtocolType.HANDSHAKE, Internal.Handshake::parseFrom, Internal.Handshake.class);
        ProtocolMap.register(Protocol.ProtocolType.TEXT_MESSAGE, External.TextMessage::parseFrom, External.TextMessage.class);
    }

}
