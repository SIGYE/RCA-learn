package com.practice.neBanking.controllers;

import com.practice.neBanking.payload.response.ApiResponse;
import com.practice.neBanking.services.ICustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(path = "/api/v1/transactions")
@RequiredArgsConstructor
public class BankingTransactionController {
    private final IBankingTransactionService bankingTransactionService;
    private final ExcelService excelService;
    private ICustomerService customerService;

    @PostMapping("/create")
    private ResponseEntity<ApiResponse> createTransaction(@RequestBody @Valid CreateTransactionDTO dto, @RequestParam(value = "receiverAccount", required = false) String recieverAccount){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().toString());
        return ResponseEntity.created(uri).body(ApiResponse.success("Transaction created successfully", this.bankingTransactionService.createTransaction(dto, recieverAccount)));
    }

    @GetMapping("/all")
    private ResponseEntity<ApiResponse> getAllTransactions(@RequestParam)

}
