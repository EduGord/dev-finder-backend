package com.edug.devfinder.models.consumers.users;

import com.edug.devfinder.validators.email.ValidEmail;
import com.edug.devfinder.validators.password.ValidPassword;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Builder
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

    public String getPassword(PasswordEncoder passwordEncoder) {
        return passwordEncoder.encode(this.rawPassword);
    }
}
