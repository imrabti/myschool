package com.gsr.myschool.common.shared.type;

public enum DossierStatus {
    CREATED("Créé"),
    SUBMITED("Soumis"),
    RECEIVED("Reçu"),
    REJECTED("Rejeté"),
    ACCEPTED_FOR_STUDY("Accepté pour étude"),
    STANDBY("Créé"),
    ACCEPTED_FOR_TEST("Accepté pour test"),
    INVITED_TO_TEST("Convoqué"),
    NOT_ACCEPTED_FOR_TEST("Refusé");

    private String label;

    private DossierStatus(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
