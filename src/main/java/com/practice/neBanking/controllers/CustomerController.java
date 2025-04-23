package com.practice.neBanking.controllers;

import com.practice.neBanking.enums.ERole;
import com.practice.neBanking.exceptions.BadRequestException;
import com.practice.neBanking.models.Customer;
import com.practice.neBanking.models.File;
import com.practice.neBanking.payload.response.ApiResponse;
import com.practice.neBanking.services.ICustomerService;
import com.practice.neBanking.payload.request.CreateCustomerDTO;
import com.practice.neBanking.payload.request.UpdateCustomerDTO;
import com.practice.neBanking.services.ICustomerService;
import com.practice.neBanking.exceptions.BadRequestException;
import com.practice.neBanking.utils.Constants;
import com.practice.neBanking.services.IFileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.practice.neBanking.repositories.IRoleRepository;
import com.practice.neBanking.models.Role;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
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
        Customer updated = this.customerService.update(this.customerService.getLoggedInCustomer().getId(), dto);
        return ResponseEntity.ok(ApiResponse.success("Customer updated successfully", updated));
    }

    @GetMapping(path = "/all")
    public ResponseEntity<ApiResponse> getAllUsers(
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit
    ){
        Pageable pageable = Pageable.ofSize(limit).withPage(page);
        return ResponseEntity.ok(ApiResponse.success("Users fetched successfully", this.customerService.getAll(pageable)));
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
        customer.setEmail(dto.getEmail());
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setMobile(dto.getMobile());
        customer.setPassword(dto.getPassword());
        customer.setDob(dto.getDob());
        customer.setBalance(dto.getBalance());
        customer.setAccount(accountCode);
        customer.setRoles(Collections.singleton(role));

        Customer entity = this.customerService.create(customer);
        return ResponseEntity.ok(ApiResponse.success("Customer created successfully", entity));
    }

    @PutMapping(path = "/updated-profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> uploadProfile(@RequestParam("file")MultipartFile document){
        if(!Utility.isImageFile(document)){
            throw new BadRequestExceptiom("Only images are allowed!");
        }
        Customer customer = this.customerService.getLoggedInCustomer();
        File file = this.fileService.create(document, customerProfilesDirectory);
        return ResponseEntity.ok(ApiResponse.success("Proile saved successfully", updated));
    }

    @PatchMapping(path = "/remove-profile")
    public ResponseEntity<ApiResponse> removeProfile(){
        Customer customer = this.customerService.getLoggedInCustomer();
        Customer updated = this.customerService.removeProfileImage(customer.getId());
        return ResponseEntity.ok(ApiResponse.success("Profile removed successfully", updated));
    }

    @DeleteMapping(path = "/delete")
    public  ResponseEntity<ApiResponse> deleteMyAccount(){
        Customer customer = this.customerService.getLoggedInCustomer();
        this.customerService.delete(customer.getId());
        return ResponseEntity.ok(ApiResponse.success("Account deleted successfully"));
    }

    @DeleteMapping(path = "/delete/{id}")
    public  ResponseEntity<ApiResponse> deleteByAdmin(@PathVariable(value = "id") UUID id){
        this.customerService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Account deleted successfully"));

    }
}
