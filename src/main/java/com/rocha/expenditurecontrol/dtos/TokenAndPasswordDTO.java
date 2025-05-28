package com.rocha.expenditurecontrol.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TokenAndPasswordDTO(
        @NotBlank(message = "Email is required")
        @Email(message = "Email format is invalid")
        String email,
        @NotBlank(message = "Token is required")
        String token,
        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must be at least 8 characters")
        String password){
}
