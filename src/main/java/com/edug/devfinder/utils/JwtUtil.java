package com.edug.devfinder.utils;

import com.auth0.jwt.JWT;
import com.edug.devfinder.security.AuthenticationConstants;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Collection;
import java.util.stream.Collectors;

public class JwtUtil implements AuthenticationConstants {

    public static String createToken(HttpServletRequest request,
                              String username,
                              Collection<? extends GrantedAuthority> authorities) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(DateUtils.addHours(Calendar.getInstance().getTime(), TOKEN_DURATION))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("authorities", authorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .sign(ALGORITHM);

    }

    public static String createRefreshToken(HttpServletRequest request,
                                     String username) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(DateUtils.addDays(Calendar.getInstance().getTime(), REFRESH_TOKEN_DURATION))
                .withIssuer(request.getRequestURL().toString())
                .sign(ALGORITHM);
    }

}
