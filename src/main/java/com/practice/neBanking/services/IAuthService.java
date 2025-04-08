package com.practice.neBanking.services;

public interface IAuthService {
    JwtAuthenticationResponse login(String email, String password);
    void initiatePassword(String email);
    void resetPassword(String email, String passwordResetCode, String newPassword);
    void initiateAccountVerification(String email);
    void verifyAccount(String activationCode);
}
