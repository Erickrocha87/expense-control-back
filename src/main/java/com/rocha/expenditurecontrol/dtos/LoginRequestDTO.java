package com.rocha.expenditurecontrol.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO (
        @NotBlank(message = "Email is required")
        String email,
        @NotBlank(message = "password is required")
        @Size(min = 8, message = "Password must be at least 8 characters")
        String password) {
}
