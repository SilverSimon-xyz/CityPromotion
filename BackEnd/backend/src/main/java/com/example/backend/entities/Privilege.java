package com.example.backend.entities;

import com.example.backend.entities.enums.PrivilegeType;
import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "privileges")
public class Privilege {

    @Id
    @Column(name = "privilege_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private PrivilegeType name;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;

    public Privilege() {

    }

    public Privilege(PrivilegeType name) {
        this.name = name;
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

    public Collection<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
}
