package com.rocha.expenditurecontrol.mapper;

import com.rocha.expenditurecontrol.dtos.subscription.SubscriptionRequestDTO;
import com.rocha.expenditurecontrol.dtos.subscription.SubscriptionResponseDTO;
import com.rocha.expenditurecontrol.entities.Subscription;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SubscriptionMapper {

    public Subscription toSubscription(SubscriptionRequestDTO subscription) {
        return new Subscription(
                subscription.getId(),
                subscription.getServiceName(),
                subscription.getPrice(),
                subscription.getDueDate(),
                subscription.getFrequency(),
                subscription.getStatus(),
                subscription.getUser());

    }

    public SubscriptionResponseDTO toSubscriptionResponseDTO(Subscription request) {
        return new SubscriptionResponseDTO(
                request.getId(),
                request.getServiceName(),
                request.getPrice(),
                request.getDueDate(),
                request.getFrequency(),
                request.getStatus()
        );
    }
}
