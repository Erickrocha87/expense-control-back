package com.rocha.expenditurecontrol.dtos.subscription;

import com.rocha.expenditurecontrol.entities.enums.SubscriptionFrequency;
import com.rocha.expenditurecontrol.entities.User;
import com.rocha.expenditurecontrol.entities.enums.SubscriptionStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionRequestDTO{
    private Long id;

    @NotBlank(message = "Service name is required")
    private String serviceName;

    @Positive(message = "The price must be positive ")
    private BigDecimal price;

    @FutureOrPresent(message = "The due date must be in the present or future ")
    private LocalDate dueDate;

    //@NotBlank(message = "Frequency is required")
    private SubscriptionFrequency frequency;

    //@NotBlank(message = "Frequency is required")
    private SubscriptionStatus status;

    private User user;
}
