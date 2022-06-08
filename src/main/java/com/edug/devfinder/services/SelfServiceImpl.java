package com.edug.devfinder.services;

import com.edug.devfinder.exceptions.ApplicationException;
import com.edug.devfinder.messages.MessagesEnum;
import com.edug.devfinder.models.consumers.users.CreateSelfJobExperienceRequest;
import com.edug.devfinder.models.consumers.users.UpdateSelfJobExperienceRequest;
import com.edug.devfinder.models.entities.User;
import com.edug.devfinder.models.entities.UserTechnology;
import com.edug.devfinder.models.enums.ProficiencyEnum;
import com.edug.devfinder.models.responses.JobExperienceDTO;
import com.edug.devfinder.models.responses.UserDTO;
import com.edug.devfinder.models.responses.UserTechnologyDTO;
import com.edug.devfinder.repositories.JobExperienceRepository;
import com.edug.devfinder.repositories.TechnologyRepository;
import com.edug.devfinder.repositories.UserRepository;
import com.edug.devfinder.repositories.UserTechnologyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SelfServiceImpl implements SelfService {
    private final UserRepository userRepository;
    private final TechnologyRepository technologyRepository;
    private final UserTechnologyRepository userTechnologyRepository;
    private final JobExperienceRepository jobExperienceRepository;


    private User findSelf() throws AuthenticationException {
        var username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException(MessagesEnum.USER_NOT_FOUND.getMessage()));
    }

    public UserDTO findSelfDTO() throws AuthenticationException {
        var consumer = findSelf();
        return new UserDTO(consumer);
    }

    @Override
    public List<UserTechnologyDTO> getSelfTechnologies() throws AuthenticationException {
        var user = findSelf();
        var userTechnologies = userTechnologyRepository.findAllByUser(user)
                .orElseGet(ArrayList::new);
        return userTechnologies.stream()
                .map(UserTechnologyDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public UserTechnologyDTO createSelfTechnology(String technologyName, ProficiencyEnum proficiency)
            throws AuthenticationException, ApplicationException, EntityNotFoundException, IllegalArgumentException {
        var user = findSelf();
        var technology = technologyRepository.findByNameIgnoreCase(technologyName)
                .orElseThrow(() -> new EntityNotFoundException(MessagesEnum.TECHNOLOGY_NOT_FOUND.getMessage()));
        var existingUserTechnology = userTechnologyRepository.findByUserAndTechnology(user, technology);
        if (existingUserTechnology.isPresent())
            throw new ApplicationException(MessagesEnum.USER_TECHNOLOGY_ALREADY_EXISTS);
        var userTechnology = UserTechnology.builder()
                .userId(user.getId())
                .technologyId(technology.getId())
                .uuid(UUID.randomUUID())
                .technology(technology)
                .proficiencyEnum(proficiency)
                .user(user)
                .build();
        userTechnology = userTechnologyRepository.save(userTechnology);
        return new UserTechnologyDTO(userTechnology);
    }

    @Override
    public UserTechnologyDTO putSelfTechnology(UUID userTechnologyUuid, ProficiencyEnum proficiency)
            throws AuthenticationException, ApplicationException, EntityNotFoundException {
        var user = findSelf();
        var userTechnology = userTechnologyRepository.findByUuidAndUser(userTechnologyUuid, user)
                .orElseThrow(() -> new EntityNotFoundException(MessagesEnum.USER_TECHNOLOGY_NOT_FOUND.getMessage()));
        if (!userTechnology.getProficiencyEnum().equals(proficiency)) {
            userTechnology.setProficiencyEnum(proficiency);
            userTechnology = userTechnologyRepository.save(userTechnology);
        }
        return new UserTechnologyDTO(userTechnology);
    }

    @Override
    public void deleteSelfTechnology(UUID userTechnologyUID) throws EntityNotFoundException, AuthenticationException {
        var user = findSelf();
        var userTechnology = userTechnologyRepository.findByUuidAndUser(userTechnologyUID, user)
                .orElseThrow(() -> new EntityNotFoundException(MessagesEnum.USER_TECHNOLOGY_NOT_FOUND.getMessage()));
        userTechnologyRepository.delete(userTechnology);
    }

    @Override
    public List<JobExperienceDTO> getSelfJobExperience() {
        var jobExperiences = jobExperienceRepository.findAllByUser_Id(this.findSelf().getId());
        return jobExperiences.stream()
                .map(JobExperienceDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public JobExperienceDTO createSelfJobExperience(CreateSelfJobExperienceRequest createSelfJobExperienceRequest)
            throws AuthenticationException {
        var user = findSelf();
        var technologies = technologyRepository.findAllByNameIn(createSelfJobExperienceRequest.getTechnologies());
        var existingJobExperience = jobExperienceRepository.findByUserAndCompanyNameIgnoreCaseAndPositionIgnoreCase(user, createSelfJobExperienceRequest.getCompanyName(), createSelfJobExperienceRequest.getPosition());
        if (existingJobExperience.isPresent())
            throw new ApplicationException(MessagesEnum.JOB_EXPERIENCE_ALREADY_EXISTS);
        var newJobExperience = createSelfJobExperienceRequest.toJobExperience(user, technologies);
        newJobExperience = jobExperienceRepository.save(newJobExperience);
        return new JobExperienceDTO(newJobExperience);
    }

    @Override
    public JobExperienceDTO putSelfJobExperience(UpdateSelfJobExperienceRequest editSelfJobExperienceRequest)
            throws AuthenticationException, EntityNotFoundException {
        var user = findSelf();
        var technologies = technologyRepository.findAllByNameIn(editSelfJobExperienceRequest.getTechnologies());
        var existingJobExperience = jobExperienceRepository.findByUuidAndUser(editSelfJobExperienceRequest.getUuid(), user)
                .orElseThrow(() -> new EntityNotFoundException(MessagesEnum.JOB_EXPERIENCE_NOT_FOUND.getMessage()));
        var jobExperience = editSelfJobExperienceRequest.toUpdatedJobExperience(existingJobExperience, user, technologies);
        jobExperience = jobExperienceRepository.save(jobExperience);
        return new JobExperienceDTO(jobExperience);
    }

    @Override
    public void deleteSelfJob(UUID jobExperienceUuid) throws AuthenticationException, EntityNotFoundException {
        var user = findSelf();
        var jobToDelete = jobExperienceRepository.findByUuidAndUser(jobExperienceUuid, user)
                .orElseThrow(() -> new EntityNotFoundException(MessagesEnum.JOB_EXPERIENCE_NOT_FOUND.getMessage()));
        jobExperienceRepository.delete(jobToDelete);
    }
}
