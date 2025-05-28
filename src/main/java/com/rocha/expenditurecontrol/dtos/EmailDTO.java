package com.rocha.expenditurecontrol.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailDTO(
        @NotBlank(message = "Email is required")
        @Email(message = "Email format is invalid")
        String email) {
}
