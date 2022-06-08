package com.edug.devfinder.models.responses;

import com.edug.devfinder.models.entities.Role;
import com.edug.devfinder.models.entities.User;
import com.edug.devfinder.models.entities.UserTechnology;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
public class UserDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private UUID uuid;
    private String firstName;
    private String lastName;
    private String username;
    private List<UserTechnologyDTO> technologies;
    private List<RoleDTO> roles;
    private boolean enabled;

    public UserDTO(User user) {
        this.uuid = user.getUuid();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.username = user.getUsername();
        this.enabled = user.isEnabled();
    }

    public UserDTO(User user, List<UserTechnology> userTechnologies) {
        this.uuid = user.getUuid();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.username = user.getUsername();
        this.enabled = user.isEnabled();
        this.technologies = userTechnologies != null ?
                userTechnologies.stream()
                        .map(UserTechnologyDTO::new)
                        .collect(Collectors.toList())
                : null;
    }

    public UserDTO(User user, List<UserTechnology> userTechnologies, List<Role> roles) {
        this.uuid = user.getUuid();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.username = user.getUsername();
        this.enabled = user.isEnabled();
        this.technologies = userTechnologies != null ?
                userTechnologies.stream()
                        .map(UserTechnologyDTO::new)
                        .collect(Collectors.toList())
                : null;
        this.roles = roles != null ?
                roles.stream()
                        .map(RoleDTO::new)
                        .collect(Collectors.toList())
                : null;
    }


}
