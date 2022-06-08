package com.edug.devfinder.models.consumers.users;

import com.edug.devfinder.models.enums.ProficiencyEnum;
import com.edug.devfinder.validators.enums.NameOfEnum;
import com.edug.devfinder.validators.uuid.ValidUUID;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@AllArgsConstructor
public class UpdateSelfTechnologyRequest {
    @ValidUUID
    private UUID uuid;
    @NameOfEnum(enumClass = ProficiencyEnum.class)
    private String proficiency;

    public ProficiencyEnum getProficiencyEnum() {
        return ProficiencyEnum.valueOf(this.proficiency);
    }
}
