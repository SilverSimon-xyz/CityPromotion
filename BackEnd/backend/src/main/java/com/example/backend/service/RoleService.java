package com.example.backend.service;

import com.example.backend.entities.Role;
import com.example.backend.entities.User;
import com.example.backend.entities.enums.RoleType;
import com.example.backend.repository.RoleRepository;
import com.example.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class RoleService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public RoleService() {

    }

    public Role addRole(Role role) {
        return this.roleRepository.save(role);
    }

    public List<Role> getAllRole() {
        return this.roleRepository.findAll();
    }

    public Role getRoleById(int id) {
        return this.roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Role not Found"));
    }

    public Role updateRole(int id, Role roleDetails) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Role not Found!"));
        role.setName(roleDetails.getName());
        role.setDescription(roleDetails.getDescription());
        return roleRepository.save(role);
    }

    public void deleteRole(int id) {
        this.roleRepository.deleteById(id);
    }

    public void assignRoleToUser(int id, RoleType name) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not Found!"));
        Role role = roleRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Role not Found!"));
        user.getRoles().add(role);
        user.setUpdatedAt(new Date());
        this.userRepository.save(user);

    }

    public void removeRoleToUser(int id, RoleType name) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not Found!"));
        Role role = roleRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Role not Found!"));
        user.getRoles().remove(role);
        user.setUpdatedAt(new Date());
        this.userRepository.save(user);
    }

}
