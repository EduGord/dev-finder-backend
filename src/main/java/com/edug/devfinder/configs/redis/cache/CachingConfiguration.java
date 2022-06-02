package com.edug.devfinder.configs.redis.cache;

import com.edug.devfinder.messages.LogMessages;
import io.lettuce.core.RedisCommandTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ClassUtils;

@Configuration
public class CachingConfiguration extends CachingConfigurerSupport {

    @Override
    public CacheErrorHandler errorHandler() {
        return new RedisCacheErrorHandler();
    }

    public static class RedisCacheErrorHandler implements CacheErrorHandler {
        private final Logger log = LoggerFactory.getLogger(ClassUtils.getUserClass(this.getClass()));

        @Override
        public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
            handleTimeOutException(exception);
            log.warn(String.format(LogMessages.REDIS_UNABLE_TO_GET_CACHE, cache.getName(), exception.getMessage()));
        }

        @Override
        public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
            handleTimeOutException(exception);
            log.warn(String.format(LogMessages.REDIS_UNABLE_TO_PUT_CACHE, cache.getName(), exception.getMessage()));
        }

        @Override
        public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
            handleTimeOutException(exception);
            log.warn(String.format(LogMessages.REDIS_UNABLE_TO_EVICT_CACHE, cache.getName(), exception.getMessage()));
        }

        @Override
        public void handleCacheClearError(RuntimeException exception, Cache cache) {
            handleTimeOutException(exception);
            log.warn(String.format(LogMessages.REDIS_UNABLE_TO_CLEAN_CACHE, cache.getName(), exception.getMessage()));
        }

        private void handleTimeOutException(RuntimeException exception) {

            if (exception instanceof RedisCommandTimeoutException) {
                log.warn(String.format(LogMessages.REDIS_TIMEOUT, exception.getMessage()));
            }
        }
    }
}