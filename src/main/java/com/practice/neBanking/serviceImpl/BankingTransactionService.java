package com.practice.neBanking.serviceImpl;

import com.practice.neBanking.enums.ETransactionType;
import com.practice.neBanking.exceptions.BadRequestException;
import com.practice.neBanking.exceptions.ResourceNotFoundException;
import com.practice.neBanking.models.BankingTransaction;
import com.practice.neBanking.models.Customer;
import com.practice.neBanking.payload.request.CreateTransactionDTO;
import com.practice.neBanking.repositories.IBankingRepository;
import com.practice.neBanking.services.IBankingTransactionService;
import com.practice.neBanking.services.ICustomerService;
import com.practice.neBanking.standalone.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankingTransactionService implements IBankingTransactionService {
    private final IBankingRepository bankingRepository;
    private final ICustomerService customerService;
    private final MailService mailService;

    @Override
    public BankingTransaction createTransaction(CreateTransactionDTO dto, String receiverAccount){
        Customer customer = this.customerService.getLoggedInCustomer();
        BankingTransaction transaction = new BankingTransaction();
        if (dto.getTransactionType() == ETransactionType.WITHDRAW){
            if (customer.getBalance()< dto.getAmount()){
                throw new BadRequestException("Insufficient balance");
            }
            customer.setBalance(customer.getBalance() - dto.getAmount());
        } else if (dto.getTransactionType() == ETransactionType.SAVING) {
            customer.setBalance(customer.getBalance() + dto.getAmount());
        } else if (dto.getTransactionType() == ETransactionType.TRANSFER && receiverAccount !=null) {
            if (customer.getBalance()< dto.getAmount()){
                throw new BadRequestException("Insufficient balance");
            }
            customer.setBalance(customer.getBalance() - dto.getAmount());
            Customer receiver = this.customerService.findByAccountCode(receiverAccount).orElseThrow(()-> new ResourceNotFoundException("Customer", "account", receiverAccount));
            if (receiver.getId().equals(customer.getId())){
                throw new BadRequestException("You can't transfer to yourself");
            }
            receiver.setBalance(receiver.getBalance() + dto.getAmount());
            this.customerService.save(receiver);
            transaction.setReceiver(receiver);
        } else {
            if (dto.getTransactionType() == ETransactionType.TRANSFER){
                throw new BadRequestException("Receiver id required");
            }
            throw new BadRequestException("Invalid transaction type");
        }
        this.customerService.save(customer);
        transaction.setAmount(dto.getAmount());
        transaction.setTransactionType(dto.getTransactionType());
        transaction.setCustomer(customer);

        if (dto.getTransactionType() == ETransactionType.SAVING){
            mailService.sendSavingsStoredSuccessfullyEmail();
        }
    }
}
