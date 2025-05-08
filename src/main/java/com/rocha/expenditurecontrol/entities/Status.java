package com.rocha.expenditurecontrol.entities;

public enum Status {
    PENDING("Pending"),
    FAILED("Failed"),
    SENT("Sent"),;

    private String status;

    Status(String status) {
        this.status = status;
    }
    public String getFrequency() {
        return status;
    }
}
