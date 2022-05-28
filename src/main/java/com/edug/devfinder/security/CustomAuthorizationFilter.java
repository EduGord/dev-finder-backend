package com.edug.devfinder.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.edug.devfinder.messages.MessagesEnum;
import com.edug.devfinder.utils.LoggerUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ClassUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CustomAuthorizationFilter extends OncePerRequestFilter implements AuthenticationConstants {
    private final Logger log = LoggerFactory.getLogger(ClassUtils.getUserClass(this.getClass()));
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
    private final ObjectMapper objectMapper;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return Arrays.stream(AUTHORIZATION_SHOULD_NOT_FILTER_PATTERNS)
                .anyMatch(pattern -> antPathMatcher.match(pattern, request.getServletPath()));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, JWTVerificationException, AuthorizationServiceException {
        var authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            var token = authorizationHeader.substring("Bearer ".length());
            var verifier = JWT.require(ALGORITHM).build();
            try {
                var decodedJwt = verifier.verify(token);
                var username = decodedJwt.getSubject();
                var permissions = decodedJwt.getClaim("authorities").asArray(String.class);

                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                Stream.of(permissions).map(SimpleGrantedAuthority::new).forEach(authorities::add);
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, null, authorities));

                filterChain.doFilter(request, response);
            } catch (JWTVerificationException e) {
                var errors = new HashMap<String, Object>();
                var error = MessagesEnum.INVALID_JWT_TOKEN.toMap(e);
                errors.put("Errors", List.of(error));
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                objectMapper.writeValue(response.getOutputStream(), errors);
                LoggerUtil.logError(log, e);
            }
        } else {
            var exception = new AuthorizationServiceException(MessagesEnum.AUTHORIZATION_HEADERS_NOT_PRESENT.getMessage());
            var errors = new HashMap<String, Object>();
            var error = MessagesEnum.AUTH_TOKEN_REQUIRED.toMap(exception);
            errors.put("Errors", List.of(error));
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            objectMapper.writeValue(response.getOutputStream(), errors);
            LoggerUtil.logError(log, exception);
        }
    }
}
