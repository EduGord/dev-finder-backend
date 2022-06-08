package com.edug.devfinder.services;

import com.edug.devfinder.exceptions.ApplicationException;
import com.edug.devfinder.messages.MessagesEnum;
import com.edug.devfinder.models.consumers.users.UserRegistrationRequest;
import com.edug.devfinder.models.entities.Role;
import com.edug.devfinder.models.entities.User;
import com.edug.devfinder.models.enums.RolesEnum;
import com.edug.devfinder.models.responses.RoleDTO;
import com.edug.devfinder.models.responses.UserDTO;
import com.edug.devfinder.repositories.RoleRepository;
import com.edug.devfinder.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final SecurityService securityService;

    @Transactional
    public Collection<RoleDTO> addRoleToUser(String username, RolesEnum rolesEnum) throws UsernameNotFoundException,
            EntityNotFoundException {
        var user = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException(MessagesEnum.USER_NOT_FOUND.getMessage()));
        var role = roleRepository.findByRole(rolesEnum)
                .orElseThrow(() -> new EntityNotFoundException(MessagesEnum.ROLE_NOT_FOUND.getMessage()));
        user.addRole(role);
        user = userRepository.save(user);
        var roles = user.getRoles();
        return roles.stream()
                .map(RoleDTO::new)
                .collect(Collectors.toList());
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(UserDTO::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDTO register(UserRegistrationRequest userRegistrationRequest)
            throws EntityNotFoundException, ApplicationException {
        var existingUser = userRepository.findByUsernameIgnoreCase(userRegistrationRequest.getEmail());
        if (existingUser.isPresent())
            throw new ApplicationException(MessagesEnum.USER_ALREADY_EXISTS);
        var userRole = roleRepository.findByRole(RolesEnum.USER)
                .orElseThrow(() -> new EntityNotFoundException(MessagesEnum.ROLE_NOT_FOUND.getMessage()));
        var user = User.builder()
                .uuid(UUID.randomUUID())
                .username(userRegistrationRequest.getEmail())
                .password(securityService.encode(userRegistrationRequest.getRawPassword()))
                .firstName(userRegistrationRequest.getFirstName())
                .lastName(userRegistrationRequest.getLastName())
                .build();
        user.addRole(userRole);
        user = userRepository.save(user);
        return new UserDTO(user);
    }
}
