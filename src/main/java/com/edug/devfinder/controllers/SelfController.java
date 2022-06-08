package com.edug.devfinder.controllers;

import com.edug.devfinder.models.consumers.users.CreateSelfTechnologyRequest;
import com.edug.devfinder.models.consumers.users.CreateSelfJobExperienceRequest;
import com.edug.devfinder.models.consumers.users.UpdateSelfTechnologyRequest;
import com.edug.devfinder.models.consumers.users.UpdateSelfJobExperienceRequest;
import com.edug.devfinder.services.SelfServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(path = "/self")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SelfController {
    private final SelfServiceImpl selfServiceImpl;

    @GetMapping(path="", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findSelf() {
        return ResponseEntity.ok(selfServiceImpl.findSelfDTO());
    }

    @GetMapping(path="/technology")
    public ResponseEntity<?> getSelfTechnologies() {
        var selfTechnologies = selfServiceImpl.getSelfTechnologies();
        if (selfTechnologies.isEmpty())
            return ResponseEntity.noContent().build();
        return new ResponseEntity<>(selfTechnologies, HttpStatus.OK);
    }

    @PostMapping(path="/technology")
    public ResponseEntity<?> createSelfTechnology(@Valid CreateSelfTechnologyRequest createSelfTechnologyRequest) {
        return ResponseEntity.ok(selfServiceImpl.createSelfTechnology(createSelfTechnologyRequest.getName(),
                createSelfTechnologyRequest.getProficiencyEnum()));
    }

    @PutMapping(path="/technology")
    public ResponseEntity<?> editSelfTechnology(
            @Valid UpdateSelfTechnologyRequest updateSelfTechnologyRequest) {
        return ResponseEntity.ok(selfServiceImpl.putSelfTechnology(updateSelfTechnologyRequest.getUuid(),
                updateSelfTechnologyRequest.getProficiencyEnum()));
    }

    @DeleteMapping(path="/technology/{uuid}")
    public ResponseEntity<?> deleteSelfTechnology(@PathVariable("uuid") UUID technologyUID) {
        selfServiceImpl.deleteSelfTechnology(technologyUID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path="/job")
    public ResponseEntity<?> getSelfJobs() {
        var jobExperiences = selfServiceImpl.getSelfJobExperience();
        if (jobExperiences.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(jobExperiences);
    }


    @PostMapping(path="/job")
    public ResponseEntity<?> createSelfJob(@Valid CreateSelfJobExperienceRequest createSelfJobExperienceRequest) {
        return new ResponseEntity<>(selfServiceImpl.createSelfJobExperience(createSelfJobExperienceRequest), HttpStatus.OK);
    }

    @PutMapping(path="/job")
    public ResponseEntity<?> putSelfJob(@Valid UpdateSelfJobExperienceRequest editSelfJobExperienceRequest) {
        return new ResponseEntity<>(selfServiceImpl.putSelfJobExperience(editSelfJobExperienceRequest), HttpStatus.OK);
    }

    @DeleteMapping(path="/job/{uuid}")
    public ResponseEntity<?> deleteSelfJob(@PathVariable("uuid") UUID jobExperienceUID) {
        selfServiceImpl.deleteSelfJob(jobExperienceUID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
