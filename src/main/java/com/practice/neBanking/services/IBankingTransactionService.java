package com.practice.neBanking.services;

import com.practice.neBanking.enums.ETransactionType;
import com.practice.neBanking.payload.request.CreateTransactionDTO;
import com.practice.neBanking.models.BankingTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IBankingTransactionService {
    BankingTransaction createTransaction(CreateTransactionDTO dto, String receiverAccount);
    Page<BankingTransaction> getAllTransactions(Pageable pageable);
    Page<BankingTransaction> getAllTransactionsByCustomer(Pageable pageable, UUID customerId);
    Page<BankingTransaction> getAllTransactionsByType(Pageable pageable, ETransactionType type);
    BankingTransaction getTransactionById(UUID id);
    List<BankingTransaction> getAllTransactionsByCustomer(UUID customerId);
}
