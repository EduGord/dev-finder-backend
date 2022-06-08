package com.edug.devfinder.configs.redis.cache;

import com.edug.devfinder.security.AuthenticationConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.ReadFrom;
import io.lettuce.core.SocketOptions;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import io.lettuce.core.resource.Delay;
import io.lettuce.core.resource.DirContextDnsResolver;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.ClassUtils;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
@ConfigurationProperties(prefix = "spring.redis")
@EnableCaching
@Setter
public class RedisConfig implements RedisConfigConstants, CacheTopicsConstants, AuthenticationConstants {
    private final Logger log = LoggerFactory.getLogger(ClassUtils.getUserClass(this.getClass()));

    private final RedisProperties properties;
    private final ObjectMapper objectMapper;

    @Resource
    private ClusterConfigProperties clusterConfigProperties;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    public RedisConfig(RedisProperties properties,
                       @Qualifier("redisObjectMapper") ObjectMapper redisObjectMapper) {
        this.properties = properties;
        this.objectMapper = redisObjectMapper;
    }

    @Bean
    SocketOptions socketOptions() {
        return SocketOptions.builder()
                .connectTimeout(properties.getConnectTimeout())
                .build();
    }

    @Bean
    ClusterClientOptions clusterClientOptions() {
        return ClusterClientOptions.builder()
                .topologyRefreshOptions(this.clusterTopologyRefreshOptions())
                .socketOptions(this.socketOptions())
                .autoReconnect(false)
                .suspendReconnectOnProtocolFailure(true)
                .pingBeforeActivateConnection(true)
                .disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS)
                .build();
    }

    @Bean(destroyMethod = "shutdown")
    ClientResources clientResources() {
        return DefaultClientResources
                .builder()
                .dnsResolver(new DirContextDnsResolver())
                .reconnectDelay(Delay.fullJitter(
                        Duration.ofMillis(RECONNECT_DELAY_MINIMUM),
                        Duration.ofSeconds(RECONNECT_DELAY_MAXIMUM),
                        RECONNECT_DELAY_BASE, TimeUnit.MILLISECONDS))
                .build();
    }

    @Bean
//    @Profile("!local")
    RedisClusterConfiguration redisClusterConfiguration() {
        log.info("Initializing Redis Cluster Configuration instance.");
        return new RedisClusterConfiguration(clusterConfigProperties.getNodes());
    }

//    @Bean
//    @Profile("local")
//    RedisStandaloneConfiguration redisStandaloneConfiguration() {
//        var config = new RedisStandaloneConfiguration(properties.getHost(), properties.getPort());
//        config.setPassword(RedisPassword.of(properties.getPassword()));
//        config.setUsername(properties.getUsername());
//        return config;
//    }

    // Writes to master, reads from a replica if available
    @Bean
    LettuceClientConfiguration lettuceClientConfiguration() {
        return LettuceClientConfiguration.builder()
                .readFrom(ReadFrom.REPLICA_PREFERRED)
                .clientResources(this.clientResources())
                .clientOptions(this.clusterClientOptions())
                .shutdownTimeout(properties.getConnectTimeout())
                .commandTimeout(properties.getConnectTimeout())
                .build();
    }

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        LettuceConnectionFactory conn;
//        if (activeProfile.equals("local")) {
//            log.info("Initializing Redis Connection Factory using Standalone Configuration");
//            conn = new LettuceConnectionFactory(redisStandaloneConfiguration(),
//                    this.lettuceClientConfiguration());
//        } else {
            log.info("Initializing Redis Connection Factory using Cluster Configuration");
            conn = new LettuceConnectionFactory(redisClusterConfiguration(),
                    this.lettuceClientConfiguration());
//        }
        conn.setValidateConnection(true);
        return conn;
    }


    @Bean
    public ClusterTopologyRefreshOptions clusterTopologyRefreshOptions() {
        return ClusterTopologyRefreshOptions.builder()
                .enablePeriodicRefresh(Duration.ofMinutes(PERIODIC_REFRESH))
                .dynamicRefreshSources(false)
                .enableAllAdaptiveRefreshTriggers()
                .refreshTriggersReconnectAttempts(RECONNECT_ATTEMPS)
                .build();
    }

    @Bean
    public RedisSerializer<Object> redisSerializer() {
        GenericJackson2JsonRedisSerializer.registerNullValueSerializer(objectMapper, null);
        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }

    @Bean
    RedisCacheConfiguration redisCacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .prefixCacheNameWith(APPLICATION_CACHE_PREFIX)
                .disableCachingNullValues()
                .entryTtl(Duration.ofHours(DEFAULT_TTL))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(this.redisSerializer()));
    }

    @Bean
    RedisCacheManager redisCacheManager() {
        var redisCacheManager = RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(this.redisConnectionFactory())
                .cacheDefaults(this.redisCacheConfiguration())
                .withCacheConfiguration(USER_LOGIN_ATTEMPTS,
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(MAX_ATTEMPTS_PER_USERNAME_TTL))
                .withCacheConfiguration(IP_LOGIN_ATTEMPTS,
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(MAX_ATTEMPTS_PER_IP_TTL));
//                .transactionAware();
        return redisCacheManager.build();
    }
}
