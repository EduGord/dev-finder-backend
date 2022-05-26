package com.edug.devfinder.messages;

public class LogMessages {

    public static final String HTTP_REQUEST = "Received Request. [UUID=%s, URI=%s, Method=%s, RemoteAddr=%s, Headers=%s, Body=%s]";
    public static final String HTTP_RESPONSE = "Sent Response. [UUID=%s, URI=%s, Method=%s, RemoteAddr=%s, Headers=%s, Body=%s, Status=%s]";

    public static final String HTTP_EXTERNAL_REQUEST = "Requesting External Resource. [UUID=%s, Provider=%s, URI=%s, Method=%s, Headers=%s, Body=%s]";
    public static final String HTTP_EXTERNAL_RESPONSE = "Received Response from External Resource. [UUID=%s, Provider=%s, URI=%s, Method=%s, Headers=%s, Body=%s, Status=%s]";
    public static final String HTTP_EXTERNAL_RESPONSE_WITH_DURATION = "Received Response from External Resource. [UUID=%s, Provider=%s, URI=%s, Method=%s, Headers=%s, Body=%s, Status=%s, Seconds=%s]";

    public static final String FAILED_TO_LOG_STDOUT = "Error attempting to write log. [Exception=%s]";
    public static final String FAILED_TO_STDOUT = "Error attempting to write log. [Exception=%s]";
    public static final String FAILED_TO_STDOUT_LOGGER_UTIL = "Error logging [Log=%s, Throwable=%s, Reason=%s]";
}
