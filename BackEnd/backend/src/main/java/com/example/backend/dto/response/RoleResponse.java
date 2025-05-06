package com.example.backend.dto.response;

import com.example.backend.entities.users.RoleType;

import java.util.Collection;

public class RoleResponse {

    private int id;

    private RoleType name;

    private String description;

    private Collection<String> users;

    private Collection<String> privileges;

    public RoleResponse() {

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

    public Collection<String> getPrivileges() {
        return this.privileges;
    }

    public void setPrivileges(Collection<String> privileges) {
        this.privileges = privileges;
    }
}
