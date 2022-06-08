package com.edug.devfinder.models.entities;

import com.edug.devfinder.models.enums.PermissionEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "permission")
public class Permission implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private PermissionEnum permission;

    @ManyToMany(mappedBy="permissions", fetch = FetchType.LAZY)
    private List<Role> roles;

    @Override
    @JsonIgnore
    public String getAuthority() {
        return this.permission.name();
    }
}
