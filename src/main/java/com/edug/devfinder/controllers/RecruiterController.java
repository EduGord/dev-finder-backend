package com.edug.devfinder.controllers;


import com.edug.devfinder.services.RecruiterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "/recruiter")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RecruiterController {

    private final RecruiterService recruiterService;

    @GetMapping(path = "/search-by-technology", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('RECRUITER')")
    public ResponseEntity<?> searchByTechnology(@RequestParam("name") String name,
                                                @RequestParam("proficiency") Optional<String> proficiency) {
        var usersTechnology = recruiterService.searchByTechnologyAndProficiency(name, proficiency);
        if (usersTechnology.isEmpty())
            return ResponseEntity.ok(usersTechnology);
        return ResponseEntity.noContent().build();
    }
}

