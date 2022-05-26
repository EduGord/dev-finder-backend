package com.edug.devfinder.models;

import com.edug.devfinder.validators.email.ValidEmail;
import com.edug.devfinder.validators.password.ValidPassword;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
public class UserRegistrationRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @ValidEmail
    private String email;

    @ValidPassword
    private String rawPassword;

    public String getFirstName() {
        return firstName.replaceAll("[^A-Za-z]+", "");
    }

    public String getLastName() {
        return lastName.replaceAll("[^A-Za-z]+", "");
    }

    public String getEmail() {
        return email.replaceAll("\\s+", "");
    }

    public String getRawPassword()  { return rawPassword; }
}
