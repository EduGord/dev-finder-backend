package com.edug.devfinder.controllers;

import com.edug.devfinder.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/role")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RoleController {
    private final RoleService roleService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(path="all")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(roleService.findAll(), HttpStatus.OK);
    }
}
