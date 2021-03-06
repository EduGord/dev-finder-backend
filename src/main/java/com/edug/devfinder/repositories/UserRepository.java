package com.edug.devfinder.repositories;

import com.edug.devfinder.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Table(name="users")
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameIgnoreCase(String username);
}
