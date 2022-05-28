package com.edug.devfinder.models;

import com.edug.devfinder.validators.email.ValidEmail;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
public class AuthRequest {
    @ValidEmail
    private String username;

    private String password;
}
