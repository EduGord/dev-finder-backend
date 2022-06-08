package com.edug.devfinder.services;

import com.edug.devfinder.models.consumers.users.UserRegistrationRequest;
import com.edug.devfinder.models.enums.RolesEnum;
import com.edug.devfinder.models.responses.RoleDTO;
import com.edug.devfinder.models.responses.UserDTO;

import java.util.Collection;

public interface UserService {
    UserDTO register(UserRegistrationRequest userRegistrationRequest);
    Collection<RoleDTO> addRoleToUser(String username, RolesEnum rolesEnum);
}
