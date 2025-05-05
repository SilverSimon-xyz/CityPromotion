package com.example.backend.entities.users;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="roles")
public class Role {

    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private RoleType name;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private List<User> users;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_privileges",
            joinColumns = {
                    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "privilege_id", referencedColumnName = "privilege_id")
            }
    )
    private List<Privilege> privileges;

    public Role() {

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

    public List<User> getUsers() {
        return this.users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Privilege> getPrivileges() {
        return this.privileges;
    }

    public void setPrivileges(List<Privilege> privileges) {
        this.privileges = privileges;
    }


}
