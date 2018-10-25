package com.github.onlynight.chatim.server.gate;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

class ClientConnections {

    private static final AtomicLong connectionIdGenerator = new AtomicLong(0);

    private static AttributeKey<Long> CONNECTION_ID = AttributeKey.newInstance("connection_id");

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
        Long connectionId = userConnectionMap.remove(userId);
        removeConnection(connectionId);
    }

}
