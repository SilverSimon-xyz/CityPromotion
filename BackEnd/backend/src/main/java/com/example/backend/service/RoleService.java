package com.example.backend.service;

import com.example.backend.entities.users.Role;
import com.example.backend.entities.users.User;
import com.example.backend.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRole() {
        return this.roleRepository.findAll();
    }

    public Role getRoleById(Long id) {
        return this.roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Role not Found"));
    }

    public void deleteRole(Long id) {
        this.roleRepository.deleteById(id);
    }

    @Transactional
    public List<User> getUsersByRoleName(String roleName) {
        return roleRepository.findUsersByRoleName(roleName);
    }

}
