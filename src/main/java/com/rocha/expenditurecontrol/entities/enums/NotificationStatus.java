package com.rocha.expenditurecontrol.entities.enums;

public enum NotificationStatus {
    PENDING("Pending"),
    FAILED("Failed"),
    SENT("Sent"),;

    private String status;

    NotificationStatus(String status) {
        this.status = status;
    }
    public String getFrequency() {
        return status;
    }
}
