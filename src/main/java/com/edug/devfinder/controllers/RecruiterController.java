package com.edug.devfinder.controllers;


import com.edug.devfinder.services.RecruiterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping(path = "/recruiter")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RolesAllowed("RECRUITER")
public class RecruiterController {

    private final RecruiterService recruiterService;

    @GetMapping(path = "search-by-technology", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@RequestParam("name") String name) {
        var usersTechnology = recruiterService.findAllUsersByTechnology(name);
        if (usersTechnology.isPresent())
            return ResponseEntity.ok(usersTechnology);
        else
            return ResponseEntity.noContent().build();
    }
}
