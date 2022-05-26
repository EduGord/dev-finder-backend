package com.edug.devfinder.models.entities;

import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;

@Embeddable
public class UserTechnologyKey implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "user_id")
    Long userId;

    @Column(name = "technology_id")
    Long technologyId;
}
