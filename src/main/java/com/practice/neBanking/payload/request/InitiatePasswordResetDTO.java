package com.practice.neBanking.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class InitiatePasswordResetDTO {
    @NotBlank
    @Email
    private String email;
}
