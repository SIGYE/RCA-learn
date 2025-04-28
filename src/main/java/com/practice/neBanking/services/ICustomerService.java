package com.practice.neBanking.services;
import com.practice.neBanking.models.Customer;
import com.practice.neBanking.models.File;
import com.practice.neBanking.payload.request.UpdateCustomerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface ICustomerService {
    Page<Customer> getAll(Pageable pageable);

    Page<Customer> search(Pageable pageable, String searchKey);

    Customer getById(UUID id);

    Customer create(Customer user);

    Customer save(Customer user);

    Customer update(UUID id, UpdateCustomerDTO dto);

    boolean delete(UUID id);

    Customer getLoggedInCustomer();

    Customer getByEmail(String email);

    Customer changeProfileImage(UUID id, File file);

    Customer removeProfileImage(UUID id);

    Optional<Customer> findByActivationCode(String verificationCode);

    Optional<Customer> findByAccountCode(String accountCode);

}
