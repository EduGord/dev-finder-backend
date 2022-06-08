package com.edug.devfinder.models.consumers.users;

import com.edug.devfinder.models.entities.JobExperience;
import com.edug.devfinder.models.entities.Technology;
import com.edug.devfinder.models.entities.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@AllArgsConstructor
public class CreateSelfJobExperienceRequest extends AbstractSelfJobExperienceRequest {
    @NotBlank
    private String position;

    @NotBlank
    private String companyName;

    @NotBlank
    private String location;

    @NotNull
    private Boolean currentlyWorkingHere;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",
            locale = "pt-BR",
            timezone = "America/Recife")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull
    private LocalDate startedAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",
            locale = "pt-BR",
            timezone = "America/Recife")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate finishedAt;

    @NotBlank
    private String industry;

    @NotBlank
    private String description;

    public JobExperience toJobExperience(User user, List<Technology> technologies) {
        return JobExperience.builder()
                .id(null)
                .uuid(UUID.randomUUID())
                .companyName(companyName)
                .currentlyWorkingHere(currentlyWorkingHere)
                .description(description)
                .finishedAt(finishedAt)
                .industry(industry)
                .location(location)
                .position(position)
                .startedAt(startedAt)
                .user(user)
                .technologies(technologies)
                .build();
    }
}
