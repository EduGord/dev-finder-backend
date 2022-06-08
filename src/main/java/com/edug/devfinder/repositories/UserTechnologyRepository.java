package com.edug.devfinder.repositories;

import com.edug.devfinder.models.entities.Technology;
import com.edug.devfinder.models.entities.User;
import com.edug.devfinder.models.entities.JobExperience;
import com.edug.devfinder.models.entities.UserTechnology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
@Table(name="user_technology")
public interface UserTechnologyRepository extends JpaRepository<UserTechnology, Long> {
    Optional<Set<UserTechnology>> findAllByTechnology(Technology technology);
    Optional<UserTechnology> findByUserAndTechnology(User user, Technology technology);

    Optional<UserTechnology> findByUuidAndUser(UUID uuid, User user);

    Optional<List<UserTechnology>> findAllByUser(User user);
}
