package com.example.backend.service;

import com.example.backend.dto.request.RegistrationRequest;
import com.example.backend.entities.Role;
import com.example.backend.entities.enums.RoleType;
import com.example.backend.entities.User;
import com.example.backend.repository.PrivilegeRepository;
import com.example.backend.repository.RoleRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.dto.request.AuthRequest;
import jakarta.persistence.EntityNotFoundException;
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
    @Autowired
    private PrivilegeRepository privilegeRepository;

    public User registration(RegistrationRequest registrationRequest) throws Exception {

        Optional<Role> optionalRole = roleRepository.findByName(RoleType.TOURIST);
        if(optionalRole.isEmpty()) {
            return null;
        }

        if(userRepository.existsByEmail(registrationRequest.getEmail())) {
            throw new Exception("Utente già registrato!");
        }

        User user = new User();
        user.setName(registrationRequest.getName());
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        Role role = optionalRole.get();
        user.getRoles().add(role);

        return userRepository.save(user);

    }

    public User login(AuthRequest authRequest) {

        authenticationManager.authenticate(
                 new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

        return this.userRepository.findByEmail(authRequest.getEmail()).orElseThrow(() -> new EntityNotFoundException("User not Found!"));

    }

}
