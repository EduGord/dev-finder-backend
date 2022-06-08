package com.edug.devfinder.models.responses;

import com.edug.devfinder.models.entities.JobExperience;
import com.edug.devfinder.models.entities.Technology;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
public class JobExperienceDTO implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;

        private UUID uuid;
        private String position;
        private String companyName;
        private String location;
        private Boolean currentlyWorkingHere;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate startedAt;
        @JsonFormat (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate finishedAt;
        private String industry;
        private String description;
        private List<TechnologyDTO> technologies;

        public JobExperienceDTO(JobExperience jobExperience) {
                this.uuid = jobExperience.getUuid();
                this.position = jobExperience.getPosition();
                this.companyName = jobExperience.getCompanyName();
                this.location = jobExperience.getLocation();
                this.currentlyWorkingHere = jobExperience.getCurrentlyWorkingHere();
                this.startedAt = jobExperience.getStartedAt();
                this.finishedAt = jobExperience.getFinishedAt();
                this.industry = jobExperience.getIndustry();
                this.description = jobExperience.getDescription();
                this.technologies = jobExperience.getTechnologies()
                        .stream()
                        .map(TechnologyDTO::new)
                        .collect(Collectors.toList());
        }


}
