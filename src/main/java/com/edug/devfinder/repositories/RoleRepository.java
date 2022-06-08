package com.edug.devfinder.repositories;

import com.edug.devfinder.configs.redis.cache.CacheTopicsConstants;
import com.edug.devfinder.models.entities.Role;
import com.edug.devfinder.models.enums.RolesEnum;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Table(name="role")
public interface RoleRepository extends JpaRepository<Role, Long>, CacheTopicsConstants {
    Optional<Role> findByRole(RolesEnum role);
}
