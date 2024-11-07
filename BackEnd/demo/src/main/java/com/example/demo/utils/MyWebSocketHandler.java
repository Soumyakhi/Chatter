package com.example.demo.utils;

import com.example.demo.serviceInterface.CheckMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MyWebSocketHandler extends TextWebSocketHandler {

    private final Map<Long, Set<WebSocketSession>> groupSessions = new ConcurrentHashMap<>();

    @Autowired
    private CheckMemberService checkMemberService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long groupId = Long.valueOf(session.getUri().getPath().split("/")[4]);
        Long userId = (Long) session.getAttributes().get("userId");

        if (isUserMemberOfGroup(userId, groupId)) {
            groupSessions.computeIfAbsent(groupId, k -> new HashSet<>()).add(session);
            System.out.println("User " + userId + " connected to group " + groupId);
        } else {
            session.close(CloseStatus.NOT_ACCEPTABLE);
        }
    }
    private boolean isUserMemberOfGroup(Long userId, Long groupId) {
        return checkMemberService.checkIfMember(userId, groupId);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Long groupId = Long.valueOf(session.getUri().getPath().split("/")[4]);
        Set<WebSocketSession> groupSessionSet = groupSessions.getOrDefault(groupId, new HashSet<>());
        for (WebSocketSession s : groupSessionSet) {
            if (s.isOpen()) s.sendMessage(message);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Long groupId = Long.valueOf(session.getUri().getPath().split("/")[4]);
        Set<WebSocketSession> groupSessionSet = groupSessions.getOrDefault(groupId, new HashSet<>());
        groupSessionSet.remove(session);
        if (groupSessionSet.isEmpty()) groupSessions.remove(groupId);
        System.out.println("User disconnected from group " + groupId);
    }

    public void sendTextToGroup(Long groupId, String message) {
        Set<WebSocketSession> groupSessionSet = groupSessions.getOrDefault(groupId, new HashSet<>());
        for (WebSocketSession session : groupSessionSet) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(message));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void sendTextToGroup(Long groupId, String message,long userId) {
        Set<WebSocketSession> groupSessionSet = groupSessions.getOrDefault(groupId, new HashSet<>());
        for (WebSocketSession session : groupSessionSet) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(userId+","+message));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
