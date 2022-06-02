package com.edug.devfinder.messages;

public class LogMessages {

    public static final String AUTH_TOKEN_REQUIRED = "Denied Consumer Request, consumer auth token required. [URI=%s, Method=%s, Headers=%s]";

    public static final String INVALID_AUTH_TOKEN = "Denied Consumer Request, consumer auth token is invalid. [URI=%s, Method=%s, Headers=%s]";
    public static final String FAILED_AUTHENTICATION = "Failed authentication. [Username=%s]";

    public static final String HTTP_REQUEST = "Received Request. [UUID=%s, URI=%s, Method=%s, QueryParams=%s, RemoteAddr=%s, Headers=%s, Body=%s]";
    public static final String HTTP_RESPONSE = "Sent Response. [UUID=%s, URI=%s, Method=%s, RemoteAddr=%s, Headers=%s, Body=%s, Status=%s]";

    public static final String HTTP_EXTERNAL_REQUEST = "Requesting External Resource. [UUID=%s, Provider=%s, URI=%s, Method=%s, Headers=%s, Body=%s]";
    public static final String HTTP_EXTERNAL_RESPONSE = "Received Response from External Resource. [UUID=%s, Provider=%s, URI=%s, Method=%s, Headers=%s, Body=%s, Status=%s]";
    public static final String HTTP_EXTERNAL_RESPONSE_WITH_DURATION = "Received Response from External Resource. [UUID=%s, Provider=%s, URI=%s, Method=%s, Headers=%s, Body=%s, Status=%s, Seconds=%s]";

    public static final String FAILED_TO_LOG_STDOUT = "Error attempting to write log. [Exception=%s]";
    public static final String FAILED_TO_STDOUT = "Error attempting to write log. [Exception=%s]";
    public static final String FAILED_TO_STDOUT_LOGGER_UTIL = "Error logging [Log=%s, Throwable=%s, Reason=%s]";

    // cache
    public static final String REDIS_UNABLE_TO_GET_CACHE = "Unable to get from cache [Cache=%s, Exception=%s]";
    public static final String REDIS_UNABLE_TO_PUT_CACHE = "Unable to put into cache [Cache=%s, Exception=%s]";
    public static final String REDIS_UNABLE_TO_EVICT_CACHE = "Unable to evict from cache [Cache=%s, Exception=%s]";
    public static final String REDIS_UNABLE_TO_CLEAN_CACHE = "Unable to clean cache [Cache=%s, Exception=%s]";
    public static final String REDIS_TIMEOUT = "Redis Timeout. [Exception=%s]";
    public static final String REDIS_REFRESHING_CACHE = "Refreshing cache [Cache=%s]";

    public static final String CACHE_EVICTION_SCHEDULER_CLEAR_GROUP = "Clearing cache group [Cache=%s]";

}
