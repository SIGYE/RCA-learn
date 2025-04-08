package com.practice.neBanking.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ResetPasswordDTO {
    @NotBlank
    private String email;
    @NotBlank
    private String passwordResetCode;
    @ValidPassword
    private String newPassword;
}
