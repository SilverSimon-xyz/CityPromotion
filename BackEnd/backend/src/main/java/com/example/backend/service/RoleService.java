package com.example.backend.service;

import com.example.backend.entities.Role;
import com.example.backend.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RoleService {
    @Autowired
    private RoleRepository roleRepository;


    public Role saveRole(Role role) {
        return this.roleRepository.save(role);
    }

    public List<Role> getAllRole() {
        return this.roleRepository.findAll();
    }

    public Role getRoleById(int id) {
        return this.roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role not Found"));
    }

    public Role updateRole(int id, Role roleDetails) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role not Found!"));
        role.setName(roleDetails.getName());
        role.setDescription(roleDetails.getDescription());
        return roleRepository.save(role);
    }

    public void deleteRole(int id) {
        this.roleRepository.deleteById(id);
    }

}
