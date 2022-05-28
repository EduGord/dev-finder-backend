package com.edug.devfinder.security;

import com.auth0.jwt.algorithms.Algorithm;

import java.nio.charset.StandardCharsets;

public interface AuthenticationConstants {
    String DUMMY_SECRET = "☺☻♥♦♣♠•◘○◙♂♀♪♫☼►◄↕‼¶§▬↨↑↓→←∟↔▲▼ ";
    Algorithm ALGORITHM = Algorithm.HMAC512(DUMMY_SECRET.getBytes(StandardCharsets.UTF_8));
    String[] AUTHORIZATION_SHOULD_NOT_FILTER_PATTERNS = {"/login", "/user/refresh-token**"};
    String[] ADMIN_ONLY_PATTERNS = {
            "/actuator",
            "/actuator/{(?<=health).+(?=)}",
            "/swagger-ui/**"
    };
}
