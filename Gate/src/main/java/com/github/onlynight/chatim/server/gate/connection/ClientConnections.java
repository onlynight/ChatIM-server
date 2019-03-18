package com.github.onlynight.chatim.server.gate.connection;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ClientConnections {

    private static final AtomicLong connectionIdGenerator = new AtomicLong(0);

    public static AttributeKey<Long> CONNECTION_ID = AttributeKey.newInstance("connection_id");
    public static AttributeKey<String> USER_ID = AttributeKey.newInstance("user_id");

    private static ConcurrentHashMap<Long, ChannelHandlerContext> connections = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, Long> userConnectionMap = new ConcurrentHashMap<>();

    public static void addConnection(ChannelHandlerContext ctx) {
        long id = connectionIdGenerator.incrementAndGet();
        ctx.attr(CONNECTION_ID).set(id);
        connections.put(id, ctx);
    }

    public static ChannelHandlerContext getConnection(long connectionId) {
        return connections.get(connectionId);
    }

    public static ChannelHandlerContext getConnection(String userId) {
        Long connectionId = userConnectionMap.get(userId);
        if (connectionId != null) {
            return connections.get(connectionId);
        }
        return null;
    }

    public static void removeConnection(long connectionId) {
        connections.remove(connectionId);
    }

    public static void removeConnection(ChannelHandlerContext ctx) {
        Long id = ctx.attr(CONNECTION_ID).get();
        removeConnection(id);
    }

    public static void bindUser2Connection(String userId, long connectionId) {
        userConnectionMap.putIfAbsent(userId, connectionId);
    }

    public static void unbindUser2Connection(String userId) {
        if (userId != null && !userId.equals("")) {
            userConnectionMap.remove(userId);
        }
//        removeConnection(connectionId);
    }

    public static int connectionLength() {
        return connections.size();
    }

    public static ConcurrentHashMap<Long, ChannelHandlerContext> getConnections() {
        return connections;
    }

}
