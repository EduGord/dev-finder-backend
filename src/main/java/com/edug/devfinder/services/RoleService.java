package com.edug.devfinder.services;

import com.edug.devfinder.models.responses.RoleDTO;
import com.edug.devfinder.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RoleService {
    private final RoleRepository roleRepository;

    public List<RoleDTO> findAll() {
        var roles = roleRepository.findAll();
        return roles.stream()
                .map(RoleDTO::new)
                .collect(Collectors.toList());
    }
}
