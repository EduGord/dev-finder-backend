package com.edug.devfinder.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;

@Entity
@Getter
@Setter
@Table(name="technology")
public class Technology implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;


    @ManyToMany(mappedBy = "technologies", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<JobExperience> jobExperiences = new ArrayList<>();

}
