package com.rocha.expenditurecontrol.dtos;

import lombok.Builder;

@Builder
public record JWTUserDataDto(Long id, String username, String email) {
}
