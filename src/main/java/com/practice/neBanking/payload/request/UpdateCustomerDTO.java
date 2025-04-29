package com.practice.neBanking.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UpdateCustomerDTO {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Email
    private String email;
    @NotBlank
    @Pattern(regexp = "^\\+250\\d{9}$", message = "Your number is invalid, we expect +2507****")
    private String mobile;

    @PastOrPresent(message = "Date of birth must be in the past")
    @NotNull(message = "Date of birth must not be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;
}
