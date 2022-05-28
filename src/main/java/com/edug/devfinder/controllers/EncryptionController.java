package com.edug.devfinder.controllers;

import com.edug.devfinder.services.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/encryption")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class EncryptionController {
    private final SecurityService securityService;

    @GetMapping(path = "encrypt")
    public ResponseEntity<?> register(@RequestParam("input") String input) {
        return ResponseEntity.ok(securityService.encode(input));
    }
}
