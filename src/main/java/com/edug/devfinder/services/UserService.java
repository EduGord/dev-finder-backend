package com.edug.devfinder.services;

import com.edug.devfinder.exceptions.ApplicationException;
import com.edug.devfinder.models.AuthRequest;
import com.edug.devfinder.models.UserRegistrationRequest;
import com.edug.devfinder.models.entities.Role;
import com.edug.devfinder.models.entities.User;
import com.edug.devfinder.models.security.RolesEnum;
import com.edug.devfinder.repositories.RoleRepository;
import com.edug.devfinder.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EncryptionService encryptionService;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String email, String roleName) throws EntityNotFoundException {
       var user = getUser(email).orElseThrow(()-> new EntityNotFoundException("User not found."));
       var role = getRole(roleName).orElseThrow(()-> new EntityNotFoundException("Role not found."));
       if (!user.getRoles().contains(role))
           user.getRoles().add(role);
       userRepository.save(user);
    }

    @Override
    public Optional<Role> getRole(String roleName) {
        return roleRepository.findByRole(RolesEnum.parse(roleName));
    }

    @Override
    public Optional<User> getUser(String email) {
       return userRepository.findByEmailIgnoreCase(email);
    }

    @Override
    public void throwsIfExistingUser(String email) throws ApplicationException {
        var existingUser = userRepository.findByEmailIgnoreCase(email);
        if (existingUser.isPresent())
            throw new ApplicationException("User already exists");
    }

    @Override
    public boolean signIn(AuthRequest authRequest) throws EntityNotFoundException {
        var user = getUser(authRequest.getEmail())
                .orElseThrow(()-> new EntityNotFoundException("User not found."));
        return encryptionService.checkPassword(authRequest.getRawPassword(), user.getPassword());
    }

    @Override
    public User register(UserRegistrationRequest userRegistrationRequest) throws EntityNotFoundException {
        throwsIfExistingUser(userRegistrationRequest.getEmail());
        var userRole = getRole(RolesEnum.USER.name()).orElseThrow(()-> new EntityNotFoundException("Role not found."));
        var user = User.builder()
                .uuid(UUID.randomUUID())
                .firstName(userRegistrationRequest.getFirstName())
                .lastName(userRegistrationRequest.getLastName())
                .email(userRegistrationRequest.getEmail())
                .password(encryptionService.encode(userRegistrationRequest.getRawPassword()))
                .roles(List.of(userRole))
                .build();
        return userRepository.save(user);
    }

    public List<User> listAll() {
        return userRepository.findAll();
    }
}
