package com.example.user_spring.service;

import com.example.user_spring.dto.request.RegistrationRequest;
import com.example.user_spring.exception.UserNotFoundException;
import com.example.user_spring.entities.Role;
import com.example.user_spring.entities.RoleType;
import com.example.user_spring.entities.User;
import com.example.user_spring.repository.RoleRepository;
import com.example.user_spring.repository.UserRepository;
import com.example.user_spring.dto.request.AuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public User registration(RegistrationRequest registrationRequest) throws Exception {

        Optional<Role> optional = roleRepository.findByName(RoleType.USER);
        if(optional.isEmpty()) {
            return null;
        }

        if(userRepository.existsByEmail(registrationRequest.getEmail())) {
            throw new Exception("Utente già registrato!");
        }

        User user = new User();
        user.setName(registrationRequest.getName());
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.getRoles().add(optional.get());

        return userRepository.save(user);

    }

    public User login(AuthRequest authRequest) {

        authenticationManager.authenticate(
                 new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

        return this.userRepository.findByEmail(authRequest.getEmail()).orElseThrow(() -> new UserNotFoundException("User not Found!"));

    }



}
