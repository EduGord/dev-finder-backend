package com.edug.devfinder.services;

import com.edug.devfinder.models.entities.Technology;
import com.edug.devfinder.models.responses.TechnologyDTO;
import com.edug.devfinder.repositories.TechnologyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TechnologyService {
    private final TechnologyRepository technologyRepository;

    public TechnologyDTO create(String technologyName) {
        var newTechnology = new Technology();
        newTechnology.setName(technologyName);
        newTechnology = technologyRepository.save(newTechnology);
        return new TechnologyDTO(newTechnology);
    }

    private List<TechnologyDTO> listAll() {
        var technologies = technologyRepository.findAll();
        return technologies.stream()
                .map(TechnologyDTO::new)
                .collect(Collectors.toList());
    }
    public List<TechnologyDTO> search(String technologyPrefix) {
        if (technologyPrefix == null)
            return listAll();
        var technologies = technologyRepository.findAllByNameStartsWithIgnoreCase(technologyPrefix);
        return technologies.stream().map(TechnologyDTO::new).collect(Collectors.toList());
    }

}
