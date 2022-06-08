package com.edug.devfinder.configs.redis.cache;

import java.time.Duration;

public interface CacheTopicsConstants {
    String APPLICATION_CACHE_PREFIX = "dev-finder:cache:";

    String USER_LOGIN_ATTEMPTS = "login-attempts:user";
    String IP_LOGIN_ATTEMPTS = "login-attempts:ip";
    Duration MAX_ATTEMPTS_PER_USERNAME_TTL = Duration.ofMinutes(15);
    Duration MAX_ATTEMPTS_PER_IP_TTL = Duration.ofHours(1);
}
