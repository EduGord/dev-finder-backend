package com.edug.devfinder.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.edug.devfinder.security.AuthenticationConstants;
import com.edug.devfinder.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
public class SecurityService {
    private static PasswordEncoder encoder;
    private final UserDetailsService userDetailsService;

    private final HttpServletRequest request;

    public SecurityService(@Qualifier("passwordEncoder") PasswordEncoder passwordEncoder,
                           UserDetailsService userDetailsService,
                           HttpServletRequest httpServletRequest) {
        SecurityService.encoder = passwordEncoder;
        this.request = httpServletRequest;
        this.userDetailsService = userDetailsService;
    }

    public String encode(String input){
        return encoder.encode(input);
    }

    public boolean checkPassword(String rawPassword, String encryptedPassword){
        return encoder.matches(rawPassword, encryptedPassword);
    }

    @Transactional
    public Map<String, String> refreshToken(String refreshToken) throws JWTVerificationException {
        var verifier = JWT.require(AuthenticationConstants.ALGORITHM).build();
        var decodedJwt = verifier.verify(refreshToken);
        var username = decodedJwt.getSubject();
        var userPrincipal = userDetailsService.loadUserByUsername(username);

        var authorities = userPrincipal.getAuthorities();
        var accessToken = JwtUtil.createToken(request, username, authorities);
        Map<String, String> token = new HashMap<>();
        token.put("accessToken", accessToken);
        return token;
    }
}
