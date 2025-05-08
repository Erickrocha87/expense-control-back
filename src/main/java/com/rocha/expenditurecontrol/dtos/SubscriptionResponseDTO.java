package com.rocha.expenditurecontrol.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rocha.expenditurecontrol.entities.Frequency;
import com.rocha.expenditurecontrol.entities.SubscriptionStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SubscriptionResponseDTO(
        Long id,
        String serviceName,
        BigDecimal price,
        LocalDate dueDate,
        Frequency frequency,
        SubscriptionStatus status) {
}
