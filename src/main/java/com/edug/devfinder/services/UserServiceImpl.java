package com.edug.devfinder.services;

import com.edug.devfinder.exceptions.ApplicationException;
import com.edug.devfinder.messages.MessagesEnum;
import com.edug.devfinder.models.consumers.users.UserRegistrationRequest;
import com.edug.devfinder.models.entities.User;
import com.edug.devfinder.models.enums.RolesEnum;
import com.edug.devfinder.repositories.RoleRepository;
import com.edug.devfinder.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final SecurityService securityService;

    public void addRoleToUser(String username, String roleName) throws EntityNotFoundException {
       var user = userRepository.findByUsernameIgnoreCase(username)
               .orElseThrow(()-> new UsernameNotFoundException(MessagesEnum.USER_NOT_FOUND.getMessage()));
       var role = roleRepository.findByRole(RolesEnum.valueOf(roleName.toUpperCase()))
               .orElseThrow(()-> new EntityNotFoundException("Role not found."));
       if (!user.getRoles().contains(role)) {
           user.getRoles().add(role);
           userRepository.save(user);
       }
    }

    public User findSelf() throws AuthenticationException {
        var username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(()-> new UsernameNotFoundException(MessagesEnum.USER_NOT_FOUND.getMessage()));
    }

    public User findUser(String username, String rawPassword) throws AuthenticationException {
        var user = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(()-> new UsernameNotFoundException(MessagesEnum.USER_NOT_FOUND.getMessage()));
        if (securityService.checkPassword(rawPassword, user.getPassword()))
            return user;
        else
            throw new BadCredentialsException("Password is invalid.");
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User register(UserRegistrationRequest userRegistrationRequest) throws EntityNotFoundException, ApplicationException {
        var existingUser = userRepository.findByUsernameIgnoreCase(userRegistrationRequest.getEmail());
        if (existingUser.isPresent())
            throw new ApplicationException("User already exists.");
        var userRole = roleRepository.findByRole(RolesEnum.USER)
                .orElseThrow(()-> new EntityNotFoundException("Role not found."));
        var user = User.builder()
                .username(userRegistrationRequest.getEmail())
                .password(securityService.encode(userRegistrationRequest.getRawPassword()))
                .uuid(UUID.randomUUID())
                .firstName(userRegistrationRequest.getFirstName())
                .lastName(userRegistrationRequest.getLastName())
                .roles(List.of(userRole))
                .build();
        return userRepository.save(user);
    }
}
