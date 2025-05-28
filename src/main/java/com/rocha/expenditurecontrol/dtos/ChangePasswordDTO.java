package com.rocha.expenditurecontrol.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangePasswordDTO(
        @NotBlank(message = "Password is required")
        String currentPassword,
        @NotBlank(message = "New password is required")
        @Size(min = 8)
        String newPassword,
        @NotBlank(message = "Password is required")
        @Size(min = 8)
        String confirmPassword) {
}
