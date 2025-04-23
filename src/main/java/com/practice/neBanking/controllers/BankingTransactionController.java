package com.practice.neBanking.controllers;

import com.practice.neBanking.models.Customer;
import com.practice.neBanking.payload.response.ApiResponse;
import com.practice.neBanking.services.ICustomerService;
import com.practice.neBanking.utils.Constants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/transactions")
@RequiredArgsConstructor
public class BankingTransactionController {
    private final IBankingTransactionService bankingTransactionService;
    private final ExcelService excelService;
    private ICustomerService customerService;

    @PostMapping("/create")
    private ResponseEntity<ApiResponse> createTransaction(@RequestBody @Valid CreateTransactionDTO dto, @RequestParam(value = "receiverAccount", required = false) String receiverAccount){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().toString());
        return ResponseEntity.created(uri).body(ApiResponse.success("Transaction created successfully", this.bankingTransactionService.createTransaction(dto, receiverAccount)));
    }

    @GetMapping("/all")
    private ResponseEntity<ApiResponse> getAllTransactions(@RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER)int page, @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE)int limit){
        Pageable pageable = PageRequest.of(page, limit);
        return ResponseEntity.ok(ApiResponse.success("Transaction fetched successfully",this.bankingTransactionService.getAllTransactions(pageable)));
    }

    @GetMapping("/{id}")
    private ResponseEntity<ApiResponse> getTransactionsById(@PathVariable UUID id){
        return ResponseEntity.ok(ApiResponse.success("Transaction fetched successfully",this.bankingTransactionService.getTransactionById(id)));
    }

    @GetMapping("/type/{type}")
    private ResponseEntity<ApiResponse> getAllTransactionsByType(@RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER)int page, @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE)int limit, @PathVariable("type") ETransactionType type){
        Pageable pageable = PageRequest.of(page,limit);
        return ResponseEntity.ok(ApiResponse.success("Transaction fetched successfully",this.bankingTransactionService.getAllTransactionsByType(pageable,type)));
    }

    @GetMapping("/customer")
    private ResponseEntity<ApiResponse> getAllTransactionsByCustomer(@RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER)int page, @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE)int limit, @RequestParam(value = "customerId") UUID customerId){
        Pageable pageable = PageRequest.of(page,limit);
        return ResponseEntity.ok(ApiResponse.success("Transaction fetched successfully", this.bankingTransactionService.getAllTransactionsByCustomer(pageable,customerId)));
    }

    @GetMapping("/download/{customerId}")
    private ResponseEntity<byte[]> downloadExcel(@PathVariable UUID customerId)throws IOException {
        List<String> fileHeaders = Arrays.asList("#", "Customer Names", "Transaction Type", "Amount", "Your Account", "Receiver Account", "Transaction Type");
        List<BankingTransaction> transactions = this.bankingTransactionService.getAlltransactionsByCustomer(customerId);
        List<List<String>> data = new java.util.ArrayList<>(Collections.emptyList());
        for (int i =0; i < transactions.size(); i++){
            BankingTransaction transaction = transactions.get(i);
            data.add(Arrays.asList(
                    String.valueOf(i+1),
                    transaction.getCustomer().getFirstName()+ " " + transaction.getCustomer().getLastName(),
                    transaction.getTransactionType().name(),
                    String.valueOf(transaction.getAmount()),
                    transaction.getCustomer().getAccount(),
                    transaction.getReceiver() !=null ? transaction.getCustomer().getAccount() : "N/A",
                    transaction.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            ));
        }
        byte[] excelContent = excelService.generateExcelTransactions(fileHeaders, data);
        Customer customer = this.customerService.getById(customerId);
        String fileName = customer.getFirstName() + "_" +customer.getLastName() + "_transaction.xlsx";
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; +filename=" +fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.ok().headers(headers).body(excelContent);
    }

}
