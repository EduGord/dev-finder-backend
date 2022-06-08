package com.edug.devfinder.models.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "job_experience")
public class JobExperience implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, unique = true, nullable = false, columnDefinition = "uuid DEFAULT gen_random_uuid()")
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonIgnore
    private User user;

    private String position;
    private String companyName;
    private String location;
    private Boolean currentlyWorkingHere;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startedAt;
    @JsonFormat (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate finishedAt;
    private String industry;
    private String description;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
    @JoinTable(name = "job_experience_technology",
            joinColumns = @JoinColumn(name = "technology_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "job_experience_id", referencedColumnName = "id")
    )
    @JsonManagedReference
    private List<Technology> technologies;

}
