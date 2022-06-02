package com.edug.devfinder.repositories;

import com.edug.devfinder.configs.redis.cache.CacheTopicsConstants;
import com.edug.devfinder.security.AuthenticationConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class LoginAttemptsIpRepository implements AuthenticationConstants, CacheTopicsConstants {
    private final StringRedisTemplate redisTemplate;

    public void increment(String ip) {
        var key = String.join(":", IP_LOGIN_ATTEMPTS, ip);
        redisTemplate.opsForValue().increment(key);
        redisTemplate.expire(key, MAX_ATTEMS_PER_IP_TTL);
    }

    public Integer getCounter(String ip) {
        var result = redisTemplate.opsForValue().get(String.join(":", IP_LOGIN_ATTEMPTS, ip));
        return result != null ? Integer.parseInt(result) : null;

    }
}
