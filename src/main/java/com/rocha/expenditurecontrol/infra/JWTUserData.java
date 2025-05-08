package com.rocha.expenditurecontrol.infra;

import lombok.Builder;

@Builder
public record JWTUserData(Long id, String username, String email) {
}
