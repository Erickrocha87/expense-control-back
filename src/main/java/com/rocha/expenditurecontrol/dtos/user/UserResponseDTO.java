package com.rocha.expenditurecontrol.dtos.user;

public record UserResponseDTO(
        Long id,
        String username,
        String email,
        String profileImageUrl) {
}
