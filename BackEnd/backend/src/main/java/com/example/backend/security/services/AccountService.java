package com.example.backend.security.services;

import com.example.backend.dto.Account;
import com.example.backend.repository.RoleRepository;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Account loadUserByUsername(String email) throws UsernameNotFoundException {

        return userRepository.findByEmail(email).map(Account::build)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email: " + email));
    }

}
