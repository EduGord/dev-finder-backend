package com.edug.devfinder.configs.redis.cache;

public interface CacheTopicsConstants {
    String APPLICATION_CACHE_PREFIX = "dev-finder:cache:";

    String USER_LOGIN_ATTEMPTS = "login-attempts:user";
    String IP_LOGIN_ATTEMPTS = "login-attempts:ip";
}
