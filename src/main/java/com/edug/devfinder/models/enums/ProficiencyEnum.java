package com.edug.devfinder.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum ProficiencyEnum {
    BEGGINING(1L, "Basic knowledge, understands basic concepts, may have used the technology in small projects."),
    INTERMEDIATE(2L , "Intermediate knowledge, have a good grasp of the technology concepts, usage and knows how to bypass common blockers. Have used this technology in a production environment."),
    ADVANCED(3L, "Advanced knowledge, have used this technology extensively in a production environment, knows how to solve most problems and how to tweak this technology it into your advantage."),
    EXPERT(4L, "You feel like there are no challenges that can be presented for you that you can't crush.");

    private Long id;
    private String description;

    public boolean greaterThenOrEquals(ProficiencyEnum toCompare) {
        return this.ordinal() >= toCompare.ordinal();
    }
}
