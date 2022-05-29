package com.edug.devfinder.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping(path = "/")
public class PublicController {
    private static final String FAVICON_PATH = "src/main/resources/static/favicon.ico";
    private static final byte[] IN_MEMORY_FAVICON;

    /**
     * This will prevent Bean/Application initialization if it catches an {@link IOException}.
     */
    static {
        try {
            IN_MEMORY_FAVICON = Files.readAllBytes(Paths.get(FAVICON_PATH));
        } catch (IOException e) {
            throw new RuntimeException(String.join("", FAVICON_PATH, " not found"));
        }
    }

    @GetMapping(path="/favicon.ico", produces="image/x-icon")
    public byte[] favicon() {
        return IN_MEMORY_FAVICON;
    }
}
