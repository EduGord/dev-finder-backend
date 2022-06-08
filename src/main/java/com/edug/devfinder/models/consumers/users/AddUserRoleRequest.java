package com.edug.devfinder.models.consumers.users;

import com.edug.devfinder.models.enums.RolesEnum;
import com.edug.devfinder.validators.email.ValidEmail;
import com.edug.devfinder.validators.enums.NameOfEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@AllArgsConstructor
public class AddUserRoleRequest {
    @ValidEmail
    private String username;
    @NotBlank
    @NameOfEnum(enumClass = RolesEnum.class)
    private String role;

    public RolesEnum getRoleEnum() {
        return RolesEnum.valueOf(role);
    }
}
