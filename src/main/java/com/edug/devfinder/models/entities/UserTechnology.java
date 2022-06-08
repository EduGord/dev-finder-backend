package com.edug.devfinder.models.entities;

import com.edug.devfinder.models.enums.ProficiencyEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name="user_technology")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true, value = {"id"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserTechnologyKey.class)
public class UserTechnology implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Long userId;

    @Id
    private Long technologyId;

    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, unique = true, nullable = false, columnDefinition = "uuid DEFAULT gen_random_uuid()")
    private UUID uuid;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name="user_id")
    @JsonManagedReference
    private User user;

    @ManyToOne
    @MapsId("technologyId")
    @JoinColumn(name="technology_id")
    @JsonManagedReference
    private Technology technology;

    @Enumerated(EnumType.STRING)
    @Getter
    private ProficiencyEnum proficiencyEnum;
}
