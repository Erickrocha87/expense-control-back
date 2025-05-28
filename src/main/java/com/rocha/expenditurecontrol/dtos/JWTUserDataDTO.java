package com.rocha.expenditurecontrol.dtos;

import lombok.Builder;

@Builder
public record JWTUserDataDTO(Long id, String username, String email) {
}
