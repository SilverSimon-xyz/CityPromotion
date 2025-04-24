package com.example.backend.repository;

import com.example.backend.entities.Role;
import com.example.backend.entities.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(RoleType name);
}
