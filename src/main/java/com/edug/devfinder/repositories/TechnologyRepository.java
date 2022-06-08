package com.edug.devfinder.repositories;

import com.edug.devfinder.models.entities.Technology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Table(name="technology")
public interface TechnologyRepository extends JpaRepository<Technology, Long> {
    Optional<Technology> findByNameIgnoreCase(String name);
    List<Technology> findAllByNameIn(List<String> technologies);
    List<Technology> findAllByNameStartsWithIgnoreCase(String technologyPrefix);
}
