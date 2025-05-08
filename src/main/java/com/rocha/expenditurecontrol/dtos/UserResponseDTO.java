package com.rocha.expenditurecontrol.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rocha.expenditurecontrol.entities.Subscription;

import java.util.List;

public record UserResponseDTO (
        Long id,
        String email,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        List<SubscriptionResponseDTO> subscriptions) {
}
