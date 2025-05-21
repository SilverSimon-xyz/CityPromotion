package com.example.backend.repository;

import com.example.backend.entities.users.Role;
import com.example.backend.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);

    @Query("SELECT u FROM User u JOIN u.role r WHERE r.name = :roleName")
    List<User> findUsersByRoleName(@Param("roleName") String roleName);

}
