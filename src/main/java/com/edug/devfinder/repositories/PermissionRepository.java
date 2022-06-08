package com.edug.devfinder.repositories;

import com.edug.devfinder.models.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.stereotype.Repository;

@Repository
@Table(name="permission")
public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
