package com.edug.devfinder.configs;

import com.edug.devfinder.messages.LogMessages;
import net.minidev.json.JSONObject;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ClassUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

@Component
public class HttpLoggingFilter extends OncePerRequestFilter {
    private final Logger log = LoggerFactory.getLogger(ClassUtils.getUserClass(this.getClass()));

    private final String[] SHOULD_NOT_FILTER_PATTERNS = {"/actuator/**", "/swagger-ui/**", "/h2-console/**", "/favicon.ico"};
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return Arrays.stream(SHOULD_NOT_FILTER_PATTERNS)
                .anyMatch(pattern -> antPathMatcher.match(pattern, request.getServletPath()));
    }

    private String getRemoteAddr(ContentCachingRequestWrapper request) {
        return request.getHeader("X-FORWARDED-FOR") != null ? request.getHeader("X-FORWARDED-FOR") : request.getRemoteAddr();
    }

    private void logRequest(UUID uuid, ContentCachingRequestWrapper request) {
        byte[] reqBytes = request.getContentAsByteArray();
        String reqBody = new String(reqBytes, StandardCharsets.ISO_8859_1);
        var remoteAddr = getRemoteAddr(request);
        var params = JSONObject.toJSONString(request.getParameterMap());
        var reqHeaders = new JSONObject();

        Collections.list(request.getHeaderNames())
                .forEach(key ->
                        Collections.list(request.getHeaders(key))
                                .forEach(value -> reqHeaders.put(key, value)));

        var logMessage = String.format(LogMessages.HTTP_REQUEST, uuid, request.getRequestURI(), request.getMethod(), params, remoteAddr, reqHeaders, reqBody);

        log.trace(logMessage);
    }

    private void logResponse(UUID uuid, ContentCachingRequestWrapper request, ContentCachingResponseWrapper response) {
        byte[] respBytes = response.getContentAsByteArray();
        String respBody = new String(respBytes, StandardCharsets.ISO_8859_1);
        var respHeaders = new JSONObject();

        response.getHeaderNames().forEach(key ->
                response.getHeaders(key).forEach(value -> respHeaders.put(key, value)));

        var remoteAddr = getRemoteAddr(request);

        var logMessage = String.format(LogMessages.HTTP_RESPONSE, uuid, request.getRequestURI(),
                request.getMethod(), remoteAddr, respHeaders, respBody, response.getStatus());

        log.trace(logMessage);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        ContentCachingRequestWrapper req = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper resp = new ContentCachingResponseWrapper(response);

        // Free request chain
        filterChain.doFilter(req, resp);

        UUID uuid = UUID.nameUUIDFromBytes(ArrayUtils.addAll(req.getContentAsByteArray(), resp.getContentAsByteArray()));
        logRequest(uuid, req);
        logResponse(uuid, req, resp);

        // Finally remember to respond to the client with the cached data.
        resp.copyBodyToResponse();
    }
}
