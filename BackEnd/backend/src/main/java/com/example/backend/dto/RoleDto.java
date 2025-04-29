package com.example.backend.dto;

import com.example.backend.entities.enums.PrivilegeType;
import com.example.backend.entities.enums.RoleType;

import java.util.Collection;

public class RoleDto {

    private int id;

    private RoleType name;

    private String description;

    private Collection<String> users;

    private Collection<PrivilegeType> privileges;

    public RoleDto() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RoleType getName() {
        return name;
    }

    public void setName(RoleType name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<String> getUsers() {
        return this.users;
    }

    public void setUsers(Collection<String> users) {
        this.users = users;
    }

    public Collection<PrivilegeType> getPrivileges() {
        return this.privileges;
    }

    public void setPrivileges(Collection<PrivilegeType> privileges) {
        this.privileges = privileges;
    }
}
