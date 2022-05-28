package com.edug.devfinder.services;

import com.edug.devfinder.models.AuthRequest;
import com.edug.devfinder.models.UserRegistrationRequest;
import com.edug.devfinder.models.entities.User;

public interface UserService {
    User login(String username, String rawPassword);
    User register(UserRegistrationRequest userRegistrationRequest);

}
