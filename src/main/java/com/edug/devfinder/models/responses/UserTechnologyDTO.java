package com.edug.devfinder.models.responses;

import com.edug.devfinder.models.entities.UserTechnology;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
public class UserTechnologyDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private UUID uuid;
    private UserDTO user;
    private TechnologyDTO technology;
    private String proficiency;

    public UserTechnologyDTO(UserTechnology userTechnology) {
        this.uuid = userTechnology.getUuid();
        this.user = new UserDTO(userTechnology.getUser());
        this.technology = new TechnologyDTO(userTechnology.getTechnology());
        this.proficiency = userTechnology.getProficiencyEnum().name();
    }
}
