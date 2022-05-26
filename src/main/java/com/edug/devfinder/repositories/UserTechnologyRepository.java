package com.edug.devfinder.repositories;

import com.edug.devfinder.models.entities.Technology;
import com.edug.devfinder.models.entities.UserTechnology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserTechnologyRepository extends JpaRepository<UserTechnology, Long> {
    Optional<Set<UserTechnology>> findAllByTechnology(Technology technology);
}
