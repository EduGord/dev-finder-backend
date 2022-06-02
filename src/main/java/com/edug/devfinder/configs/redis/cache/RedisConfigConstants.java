package com.edug.devfinder.configs.redis.cache;

public interface RedisConfigConstants {

    int PERIODIC_REFRESH                = 10; // minutes
    int RECONNECT_ATTEMPS               = 5;
    int DEFAULT_TTL                     = 3;  // hours

    int RECONNECT_DELAY_MINIMUM = 80;         // miliseconds
    int RECONNECT_DELAY_MAXIMUM = 5;          // seconds
    int RECONNECT_DELAY_BASE = 100;           // miliseconds

}