package com.edug.devfinder.controllers;

import com.edug.devfinder.services.TechnologyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/technology")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TechnologyController {
    private final TechnologyService technologyService;

    @PostMapping(path = "/add")
    public ResponseEntity<?> register(@RequestParam("name") String technologyName) {
        var technology = technologyService.create(technologyName);
        return new ResponseEntity<>(technology, HttpStatus.CREATED);
    }

    @GetMapping(path="/search")
    public ResponseEntity<?> search(@RequestParam(value = "startsWith", required = false) String startsWith) {
        var technologies = technologyService.search(startsWith);
        if (technologies.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(technologies);
    }
}
