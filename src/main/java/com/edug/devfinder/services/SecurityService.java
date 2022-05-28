package com.edug.devfinder.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.edug.devfinder.security.AuthenticationConstants;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class SecurityService {
    private static PasswordEncoder encoder;
    private final HttpServletRequest request;

    public SecurityService(@Qualifier("passwordEncoder") PasswordEncoder passwordEncoder,
                           HttpServletRequest httpServletRequest) {
        SecurityService.encoder = passwordEncoder;
        this.request = httpServletRequest;
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
        String username = decodedJwt.getSubject();
        String[] authorities = decodedJwt.getClaim("authorities").asArray(String.class);
        var accessToken = JWT.create()
                .withSubject(username)
                .withExpiresAt(DateUtils.addHours(Calendar.getInstance().getTime(), 3))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("authorities", new ArrayList<>(Arrays.asList(authorities)))
                .sign(AuthenticationConstants.ALGORITHM);
        Map<String, String> token = new HashMap<>();
        token.put("accessToken", accessToken);
        return token;
    }
}
