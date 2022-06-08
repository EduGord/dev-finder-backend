package com.edug.devfinder.models.responses;

import com.edug.devfinder.models.entities.Technology;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Getter
public class TechnologyDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String name;

    public TechnologyDTO(Technology technology) {
        this.name = technology.getName();
    }
}
