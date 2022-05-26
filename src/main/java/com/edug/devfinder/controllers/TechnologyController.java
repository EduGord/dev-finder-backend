package com.edug.devfinder.controllers;

import com.edug.devfinder.models.entities.Technology;
import com.edug.devfinder.services.TechnologyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/technology")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TechnologyController {
    private final TechnologyService technologyService;

    @PostMapping(path = "add")
    public Technology add(@RequestParam("name") String technologyName) {
        return technologyService.add(technologyName);
    }

    @GetMapping(path = "all")
    public List<Technology> listAll() {
        return technologyService.listAll();
    }
}
