package com.practice.neBanking.security;

import com.practice.neBanking.models.Customer;
import com.practice.neBanking.repositories.ICustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final ICustomerRepository userRepository;
    @Transactional
    public UserDetails loadByUserId(UUID id){
        Customer user = this.userRepository.findById(id).orElseThrow(()-> new UsernameNotFoundException("User not found with id" +id));
        return UserPrincipal.create(user);
    }
    @Transactional
    public UserDetails loadUserByUsername(String email){
        Customer user = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found with email" +email));
        return UserPrincipal.create(user);
    }
}
