package com.edug.devfinder.controllers;

import com.edug.devfinder.services.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/security")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SecurityController {

    private final SecurityService securityService;

    @GetMapping(path="/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestParam(name="token") String refreshToken) {
        return ResponseEntity.ok(securityService.refreshToken(refreshToken));
    }

    @GetMapping(path = "/encrypt")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> encrypt(@RequestParam("input") String input) {
        return ResponseEntity.ok(securityService.encode(input));
    }
}
