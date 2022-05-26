package com.edug.devfinder.services;

import com.edug.devfinder.exceptions.ApplicationException;
import com.edug.devfinder.models.AuthRequest;
import com.edug.devfinder.models.UserRegistrationRequest;
import com.edug.devfinder.models.entities.Role;
import com.edug.devfinder.models.entities.User;

import java.util.Optional;

public interface IUserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    Optional<User> getUser(String username);

    Optional<Role> getRole(String roleName);

    void throwsIfExistingUser(String email);

    boolean signIn(AuthRequest authRequest);
    User register(UserRegistrationRequest userRegistrationRequest);

}
