package com.edug.devfinder.models.responses;

import com.edug.devfinder.models.entities.Permission;
import com.edug.devfinder.models.entities.Role;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class RoleDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String role;
    private List<PermissionDTO> permissions;

    public RoleDTO(Role role) {
        this.role = role.getName();
    }

    public RoleDTO(Role role, List<Permission> permissions) {
        this.role = role.getName();
        this.permissions = permissions.stream()
                .map(PermissionDTO::new)
                .collect(Collectors.toList());
    }
}
