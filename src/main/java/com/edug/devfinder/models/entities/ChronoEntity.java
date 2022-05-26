package com.edug.devfinder.models.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@MappedSuperclass
public class ChronoEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "created_at")
    @CreationTimestamp
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    protected LocalDateTime createdAt;

    @Column(name = "last_updated_at")
    @UpdateTimestamp
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    protected LocalDateTime updatedAt;

    @Column(name = "removed_at")
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    protected LocalDateTime removedAt;
}
