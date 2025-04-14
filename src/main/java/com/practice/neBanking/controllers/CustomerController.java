package com.practice.neBanking.controllers;

import com.practice.neBanking.enums.ERole;
import com.practice.neBanking.models.Customer;
import com.practice.neBanking.payload.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.practice.neBanking.repositories.IRoleRepository;
import com.practice.neBanking.models.Role;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;


@RestController
@RequestMapping(path = "/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private ICustomerService customerService;
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private IRoleRepository roleRepository;
    private IFileService fileService;

    @Value("${uploads.directory.customer_profiles}")
    private String customerProfilesDirectory;

    @GetMapping(path = "/current-customer")
    public ResponseEntity<ApiResponse> currentLoggedInCustomer(){
        return ResponseEntity.ok(ApiResponse.success("Currently logged in customer fetched", customerService.getLoggedInCustomer()));
    }

    @PutMapping(path = "/update")
    public ResponseEntity<ApiResponse> update(@RequestBody UpdateCustomerDTO dto){
        Customer updated = this.customerService.update(this.customerService.getLoggeInCustomer().getId(), dto);
        return ResponseEntity.ok(ApiResponse.success("Customer updated successfully", updated));
    }

    @GetMapping(path = "/all")
    public ResponseEntity<ApiResponse> getAllUsers(
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit
    ){
        Pageable pageable = Pageable.ofSize(limit).withPage(page);
        return ResponseEntity.ok(ApiResponse.success("Users fetched successfully", this.customerService.getALl(pageable)));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse> search(
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "q") String q
    ){
        Pageable pageable = Pageable.ofSize(limit).withPage(page);
        return ResponseEntity.ok(ApiResponse.success("User fetched successfully", this.customerService.search(pageable,q)));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable(value = "id")UUID id){
        return ResponseEntity.ok(ApiResponse.success("Users fetched successfully", this.customerService.getBYId()));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody @Valid CreateCustomerDTO dto){
        Customer customer = new Customer();
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        Role role = roleRepository.findByName(ERole.CUSTOMER).orElseThrow(
                () -> new BadRequestException("Customer Role not set"));
        String accountCode;

        do{
            accountCode = Utility.generatedCode();
        } while(this.customerService.findByAccountCode(accountCode).isPresent());
    }

}
