package com.rocha.expenditurecontrol.dtos.user;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;

public record UserRequestDTO(
        @Nullable String username,
        @Email(message = "Email format is invalid")
        @Nullable String email) {
}
