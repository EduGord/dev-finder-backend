package com.edug.devfinder.models.entities;

import com.edug.devfinder.models.enums.ProficiencyEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name="user_technology")
@Getter
@JsonIgnoreProperties(ignoreUnknown = true, value = {"id"})
public class UserTechnology implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    UserTechnologyKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name="user_id")
    @JsonManagedReference
    User user;

    @ManyToOne
    @MapsId("technologyId")
    @JoinColumn(name="technology_id")
    @JsonManagedReference
    Technology technology;

    @Enumerated(EnumType.STRING)
    @Getter
    private ProficiencyEnum proficiencyEnum;
}
