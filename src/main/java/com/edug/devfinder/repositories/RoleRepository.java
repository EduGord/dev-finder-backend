package com.edug.devfinder.repositories;

import com.edug.devfinder.models.entities.Role;
import com.edug.devfinder.models.enums.RolesEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(RolesEnum role);
}
