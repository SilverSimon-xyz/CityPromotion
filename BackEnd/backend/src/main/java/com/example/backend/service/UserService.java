package com.example.backend.service;
import com.example.backend.dto.request.UserRequest;
import com.example.backend.entities.users.Role;
import com.example.backend.entities.users.User;
import com.example.backend.repository.RoleRepository;
import com.example.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(UserRequest request) {
        Optional<Role> optionalRole = roleRepository.findByName(request.roleName());
        if(optionalRole.isEmpty()) throw new EntityNotFoundException("Role not present!");
        Role role = optionalRole.get();
        User user = User.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build()
                .setRole(role)
                .setCreatedAt(new Date());

        return this.userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public User getById(Long id) {
        return this.userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not Found!"));
    }

    public User updateUser(Long id, UserRequest request) {
        Optional<User> optional = userRepository.findById(id);
        if(optional.isPresent()) {
            Role role = roleRepository.findByName(request.roleName()).orElseThrow(() -> new RuntimeException("Role not Found!"));
            User updateUser = optional.get()
                    .setFirstname(request.firstname())
                    .setLastname(request.lastname())
                    .setEmail(request.email())
                    .setPassword(passwordEncoder.encode(request.password()))
                    .setRole(role)
                    .setUpdatedAt(new Date());
            return userRepository.save(updateUser);
        } else {
            throw new EntityNotFoundException("User not Found!");
        }
    }

    public boolean deleteUser(Long id) {
        if(userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with username or email: " + email));
    }
}
