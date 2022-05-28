package com.edug.devfinder.security;

import com.edug.devfinder.messages.LogMessages;
import com.edug.devfinder.messages.MessagesEnum;
import com.edug.devfinder.repositories.PermissionRepository;
import com.edug.devfinder.repositories.UserRepository;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CustomAuthenticationProvider implements AuthenticationProvider, AuthenticationManager {

    private final Logger log = LoggerFactory.getLogger(ClassUtils.getUserClass(this.getClass()));

    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var username = authentication.getName();
        var password = authentication.getCredentials().toString();
        var user = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException(MessagesEnum.USER_NOT_FOUND.getMessage()));
        var permissions = permissionRepository.findAllDistinctByUser_username(username);

        if (passwordEncoder.matches(password, user.getPassword()))
            return new UsernamePasswordAuthenticationToken(username, password, permissions);

        log.trace(String.format(LogMessages.FAILED_AUTHENTICATION, username));
        throw new BadCredentialsException(MessagesEnum.INVALID_CREDENTIALS.getMessage());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
