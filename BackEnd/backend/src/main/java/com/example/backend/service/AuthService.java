package com.example.backend.service;

import com.example.backend.dto.request.RegistrationRequest;
import com.example.backend.entities.users.Role;
import com.example.backend.entities.users.User;
import com.example.backend.repository.RoleRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.dto.request.AuthRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    public AuthService(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public User registration(RegistrationRequest registrationRequest) throws Exception {
        if(userRepository.existsByEmail(registrationRequest.email())) {
            throw new Exception("User already exist!");
        }
        Optional<Role> optionalRole = roleRepository.findByName("TOURIST");
        if(optionalRole.isEmpty()) {
            return null;
        }
        Role role = optionalRole.get();
        User user = User.builder()
                .firstname(registrationRequest.firstname())
                .lastname(registrationRequest.lastname())
                .email(registrationRequest.email())
                .password(passwordEncoder.encode(registrationRequest.password()))
                .role(role)
                .build();
        userRepository.save(user);
        return user;
    }

    public User login(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return this.userRepository.findByEmail(authRequest.email()).orElseThrow(() -> new EntityNotFoundException("User not Found!"));
    }

}
