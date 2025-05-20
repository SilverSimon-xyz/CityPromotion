package com.example.backend.dto.request;

import com.example.backend.entities.users.User;

public record UserRequest(String firstname, String lastname,String email, String password, String roleName) {

    public User toUser() {
        return User.builder()
                .firstname(firstname)
                .lastname(lastname)
                .email(email)
                .password(password)
                .build();
    }
}
