package com.example.backend.entities.users;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "privileges")
public class Privilege {

    @Id
    @Column(name = "privilege_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private PrivilegeType name;

    @ManyToMany(mappedBy = "privileges")
    @JsonBackReference
    private List<Role> roles;

    public Privilege() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PrivilegeType getName() {
        return name;
    }

    public void setName(PrivilegeType name) {
        this.name = name;
    }

    public List<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
