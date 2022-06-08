package com.edug.devfinder.services;

import com.edug.devfinder.models.responses.PermissionDTO;
import com.edug.devfinder.repositories.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class PermissionService {
    private final PermissionRepository permissionRepository;

    public Collection<PermissionDTO> findAll() {
        var permissions = permissionRepository.findAll();
        return permissions.stream()
                .map(PermissionDTO::new)
                .collect(Collectors.toList());
    }
}
