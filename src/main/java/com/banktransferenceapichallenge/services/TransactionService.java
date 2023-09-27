package com.banktransferenceapichallenge.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.banktransferenceapichallenge.domain.transaction.Transaction;
import com.banktransferenceapichallenge.domain.user.User;
import com.banktransferenceapichallenge.dtos.TransactionDTO;
import com.banktransferenceapichallenge.repositories.TransactionRepository;

@Service
public class TransactionService {
    @Autowired
    private UserService userService;
    @Autowired
    private TransactionRepository repository;
    @Autowired
    private RestTemplate restTemplate;

    public void createTransaction(TransactionDTO transaction) throws Exception {
        User sender = this.userService.findUserById(transaction.senderId());
        User receiver = this.userService.findUserById(transaction.receiverId());

        this.userService.validateTransaction(sender, transaction.value());

        if (! this.isAuthorizedTransaction(sender, transaction.value())) {
            throw new Exception("Transaction not authorized");
        }

        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transaction.value());
        newTransaction.setSender(sender);
        newTransaction.setReceiver(receiver);
        newTransaction.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transaction.value()));
        receiver.setBalance(receiver.getBalance().add(transaction.value()));

        repository.save(newTransaction);
        userService.saveUser(sender);
        userService.saveUser(receiver);
    }

    public boolean isAuthorizedTransaction(User sender, BigDecimal value) {
        ResponseEntity<Map> response = restTemplate.getForEntity("https://run.mocky.io/v3/eda53610-258d-4265-bcca-89e4e07782bb", Map.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            String message = (String)response.getBody().get("message");
            return "Authorized".equalsIgnoreCase(message);
        }
        return false;
    }
}
