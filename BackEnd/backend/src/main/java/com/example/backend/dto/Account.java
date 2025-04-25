package com.example.backend.dto;

import com.example.backend.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Account implements UserDetails {

    private final int id;
    private final String email;
    private final String name;
    private final String password;
    private final Date createdAt;
    private final Date updatedAt;

    private final Collection<? extends GrantedAuthority> authorities;

    public Account(int id, String name, String email, String password,
                           Collection<? extends GrantedAuthority> authorities, Date createdAt, Date updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.createdAt=createdAt;
        this.updatedAt=updatedAt;
    }

    public static Account build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
        return new Account(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                authorities,
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Account acc = (Account) o;
        return Objects.equals(id, acc.id);
    }

}
