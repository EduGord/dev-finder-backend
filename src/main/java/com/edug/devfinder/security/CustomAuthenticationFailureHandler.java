package com.edug.devfinder.security;

import com.edug.devfinder.exceptions.LoginAttemptBlockedException;
import com.edug.devfinder.messages.MessagesEnum;
import com.edug.devfinder.services.LoginAttemptsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class CustomAuthenticationFailureHandler
        implements AuthenticationFailureHandler {

    private final Logger log = LoggerFactory.getLogger(ClassUtils.getUserClass(this.getClass()));
    private final ObjectMapper objectMapper;

    private final LoginAttemptsService loginAttemptsService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws ServletException, IOException {

        String ip = request.getHeader("X-FORWARDED-FOR") != null ? request.getHeader("X-FORWARDED-FOR") : request.getRemoteAddr();

        var errors = new HashMap<String, Object>();
        var error = new HashMap<String, Object>();

        if (exception instanceof LoginAttemptBlockedException) {
            error = MessagesEnum.LOGIN_ATTEMPTS_EXCEEDED_LIMIT.toMap(exception);
        } else {
            error =  MessagesEnum.INVALID_CREDENTIALS.toMap(exception);
            loginAttemptsService.onAuthenticationFailure(request.getParameter("username"), ip);
        }

        errors.put("Errors", List.of(error));
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        objectMapper.writeValue(response.getOutputStream(), errors);
    }
}
