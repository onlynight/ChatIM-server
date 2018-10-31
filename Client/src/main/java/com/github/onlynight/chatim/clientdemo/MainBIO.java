package com.github.onlynight.chatim.clientdemo;

import com.github.onlynight.chatim.server.data.external.External;
import com.github.onlynight.chatim.server.data.protocol.Protocol;
import com.github.onlynight.chatim.server.data.protocol.ProtocolMap;
import com.github.onlynight.chatim.server.data.protocol.ProtocolMapRegistry;
import com.google.protobuf.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Date;

public class MainBIO {

    private static final String DEFAULT_USER_ID = "5";
    private static final String DEFAULT_CHAT_USER_ID = "1";

    public static void main(String[] args) throws UnknownHostException {

        ProtocolMapRegistry.initRegistry();

        try {
            // read buffer
            byte[] buf = new byte[4096];

            Socket socket = new Socket("127.0.0.1", 8080);

            OutputStream os = socket.getOutputStream();
            InputStream is = socket.getInputStream();

            // send login request
            External.Login login = External.Login.newBuilder()
                    .setUserId(DEFAULT_USER_ID)
                    .build();
            sendData(os, encodeData(login.toByteArray(), Protocol.ProtocolType.LOGIN));
            System.out.println("USER <" + login.getUserId() + "> login");

            // receive login result
            CommonProtocol commonProtocol = receiveData(is, buf);
            Message message = null;
            if (commonProtocol != null) {
                message = commonProtocol.data;
            }

            if (message instanceof External.LoginResult) {
                System.out.println("LOGIN RESULT STATE = " + ((External.LoginResult) message).getState());
            }

            // send text message
            External.TextMessage textMessage = External.TextMessage.newBuilder()
                    .setFrom(DEFAULT_USER_ID)
                    .setTo(DEFAULT_CHAT_USER_ID)
                    .setMsg("hello from bio socket client")
                    .setTimestamp(new Date().getTime())
                    .build();
            sendData(os, encodeData(textMessage.toByteArray(), Protocol.ProtocolType.TEXT_MESSAGE));
            System.out.println("USER <" + DEFAULT_USER_ID + "> SEND msg TO USER <" + DEFAULT_CHAT_USER_ID + "> SUCCESS");

            for (int i = 0; i < 10; i++) {
                Thread.sleep(1000);
                sendData(os, encodeData(textMessage.toByteArray(), Protocol.ProtocolType.TEXT_MESSAGE));
                System.out.println("USER <" + DEFAULT_USER_ID + "> SEND msg TO USER <" + DEFAULT_CHAT_USER_ID + "> SUCCESS");
            }

            socket.close();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void sendData(OutputStream os, byte[] data) {
        try {
            os.write(data);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static CommonProtocol receiveData(InputStream is, byte[] buf) {
        try {
            int len = is.read(buf);
            return decodeData(buf, len);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] encodeData(byte[] data, Protocol.ProtocolType protocolType) {
        int length = data.length;
        ByteBuffer buffer = ByteBuffer.allocate(8 + length);
        buffer.putInt(length);
        buffer.putInt(protocolType.getNumber());
        buffer.put(data);
        return buffer.array();
    }

    private static CommonProtocol decodeData(byte[] data, int length) {
        byte[] protocolBuf = subBytes(data, 0, 8);
        byte[] dataBuf = subBytes(data, 8, length - 8);
        ByteBuffer buffer = ByteBuffer.wrap(protocolBuf);

        CommonProtocol commonProtocol = new CommonProtocol();
        commonProtocol.length = buffer.getInt();
        Protocol.ProtocolType protocolType = Protocol.ProtocolType.valueOf(buffer.getInt());
        commonProtocol.protocolType = protocolType;

        try {
            commonProtocol.data = ProtocolMap.getMessage(protocolType, dataBuf);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return commonProtocol;
    }

    private static byte[] subBytes(byte[] src, int begin, int count) {
        byte[] bs = new byte[count];
        System.arraycopy(src, begin, bs, 0, count);
        return bs;
    }

    public static class CommonProtocol {
        int length;
        Protocol.ProtocolType protocolType;
        Message data;
    }

}
