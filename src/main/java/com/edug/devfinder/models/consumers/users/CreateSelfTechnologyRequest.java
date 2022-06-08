package com.edug.devfinder.models.consumers.users;

import com.edug.devfinder.models.enums.ProficiencyEnum;
import com.edug.devfinder.validators.enums.NameOfEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@AllArgsConstructor
public class CreateSelfTechnologyRequest {
    @NotBlank
    private String name;

    @NameOfEnum(enumClass = ProficiencyEnum.class)
    private String proficiency;

    public ProficiencyEnum getProficiencyEnum() {
        return ProficiencyEnum.valueOf(this.proficiency);
    }
}
