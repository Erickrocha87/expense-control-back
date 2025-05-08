package com.rocha.expenditurecontrol.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SubscriptionFrequency {
    MONTHLY("Mensal"),
    ANNUALLY("Anual"),
    SEMIANNUALLY("Semestral"),
    QUARTERLY("Trimestral");

    private String frequency;

    SubscriptionFrequency(String value) {
        this.frequency = value;
    }

    @JsonValue
    public String getFrequency() {
        return frequency;
    }
    
    @JsonCreator
    public static SubscriptionFrequency fromString(String value) {
        for (SubscriptionFrequency freq : SubscriptionFrequency.values()) {
            if (freq.name().equalsIgnoreCase(value) || freq.getFrequency().equalsIgnoreCase(value)) {
                return freq;
            }
        }
        throw new IllegalArgumentException("Invalid value from frequency: " + value);
    }
    

}
