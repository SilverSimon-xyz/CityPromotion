package com.example.backend.entities.users;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "users",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"email"})
        })
public class User implements UserDetails, Principal {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
                    @JoinColumn(name = "email", referencedColumnName = "email")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id", referencedColumnName = "role_id"),
                    @JoinColumn(name = "role_name", referencedColumnName = "name")
            })
    private Set<Role> roles;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @Column(name = "created_at", columnDefinition = "TIMESTAMP", updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private Date updatedAt;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private RefreshToken refreshToken;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return authorities;
    }

    @Override
    public String getName() {
        return getFullName();
    }

    private String getFullName() {
        return firstname + " " + lastname;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}

