package com.edug.devfinder.services;

import com.edug.devfinder.repositories.LoginAttemptsIpRepository;
import com.edug.devfinder.repositories.LoginAttemptsUserRepository;
import com.edug.devfinder.security.AuthenticationConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class LoginAttemptsService implements AuthenticationConstants {

    private final LoginAttemptsUserRepository loginAttemptsUserRepository;
    private final LoginAttemptsIpRepository loginAttemptsIpRepository;

    public void onAuthenticationFailure(String username, String ip) {
        loginAttemptsIpRepository.increment(ip);
        loginAttemptsUserRepository.increment(username);
    }

    public boolean isBlocked(String username, String ip) {
        try {
            return loginAttemptsUserRepository.getCounter(username) >= MAX_ATTEMPS_PER_USERNAME
                    ||
                    loginAttemptsIpRepository.getCounter(ip) >= MAX_ATTEMPTS_PER_IP;
        } catch (Exception ignored) {
            return false;
        }
    }
}