package com.edug.devfinder.services;

import com.edug.devfinder.models.enums.ProficiencyEnum;
import com.edug.devfinder.models.responses.UserTechnologyDTO;
import com.edug.devfinder.repositories.TechnologyRepository;
import com.edug.devfinder.repositories.UserTechnologyRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RecruiterService {
    private final Logger log = LoggerFactory.getLogger(ClassUtils.getUserClass(this.getClass()));
    private final TechnologyRepository technologyRepository;
    private final UserTechnologyRepository userTechnologyRepository;

    public List<UserTechnologyDTO> searchByTechnologyAndProficiency(String name, Optional<String> proficiency)
            throws EntityNotFoundException {

        var technology = technologyRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new EntityNotFoundException("Technology not found."));
        var userTechnologies = userTechnologyRepository.findAllByTechnology(technology);

        if (userTechnologies.isPresent()) {
            if (proficiency.isPresent()) {
                var proficiencyEnum = ProficiencyEnum.valueOf(proficiency.get().toUpperCase());
                var filtered = userTechnologies.get().stream()
                        .filter((ut) -> ut.getProficiencyEnum().greaterThenOrEquals(proficiencyEnum))
                        .collect(Collectors.toSet());
                return filtered.stream()
                        .map(UserTechnologyDTO::new)
                        .collect(Collectors.toList());
            }
            return userTechnologies.stream()
                    .flatMap(Collection::stream)
                    .map(UserTechnologyDTO::new)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
