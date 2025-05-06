package com.example.backend.dto.response;

import com.example.backend.entities.users.Privilege;
import com.example.backend.entities.users.Role;
import com.example.backend.entities.users.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class Account implements UserDetails {

    private final User user;

    public Account(User user) {
        this.user = user;
    }

    public int getId() {
        return user.getId();
    }

    public String getName() {
        return user.getName();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = new HashSet<>(user.getRoles());
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName().name()));
            Set<Privilege> privileges = new HashSet<>(role.getPrivileges());
            privileges.forEach(privilege -> authorities.add(new SimpleGrantedAuthority(privilege.getName().name())));
        });
        return authorities;
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
        return Objects.equals(getId(), acc.getId());
    }

}
