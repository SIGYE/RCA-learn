package com.practice.neBanking.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.practice.neBanking.services.IAuthService;
import com.practice.neBanking.payload.request.InitiateAccountVerificationDTO;
import com.practice.neBanking.payload.request.InitiatePasswordResetDTO;
import com.practice.neBanking.payload.request.*;

@RestController
@RequestMapping(path = "/api/neBanking/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final IAuthService authService;

    @PostMapping(path = "/login")
    public ResponseEntity<ApiResponse> login (@Valid @RequestBody LoginDTO dto){
        return ResponseEntity.ok(ApiResponse.success("Login Successful", this.authService.login(dto.getEmail(), dto.getPassword())));
    }

    @PostMapping(path = "/initiate-reset-password")
    public ResponseEntity<ApiResponse> initiateResetPassword(@RequestBody @Valid InitiatePasswordResetDTO dto){
        this.authService.initiatePasswordReset(dto.getEmail());
        return ResponseEntity.ok(ApiResponse.success("Please check your email and activate your account"));
    }

    @PostMapping(path = "/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody @Valid ResetPasswordDTO dto){
        this.authService.resetPassword(dto.getEmail(), dto.getPasswordResetCode(), dto.getNewPassword());
        return ResponseEntity.ok(ApiResponse.success("Password successfully reset"));
    }

    @PutMapping("/initiate-account-verification")
    public ResponseEntity<ApiResponse> initiateAccountVerification(@RequestBody @Valid InitiateAccountVerificationDTO dto){
        this.authService.initiateAccountVerification(dto.getEmail());
        return ResponseEntity.ok(ApiResponse.success("Verification code sent to your email, will expire in 3 hours"));
    }

    @PatchMapping("/verify-account/{verificationCode}")
    public ResponseEntity<ApiResponse> verifyAccount(@PathVariable("verificationCode") String verificationCode){
        this.authService.verifyAccount(verificationCode);
        return ResponseEntity.ok(ApiResponse.success("Account Verified Successfully, you can now login"));
    }
}
