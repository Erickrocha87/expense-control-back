package com.rocha.expenditurecontrol.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthRequestDTO(
        //@NotBlank(message = "Username is required")
        String username,
        @Size(min = 8, message = "The password must be at least 8 characters long ")
        String password,
        @NotBlank(message = "Email is required")
        String email) {
}
