package com.practice.neBanking.serviceImpl;

import com.practice.neBanking.exceptions.BadRequestException;
import com.practice.neBanking.exceptions.ResourceNotFoundException;
import com.practice.neBanking.models.Customer;
import com.practice.neBanking.models.File;
import com.practice.neBanking.payload.request.UpdateCustomerDTO;
import com.practice.neBanking.payload.response.ApiResponse;
import com.practice.neBanking.repositories.ICustomerRepository;
import com.practice.neBanking.services.ICustomerService;
import com.practice.neBanking.services.IFileService;
import com.practice.neBanking.standalone.FileStorageService;
import com.practice.neBanking.utils.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements ICustomerService {
    private ICustomerRepository userRepository;
    private IFileService fileService;
    private FileStorageService fileStorageService;

    @Override
    public Page<Customer> getAll(Pageable pageable){
        return this.userRepository.findAll(pageable);
    }
    @Override
    public Page<Customer> search(Pageable pageable, String searchKey){
        return this.userRepository.search(pageable, searchKey);
    }
    @Override
    public Customer getById(UUID id){
        return this.userRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Customer", "id", id.toString())
        );
    }
    @Override
    public Customer create(Customer user){
        try{
            Optional<Customer> userOptional = this.userRepository.findByEmail(user.getEmail());
            if (userOptional.isPresent())
                throw new BadRequestException(String.format("Customer with email '%s' already exists", user.getEmail()));
            return this.userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            String errorMessage = Utility.getConstraintViolationMessage(ex, user);
            throw new BadRequestException(errorMessage, ex);
        }
    }
    @Override
    public Customer save(Customer user){
        try{
            return this.userRepository.save(user);
        }catch (DataIntegrityViolationException ex){
            String errorMessage = Utility.getConstraintViolationMessage(ex, user);
            throw new BadRequestException(errorMessage, ex);
        }
    }
    @Override
    public Customer update(UUID id, UpdateCustomerDTO dto){
        Customer entity = this.userRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Customer", "Id",id.toString())
        );
        Optional<Customer> userOptional = this.userRepository.findByEmail(dto.getEmail());
        if (userOptional.isPresent() && (userOptional.get().getId() != entity.getId()))
            throw new BadRequestException(String.format("User with email '%s' already exists", entity.getEmail()));

        entity.setEmail(dto.getEmail());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setMobile(dto.getMobile());
        entity.setDob(dto.getDob());

        return this.userRepository.save(entity);
    }

    @Override
    public boolean delete(UUID id){
        this.userRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("User","id",id)
        );
        this.userRepository.deleteById(id);
        return true;
    }
    @Override
    public Customer getLoggedInCustomer(){
        String email;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        }else {
            email = principal.toString();
        }
        return this.userRepository.findByEmail(email).orElseThrow(
                ()-> new ResourceNotFoundException("Customer","id",email)
        );
    }
    @Override
    public Customer getByEmail(String email){
        return this.userRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("Customer", "id", email));
    }
    @Override
    public Optional<Customer> findByActivationCode(String activationCode){
        return this.userRepository.findByActivationCode(activationCode);
    }
    @Override
    public Optional<Customer> findByAccountCode(String accountCode){
        return this.userRepository.findByAccount(accountCode);
    }

}
