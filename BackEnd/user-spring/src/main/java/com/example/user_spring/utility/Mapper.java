package com.example.user_spring.utility;

import com.example.user_spring.dto.UserDto;
import com.example.user_spring.entities.Role;
import com.example.user_spring.entities.User;
import com.example.user_spring.security.services.Account;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class Mapper {

    public UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setRoles(user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));
        userDto.setCreatedAt(user.getCreatedAt());
        userDto.setUpdatedAt(user.getUpdatedAt());
        return userDto;
    }


    public Account mapUserToAccount(User user) {
        return Account.build(user);
    }
}
