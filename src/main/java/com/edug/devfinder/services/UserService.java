package com.edug.devfinder.services;

import com.edug.devfinder.models.consumers.users.UserRegistrationRequest;
import com.edug.devfinder.models.entities.User;

public interface UserService {
    User register(UserRegistrationRequest userRegistrationRequest);
}
