package com.banktransferenceapichallenge.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.banktransferenceapichallenge.domain.transaction.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
