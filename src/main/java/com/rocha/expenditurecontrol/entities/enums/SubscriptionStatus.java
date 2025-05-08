package com.rocha.expenditurecontrol.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SubscriptionStatus {
    LATE("Vencido"),
    PENDING("Pendente"),
    PAID("Pago");


    private String status;

    SubscriptionStatus(String status) {
        this.status = status;
    }

    @JsonValue
    public String getStatus() {
        return status;
    }

    @JsonCreator
    public static SubscriptionStatus fromString(String value) {
        for (SubscriptionStatus status : SubscriptionStatus.values()) {
            if (status.name().equalsIgnoreCase(value) || status.getStatus().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid value from status: " + value);
    }
}
