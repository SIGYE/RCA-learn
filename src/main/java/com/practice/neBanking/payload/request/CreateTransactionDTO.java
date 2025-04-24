package com.practice.neBanking.payload.request;

import com.practice.neBanking.enums.ETransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateTransactionDTO {
    @NotNull
    @DecimalMin(value = "0.1", inclusive = false)
    private Double amount;

    @NotNull
    private ETransactionType transactionType;
}
