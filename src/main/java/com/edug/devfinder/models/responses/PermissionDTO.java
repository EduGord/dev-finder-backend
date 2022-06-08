package com.edug.devfinder.models.responses;

import com.edug.devfinder.models.entities.Permission;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Getter
public class PermissionDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String permission;

    public PermissionDTO(Permission permission) {
        this.permission = permission.getAuthority();
    }
}
