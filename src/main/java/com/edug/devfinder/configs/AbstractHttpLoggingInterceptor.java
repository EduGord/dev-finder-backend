package com.edug.devfinder.configs;

import com.edug.devfinder.utils.LoggerUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;


public abstract class AbstractHttpLoggingInterceptor implements ClientHttpRequestInterceptor {

    private UUID getNameUUIDFromBytes(String providerName, HttpRequest request, byte[] bytes) {
        var mergedBytes = Stream.of(providerName, request.getURI().toString(), request.getMethodValue(),
                        request.getHeaders().toString())
                .map(string-> string.getBytes(StandardCharsets.ISO_8859_1))
                .reduce(bytes, ArrayUtils::addAll);
        return UUID.nameUUIDFromBytes(mergedBytes);
    }

    // logs the external http request
    protected UUID preExecute(Logger log, String providerName, HttpRequest request, byte[] bytes) {
        var uuid = this.getNameUUIDFromBytes(providerName, request, bytes);
        log.trace(LoggerUtil.parseRequest(uuid, providerName, request, bytes));
        return uuid;
    }

    // logs the external http response
    protected void postExecute(Logger log, String providerName, UUID uuid,
                               HttpRequest request, ClientHttpResponse response,
                               Optional<Double> timeElapsedInSeconds) throws IOException {
        var logMessage = LoggerUtil.parseResponse(uuid, providerName, request, response, timeElapsedInSeconds);
        log.trace(logMessage);
    }
}
