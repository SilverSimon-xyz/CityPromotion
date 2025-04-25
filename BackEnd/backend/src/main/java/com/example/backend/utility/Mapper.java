package com.example.backend.utility;

import com.example.backend.entities.User;
import com.example.backend.dto.Account;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public Account mapUserToAccount(User user) {
        return Account.build(user);
    }

}
