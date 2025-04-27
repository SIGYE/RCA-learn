package com.practice.neBanking.serviceImpl;

import com.practice.neBanking.enums.ECustomerStatus;
import com.practice.neBanking.exceptions.AppException;
import com.practice.neBanking.exceptions.BadRequestException;
import com.practice.neBanking.exceptions.ResourceNotFoundException;
import com.practice.neBanking.models.Customer;
import com.practice.neBanking.payload.response.JwtAuthenticationResponse;
import com.practice.neBanking.security.JwtTokenProvider;
import com.practice.neBanking.services.IAuthService;
import com.practice.neBanking.services.ICustomerService;
import com.practice.neBanking.standalone.MailService;
import com.practice.neBanking.utils.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {
    private final ICustomerService customerService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final MailService mailService;
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public JwtAuthenticationResponse login(String email, String password){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = null;
        try{
            jwt = jwtTokenProvider.generateToken(authentication);
        }catch (Exception e){
            throw new AppException("Error generating token", e);
        }
        Customer user = this.customerService.getByEmail(email);
        return new JwtAuthenticationResponse(jwt,user);
    }
    @Override
    public void initiatePasswordReset(String email){
        Customer user = this.customerService.getByEmail(email);
        user.setActivationCode(Utility.randomUUID(6,0,'N'));
        user.setStatus(ECustomerStatus.RESET);
        this.customerService.save(user);
        mailService.sendResetPasswordMail(user.getEmail(),user.getFirstName()+ " " +user.getLastName(), user.getActivationCode());
    }
    @Override
    public void resetPassword(String email, String passwordResetCode, String newPassword){
        Customer user = this.customerService.getByEmail(email);
        if (Utility.isCodeValid(user.getActivationCode(), passwordResetCode) &&
                (user.getStatus().equals(ECustomerStatus.RESET)) || user.getStatus().equals(ECustomerStatus.PENDING)
        ) {
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setActivationCode(Utility.randomUUID(6,0,'N'));
            user.setStatus(ECustomerStatus.ACTIVE);
            this.customerService.save(user);
            this.mailService.sendPasswordResetSuccessfully(user.getEmail(),user.getFullName());
        }else {
            throw new BadRequestException("Invalid code or account status");
        }
    }
    @Override
    public void initiateAccountVerification(String email){
        Customer user = this.customerService.getByEmail(email);
        if (user.getStatus() == ECustomerStatus.ACTIVE){
            throw new BadRequestException("User is already verified");
        }
        String verificationCode;
        do{
            verificationCode = Utility.generateCode();
        } while (this.customerService.findByActivationCode(verificationCode).isPresent());
        this.customerService.save(user);
    }
    @Override
    public void verifyAccount(String verificationCode){
        Optional<Customer> _user = this.customerService.findByActivationCode(verificationCode);
        if (_user.isEmpty()){
            throw new ResourceNotFoundException("Customer", verificationCode,verificationCode);
        }
        Customer user = _user.get();
        if (user.getActivationCodeExpiresAt().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Code is not valid or is expired");
        }
        user.setStatus(ECustomerStatus.ACTIVE);
        user.setActivationCodeExpiresAt(null);
        user.setActivationCode(null);
        this.mailService.sendAccountVerifiedSuccessfullyEmail(user.getEmail(), user.getFullName());
        this.customerService.save(user);
    }


}
