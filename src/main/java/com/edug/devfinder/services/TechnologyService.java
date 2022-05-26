package com.edug.devfinder.services;

import com.edug.devfinder.models.entities.Technology;
import com.edug.devfinder.repositories.TechnologyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TechnologyService {
    private final TechnologyRepository technologyRepository;

    public Technology add(String technologyName) {
        var technology = new Technology();
        technology.setName(technologyName);
        return technologyRepository.save(technology);
    }

    public List<Technology> listAll() {
        return technologyRepository.findAll();
    }
}
