package com.edug.devfinder.repositories;

import com.edug.devfinder.configs.redis.cache.CacheTopicsConstants;
import com.edug.devfinder.security.AuthenticationConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class LoginAttemptsUserRepository implements AuthenticationConstants, CacheTopicsConstants {
    private final StringRedisTemplate redisTemplate;

    public void increment(String username) {
        var key = String.join(":", USER_LOGIN_ATTEMPTS, username);
        redisTemplate.opsForValue().increment(key);
        redisTemplate.expire(key, MAX_ATTEMS_PER_USERNAME_TTL);
    }

    public Integer getCounter(String username) {
        var result = redisTemplate.opsForValue().get(String.join(":", USER_LOGIN_ATTEMPTS, username));
        return result != null ? Integer.parseInt(result) : null;
    }
}