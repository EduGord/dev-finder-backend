package com.edug.devfinder.services;

import com.edug.devfinder.models.entities.Permission;
import com.edug.devfinder.repositories.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class PermissionService {
    private final PermissionRepository permissionRepository;

    public Collection<Permission> findAll() {
        return permissionRepository.findAll();
    }
}
