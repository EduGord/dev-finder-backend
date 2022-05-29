package com.edug.devfinder.security;

import com.edug.devfinder.messages.LogMessages;
import com.edug.devfinder.messages.MessagesEnum;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CustomAuthenticationProvider implements AuthenticationProvider, AuthenticationManager {

    private final Logger log = LoggerFactory.getLogger(ClassUtils.getUserClass(this.getClass()));
    private final PasswordEncoder passwordEncoder;

    private final UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var username = authentication.getName();
        var password = authentication.getCredentials().toString();
        var userPrincipal = userDetailsService.loadUserByUsername(username);
        var authorities = userPrincipal.getAuthorities();
        if (passwordEncoder.matches(password, userPrincipal.getPassword()))
            return new UsernamePasswordAuthenticationToken(username, password, authorities);
        log.trace(String.format(LogMessages.FAILED_AUTHENTICATION, username));
        throw new BadCredentialsException(MessagesEnum.INVALID_CREDENTIALS.getMessage());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
