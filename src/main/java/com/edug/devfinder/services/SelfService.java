package com.edug.devfinder.services;

import com.edug.devfinder.models.consumers.users.CreateSelfJobExperienceRequest;
import com.edug.devfinder.models.consumers.users.UpdateSelfJobExperienceRequest;
import com.edug.devfinder.models.entities.JobExperience;
import com.edug.devfinder.models.entities.User;
import com.edug.devfinder.models.enums.ProficiencyEnum;
import com.edug.devfinder.models.responses.JobExperienceDTO;
import com.edug.devfinder.models.responses.UserDTO;
import com.edug.devfinder.models.responses.UserTechnologyDTO;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface SelfService {
    UserDTO findSelfDTO();
    Collection<UserTechnologyDTO> getSelfTechnologies();
    UserTechnologyDTO createSelfTechnology(String technologyName, ProficiencyEnum proficiency);
    UserTechnologyDTO putSelfTechnology(UUID userTechnologyUuid, ProficiencyEnum proficiency);
    void deleteSelfTechnology(UUID userTechnologyUuid);
    List<JobExperienceDTO> getSelfJobExperience();
    JobExperienceDTO createSelfJobExperience(CreateSelfJobExperienceRequest createSelfJobExperienceRequest);
    JobExperienceDTO putSelfJobExperience(UpdateSelfJobExperienceRequest editSelfJobExperienceRequest);
    void deleteSelfJob(UUID jobExperienceUuid) throws EntityNotFoundException;
}
