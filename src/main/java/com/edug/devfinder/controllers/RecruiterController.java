package com.edug.devfinder.controllers;


import com.edug.devfinder.services.RecruiterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/recruiter")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RecruiterController {

    private final RecruiterService recruiterService;

    @GetMapping(path = "search-by-technology", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@RequestParam("name") String name) {
        try {
            return ResponseEntity.ok(recruiterService.findAllUsersByTechnology(name));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
