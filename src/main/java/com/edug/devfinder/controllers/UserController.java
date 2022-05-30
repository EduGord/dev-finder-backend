package com.edug.devfinder.controllers;

import com.edug.devfinder.models.consumers.users.UserRegistrationRequest;
import com.edug.devfinder.services.SecurityService;
import com.edug.devfinder.services.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserController {
    private final UserServiceImpl userService;

    private final SecurityService securityService;

    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@Validated @RequestBody UserRegistrationRequest userRegistrationRequest) {
        return new ResponseEntity<>(userService.register(userRegistrationRequest), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(path="all")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping(path="refresh-token")
    public ResponseEntity<?> refreshToken(@RequestParam(name="token") String refreshToken) {
        return new ResponseEntity<>(securityService.refreshToken(refreshToken), HttpStatus.OK);
    }
}