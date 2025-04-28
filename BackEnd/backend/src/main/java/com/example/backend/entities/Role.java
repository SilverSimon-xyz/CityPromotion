package com.example.backend.entities;

import com.example.backend.entities.enums.RoleType;
import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name="roles")
public class Role {
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private RoleType name;

    @Column(nullable = false)
    private String description;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

    @ManyToMany
    @JoinTable(
            name = "role_privileges",
            joinColumns = {
                    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "privilege_id", referencedColumnName = "privilege_id")
            }
    )
    private Collection<Privilege> privileges;

    public Role() {

    }

    public Role(RoleType name) {
        this.name = name;
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

    public Collection<User> getUsers() {
        return this.users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    public Collection<Privilege> getPrivileges() {
        return this.privileges;
    }

    public void setPrivileges(Collection<Privilege> privileges) {
        this.privileges = privileges;
    }


}
