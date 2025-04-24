package com.practice.neBanking.repositories;

import com.practice.neBanking.enums.ETransactionType;
import com.practice.neBanking.models.BankingTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IBankingRepository extends JpaRepository<BankingTransaction, UUID> {
    Page<BankingTransaction> findAllByCustomerId(Pageable pageable, UUID customerId);
    List<BankingTransaction> findAllByCustomerId(UUID customerId);
    Page<BankingTransaction> findAllByTransactionType(Pageable pageable, ETransactionType transactionType);
}
