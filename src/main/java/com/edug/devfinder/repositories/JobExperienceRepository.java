package com.edug.devfinder.repositories;


import com.edug.devfinder.models.entities.User;
import com.edug.devfinder.models.entities.JobExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Table(name="job_experience")
public interface JobExperienceRepository extends JpaRepository<JobExperience, Long> {
    List<JobExperience> findAllByUser_Id(Long userId);
    Optional<JobExperience> findByUuidAndUser(UUID uuid, User user);
    Optional<JobExperience> findByUserAndCompanyNameIgnoreCaseAndPositionIgnoreCase(User user, String company, String position);

}