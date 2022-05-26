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

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RecruiterService {
    private final Logger log = LoggerFactory.getLogger(ClassUtils.getUserClass(this.getClass()));
    private final TechnologyRepository technologyRepository;
    private final UserTechnologyRepository userTechnologyRepository;

    public Set<UserTechnology> findAllUsersByTechnology(String name) {
        var technology = technologyRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new RuntimeException("Tecnologia não encontrada."));
        var userTechnologies = userTechnologyRepository.findAllByTechnology(technology)
                .orElseThrow(() -> new RuntimeException("Usuários não encontrados para esta tecnologia."));
        return userTechnologies;
//        return userTechnologies.stream()
//                .map(UserTechnology::getUser)
//                .collect(Collectors.toSet());
    }
}
