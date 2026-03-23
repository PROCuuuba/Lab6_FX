package com.example.lab6_fx.Service;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    private Map<String, UserSession> sessions = new HashMap<>();

    public UserSession getSession(String userId, UserSession session) {
        return sessions.computeIfAbsent(userId, k -> session);
    }
}