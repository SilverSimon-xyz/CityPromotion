package com.example.backend.service;
import com.example.backend.exception.UserNotFoundException;
import com.example.backend.entities.User;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService {

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
        return this.userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not Found!"));
    }

    public User updateUser(int id, String name) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not Found!"));
        user.setName(name);
        user.setUpdatedAt(new Date());
        return userRepository.save(user);
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
}
