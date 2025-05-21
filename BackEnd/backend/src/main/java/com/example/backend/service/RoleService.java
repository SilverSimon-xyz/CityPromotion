package com.example.backend.service;

import com.example.backend.entities.users.Role;
import com.example.backend.entities.users.User;
import com.example.backend.repository.RoleRepository;
import com.example.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class RoleService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public Role addRole(Role role) {
        return this.roleRepository.save(role);
    }

    public List<Role> getAllRole() {
        return this.roleRepository.findAll();
    }

    public Role getRoleById(Long id) {
        return this.roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Role not Found"));
    }

    public Role updateRole(Long id, Role roleDetails) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Role not Found!"));
        role
                .setName(roleDetails.getName())
                .setDescription(roleDetails.getDescription())
                .setUsers(roleDetails.getUsers());
        return roleRepository.save(role);
    }

    public void deleteRole(Long id) {
        this.roleRepository.deleteById(id);
    }

    public User assignRoleToUser(Long id, String name) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not Found!"));
        Role role = roleRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Role not Found!"));
        user.setRole(role);
        role.getUsers().add(user);
        user.setUpdatedAt(new Date());
        this.roleRepository.save(role);
        return this.userRepository.save(user);
    }

    @Transactional
    public List<User> getUsersByRoleName(String roleName) {
        return roleRepository.findUsersByRoleName(roleName);
    }

}
