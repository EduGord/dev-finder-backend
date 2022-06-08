package com.edug.devfinder.models.consumers.users;

import com.edug.devfinder.validators.email.ValidEmail;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
public class AuthRequest {
    @ValidEmail
    private String username;

    @NotBlank
    private String password;
}
