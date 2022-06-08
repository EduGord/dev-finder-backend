package com.edug.devfinder.controllers;

import com.edug.devfinder.services.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/permission")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class PermissionController {
    private final PermissionService permissionService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(path="all")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(permissionService.findAll());
    }
}
