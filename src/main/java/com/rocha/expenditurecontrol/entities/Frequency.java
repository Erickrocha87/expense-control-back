package com.rocha.expenditurecontrol.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Frequency {
    MONTHLY("Mensal"),
    ANNUALLY("Anual"),
    SEMIANNUALLY("Semestral"),
    QUARTERLY("Trimestral");

    private String frequency;

    Frequency(String value) {
        this.frequency = value;
    }

    @JsonValue
    public String getFrequency() {
        return frequency;
    }
    
    @JsonCreator
    public static Frequency fromString(String value) {
        for (Frequency freq : Frequency.values()) {
            if (freq.name().equalsIgnoreCase(value) || freq.getFrequency().equalsIgnoreCase(value)) {
                return freq;
            }
        }
        throw new IllegalArgumentException("Invalid value from frequency: " + value);
    }
    

}
