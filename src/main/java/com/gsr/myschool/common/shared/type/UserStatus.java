package com.gsr.myschool.common.shared.type;

public enum UserStatus {
    INACTIVE("Inactif"),
    ACTIVE("Actif");

    private String label;

    private UserStatus(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
