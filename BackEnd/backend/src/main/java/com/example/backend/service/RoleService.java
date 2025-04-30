package com.example.backend.service;

import com.example.backend.entities.Privilege;
import com.example.backend.entities.Role;
import com.example.backend.entities.User;
import com.example.backend.entities.enums.PrivilegeType;
import com.example.backend.entities.enums.RoleType;
import com.example.backend.repository.PrivilegeRepository;
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
    @Autowired
    private PrivilegeRepository privilegeRepository;

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
        role.setUsers(roleDetails.getUsers());
        role.setPrivileges(roleDetails.getPrivileges());
        return roleRepository.save(role);
    }

    public void deleteRole(int id) {
        this.roleRepository.deleteById(id);
    }

    public Role assignRoleToUser(int id, RoleType name) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not Found!"));
        Role role = roleRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Role not Found!"));
        user.getRoles().add(role);
        role.getUsers().add(user);
        user.setUpdatedAt(new Date());
        this.userRepository.save(user);
        return this.roleRepository.save(role);
    }

    public void removeRoleToUser(int id, RoleType name) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not Found!"));
        Role role = roleRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Role not Found!"));
        user.getRoles().remove(role);
        role.getUsers().remove(user);
        user.setUpdatedAt(new Date());
        this.userRepository.save(user);
        this.roleRepository.save(role);
    }

    public Role assignPrivilegeRoleToUser(int id, RoleType name, PrivilegeType privilegeName) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not Found!"));
        Role role = roleRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Role not Found!"));
        Privilege privilege = privilegeRepository.findByName(privilegeName).orElseThrow(() -> new EntityNotFoundException("Privilege not Found!"));
        role.getPrivileges().add(privilege);
        privilege.getRoles().remove(role);
        user.setUpdatedAt(new Date());
        this.userRepository.save(user);
        this.privilegeRepository.save(privilege);
        return this.roleRepository.save(role);
    }

    public void removePrivilegeRoleToUser(int id, RoleType name, PrivilegeType privilegeName) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not Found!"));
        Role role = roleRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Role not Found!"));
        Privilege privilege = privilegeRepository.findByName(privilegeName).orElseThrow(() -> new EntityNotFoundException("Privilege not Found!"));
        privilege.getRoles().remove(role);
        role.getPrivileges().remove(privilege);
        user.setUpdatedAt(new Date());
        this.userRepository.save(user);
        this.privilegeRepository.save(privilege);
        this.roleRepository.save(role);
    }

    @Transactional
    public List<User> getUsersByRoleName(RoleType roleName) {
        return roleRepository.findUsersByRoleName(roleName);
    }

}
