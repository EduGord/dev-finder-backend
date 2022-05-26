package com.edug.devfinder.services;

import com.edug.devfinder.exceptions.ApplicationException;
import com.edug.devfinder.models.AuthRequest;
import com.edug.devfinder.models.UserRegistrationRequest;
import com.edug.devfinder.models.entities.User;
import com.edug.devfinder.models.security.RolesEnum;
import com.edug.devfinder.repositories.RoleRepository;
import com.edug.devfinder.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EncryptionService encryptionService;

    private void throwsIfExistingUser(String email) throws ApplicationException {
        var existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent())
            throw new ApplicationException("Usuário já existe");
    }

    public boolean signIn(AuthRequest authRequest) {
        var user = userRepository.findByEmail(authRequest.getEmail())
                .orElseThrow(()-> new RuntimeException("Usuário não encontrado."));
        return encryptionService.checkPassword(authRequest.getPassword(), user.getPassword());
    }

    public User register(UserRegistrationRequest userRegistrationRequest) throws ApplicationException {
        throwsIfExistingUser(userRegistrationRequest.getEmail());
        var userRole = roleRepository.findByRole(RolesEnum.USER);
        var user = User.builder()
                .firstName(userRegistrationRequest.getFirstName())
                .lastName(userRegistrationRequest.getLastName())
                .email(userRegistrationRequest.getEmail())
                .password(encryptionService.encode(userRegistrationRequest.getRawPassword()))
                .roles(userRole
                        .map(List::of)
                        .orElseGet(ArrayList::new))
                .build();
        ;
        return userRepository.save(user);
    }

    public List<User> listAll() {
        return userRepository.findAll();
    }
}
