package com.edug.devfinder.utils;


import com.edug.devfinder.messages.LogMessages;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

public class LoggerUtil {

    public static void logError(Logger log, Throwable throwable, String reason) {
        try {
            var string = new StringBuilder();

            string.append("\n### ERROR ###");

            if (reason != null) {
                string.append("Reason: ")
                        .append(reason)
                        .append("\n");
            }

            string.append("Exception Message: ")
                    .append(throwable.getMessage())
                    .append("\n");

            string.append("Exception Class: ")
                    .append(throwable.getClass().getSimpleName())
                    .append("\n");

            if (throwable.getCause() != null) {
                string.append("Cause: ")
                        .append(throwable.getCause().getMessage())
                        .append("\n");
            }

            string.append("\nStackTrace: ")
                    .append(ExceptionUtils.getStackTrace(throwable));

            string.append("\n### ERROR END ###");
            log.error(string.toString());
        } catch (Exception e) {
            log.error(String.format(LogMessages.FAILED_TO_STDOUT_LOGGER_UTIL,
                    log.getName(),
                    throwable != null ? throwable.getMessage(): "",
                    reason));
        }
    }

    public static void logError(Logger log, Throwable t) {
        logError(log, t, null);
    }

    public static String parseRequest(UUID uuid, String providerName, HttpRequest request, byte[] bytes) {
        var requestBody = new String(bytes, StandardCharsets.ISO_8859_1);
        var requestHeaders = SerializerUtil.toJsonString(request.getHeaders());
        return String.format(LogMessages.HTTP_EXTERNAL_REQUEST, uuid, providerName, request.getURI(),
                request.getMethodValue(), requestHeaders, requestBody);
    }

    public static String parseResponse(UUID uuid, String providerName,
                                       HttpRequest httpRequest, ClientHttpResponse clientHttpResponse,
                                       Optional<Double> timeElapsedInSeconds)
            throws IOException {
        var responseBody = new String(clientHttpResponse.getBody().readAllBytes(), StandardCharsets.ISO_8859_1);
        var responseHeaders = SerializerUtil.toJsonString(clientHttpResponse.getHeaders());

        if (timeElapsedInSeconds.isEmpty()) {
            return String.format(LogMessages.HTTP_EXTERNAL_RESPONSE, uuid, providerName,
                    httpRequest.getURI(), httpRequest.getMethodValue(), responseHeaders, responseBody,
                    clientHttpResponse.getRawStatusCode());
        }
        return String.format(LogMessages.HTTP_EXTERNAL_RESPONSE_WITH_DURATION, uuid, providerName,
                httpRequest.getURI(), httpRequest.getMethodValue(), responseHeaders, responseBody,
                clientHttpResponse.getRawStatusCode(), timeElapsedInSeconds.get());
    }














}