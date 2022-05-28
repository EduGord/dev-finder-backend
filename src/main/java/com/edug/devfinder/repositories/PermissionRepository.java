package com.edug.devfinder.repositories;

import com.edug.devfinder.models.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    @Query(nativeQuery = true,
            value = "SELECT p.* " +
                    "FROM permission p " +
                    "INNER JOIN role_permission rp ON rp.permission_id = p.id " +
                    "INNER JOIN role r               ON r.id = rp.role_id " +
                    "WHERE UPPER(r.role) = :role;"
            )
    Collection<Permission> findAllByRole_role(String role);
    @Query(nativeQuery = true,
            value = "SELECT DISTINCT p.* " +
                    "FROM permission p " +
                    "INNER JOIN role_permission rp    ON rp.permission_id = p.id " +
                    "INNER JOIN role r                ON r.id = rp.role_id " +
                    "INNER JOIN user_role ur          ON ur.role_id = r.id " +
                    "INNER JOIN users u               ON u.id = ur.user_id " +
                    "WHERE UPPER(u.username) = UPPER(:username);")
    Collection<Permission> findAllDistinctByUser_username(String username);
}
