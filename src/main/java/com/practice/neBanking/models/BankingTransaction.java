package com.practice.neBanking.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.practice.neBanking.audits.TimestampAudit;
import com.practice.neBanking.enums.ETransactionType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "banking_transaction")
public class BankingTransaction extends TimestampAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @ManyToOne()
    @JoinColumn(name = "sent_to_id")
    private Customer receiver;

    @ManyToOne()
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "amount")
    private double amount;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ETransactionType transactionType;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "banking_date_time")
    private LocalDateTime bankingDateTime;
}
