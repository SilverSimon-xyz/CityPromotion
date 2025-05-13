package com.example.backend.service;
import com.example.backend.entities.users.User;
import com.example.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return this.userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public User getById(int id) {
        return this.userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not Found!"));
    }

    public User updateUser(int id, User user) {
        Optional<User> optional = userRepository.findById(id);
        if(optional.isPresent()) {
            User updateUser = optional.get()
                    .setFirstname(user.getFirstname())
                    .setLastname(user.getLastname())
                    .setEmail(user.getEmail())
                    .setPassword(user.getPassword())
                    .setUpdatedAt(new Date());
            return userRepository.save(updateUser);
        } else {
            throw new EntityNotFoundException("User not Found!");
        }
    }

    public boolean deleteUser(int id) {
        if(userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public void deleteAllUsers() {
        this.userRepository.deleteAll();
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with username or email: " + email));
    }
}
