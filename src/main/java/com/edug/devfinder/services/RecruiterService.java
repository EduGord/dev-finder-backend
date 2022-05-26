package com.edug.devfinder.services;

import com.edug.devfinder.models.entities.User;
import com.edug.devfinder.models.entities.UserTechnology;
import com.edug.devfinder.repositories.TechnologyRepository;
import com.edug.devfinder.repositories.UserTechnologyRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RecruiterService {
    private final Logger log = LoggerFactory.getLogger(ClassUtils.getUserClass(this.getClass()));
    private final TechnologyRepository technologyRepository;
    private final UserTechnologyRepository userTechnologyRepository;

    public Optional<Set<UserTechnology>> findAllUsersByTechnology(String name) throws EntityNotFoundException {
        var technology = technologyRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new EntityNotFoundException("Technology not found."));
        return userTechnologyRepository.findAllByTechnology(technology);
    }
}
