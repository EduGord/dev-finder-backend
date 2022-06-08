package com.edug.devfinder.models.consumers.users;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public abstract class AbstractSelfJobExperienceRequest {
    protected String position;

    protected String companyName;

    protected String location;

    protected Boolean currentlyWorkingHere;

    protected LocalDate startedAt;

    protected LocalDate finishedAt;

    protected String industry;
    protected String description;
    protected List<String> technologies;
}
