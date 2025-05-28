package com.rocha.expenditurecontrol.dtos;

public record UserResponseDTO(
        Long id,
        String username,
        String email,
        String profileImageUrl) {
}
