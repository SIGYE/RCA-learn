package com.practice.neBanking.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.practice.neBanking.validators.ValidPassword;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateCustomerDTO {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Email
    private String email;
    @NotBlank
    @Pattern(regexp = "^\\+250\\d{9}$", message = "Your number is invalid, we expect +2507****")
    private String mobile;

    @PastOrPresent(message = "Date of birth should be in the past")
    @NotNull(message = "Date of birth should not be blank")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

    @NotNull
    @DecimalMin(value = "0.1", message = "Balance should be greater than 0", inclusive = false)
    private Double balance;

    @ValidPassword
    private String password;

    @NotBlank
    private String role;
}
