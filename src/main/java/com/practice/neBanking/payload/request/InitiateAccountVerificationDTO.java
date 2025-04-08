package com.practice.neBanking.payload.request;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class InitiateAccountVerificationDTO {

    @Email
    private String email;

}
