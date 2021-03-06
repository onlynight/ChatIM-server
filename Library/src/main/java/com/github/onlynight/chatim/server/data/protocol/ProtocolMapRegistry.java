package com.github.onlynight.chatim.server.data.protocol;

import com.github.onlynight.chatim.server.data.external.External;
import com.github.onlynight.chatim.server.data.internal.Internal;

public class ProtocolMapRegistry {

    public static void initRegistry() {
        ProtocolMap.register(Protocol.ProtocolType.TEXT_MESSAGE,
                External.TextMessage::parseFrom, External.TextMessage.class);

        ProtocolMap.register(Protocol.ProtocolType.LOGIN,
                External.Login::parseFrom, External.Login.class);

        ProtocolMap.register(Protocol.ProtocolType.BROAD_TEXT_MESSAGE,
                External.BroadTextMessage::parseFrom, External.BroadTextMessage.class);

        ProtocolMap.register(Protocol.ProtocolType.LOGIN_RESULT,
                External.LoginResult::parseFrom, External.LoginResult.class);


        ProtocolMap.register(Protocol.ProtocolType.I_HANDSHAKE,
                Internal.IHandshake::parseFrom, Internal.IHandshake.class);

        ProtocolMap.register(Protocol.ProtocolType.I_LOGIN,
                Internal.ILogin::parseFrom, Internal.ILogin.class);

        ProtocolMap.register(Protocol.ProtocolType.I_LOGOUT,
                Internal.ILogout::parseFrom, Internal.ILogout.class);

        ProtocolMap.register(Protocol.ProtocolType.I_TEXT_MESSAGE,
                Internal.ITextMessage::parseFrom, Internal.ITextMessage.class);

        ProtocolMap.register(Protocol.ProtocolType.I_BROAD_TEXT_MESSAGE,
                Internal.IBroadTextMessage::parseFrom, Internal.IBroadTextMessage.class);
    }

}
