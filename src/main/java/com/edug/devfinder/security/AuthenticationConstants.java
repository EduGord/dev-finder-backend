package com.edug.devfinder.security;

import com.auth0.jwt.algorithms.Algorithm;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

public interface AuthenticationConstants {
    String DUMMY_SECRET = "☺☻♥♦♣♠•◘○◙♂♀♪♫☼►◄↕‼¶§▬↨↑↓→←∟↔▲▼ ";
    Algorithm ALGORITHM = Algorithm.HMAC512(DUMMY_SECRET.getBytes(StandardCharsets.ISO_8859_1));

    String TOKEN_TYPE = "Bearer";
    String[] AUTHORIZATION_SHOULD_NOT_FILTER_PATTERNS = {
            "/login/**",
            "/user/register",
            "/security/refresh-token/**",
            "/h2-console/**"};

    String[] ADMIN_ONLY_PATTERNS = {
            "/actuator",
            "/actuator/{(?<=health).+(?=)}",
            "/swagger-ui/**",
            "/user/all",
    };

    int REFRESH_TOKEN_DURATION = 12; // hours
    int TOKEN_DURATION = 3; // hours

    int MAX_ATTEMPS_PER_USERNAME = 3;

    int MAX_ATTEMPTS_PER_IP = 12;


}
