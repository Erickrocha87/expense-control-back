package com.rocha.expenditurecontrol.dtos;

import com.rocha.expenditurecontrol.entities.enums.SubscriptionFrequency;
import com.rocha.expenditurecontrol.entities.enums.SubscriptionStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SubscriptionResponseDTO(
        Long id,
        String serviceName,
        BigDecimal price,
        LocalDate dueDate,
        SubscriptionFrequency frequency,
        SubscriptionStatus status) {
}
