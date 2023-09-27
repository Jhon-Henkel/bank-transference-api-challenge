package com.banktransferenceapichallenge.services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banktransferenceapichallenge.domain.user.User;
import com.banktransferenceapichallenge.domain.user.UserType;
import com.banktransferenceapichallenge.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public void validateTransaction(User sender, BigDecimal amount) throws Exception {
        if (sender.getUserType() == UserType.MERCHANT) {
            throw new Exception("Merchants are not allowed to send money");
        }
        if (sender.getBalance().compareTo(amount) < 0) {
            throw new Exception("Insufficient funds");
        }
    }

    public User findUserById(Long id) throws Exception {
        return this.repository.findById(id).orElseThrow(() -> new Exception("User not found"));
    }

    public void saveUser(User user) {
        this.repository.save(user);
    }
}
