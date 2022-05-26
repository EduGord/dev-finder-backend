package com.edug.devfinder.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class EncryptionService {
    private final String SECRET = "topSecretConfidentialRestrictedUnrevealedUndisclosedUnpublishedUntolsUnknownSecret";
    private final Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder(SECRET, 200000, 512);

    public String encode(String input){
        return encoder.encode(input);
    }

    public boolean checkPassword(String rawPassword, String encryptedPassword){
        return encoder.matches(rawPassword, encryptedPassword);
    }
}
