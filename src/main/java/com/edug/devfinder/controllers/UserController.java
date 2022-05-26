package com.edug.devfinder.controllers;

import com.edug.devfinder.models.AuthRequest;
import com.edug.devfinder.models.UserRegistrationRequest;
import com.edug.devfinder.models.entities.User;
import com.edug.devfinder.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/user")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserController {
    private final UserService userService;

    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@Validated @RequestBody UserRegistrationRequest userRegistrationRequest) {
        try {
            return ResponseEntity.ok(userService.register(userRegistrationRequest));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@Validated @RequestBody AuthRequest authRequest) {
        try {
            return ResponseEntity.ok(userService.signIn(authRequest));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/all")
    public List<User> listAll() {
        return userService.listAll();
    }
}
