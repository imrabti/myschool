package com.gsr.myschool.common.shared.type;

public enum DossierStatus {
    CREATED("Créé"),
    SUBMITED("Soumis"),
    RECEIVED("Reçu"),
    REJECTED("Rejeté"),
    ACCEPTED_FOR_STUDY("Accepté pour étude"),
    STANDBY("En attente"),
    ACCEPTED_FOR_TEST("Accepté pour test"),
    INVITED_TO_TEST("Convoqué pour test"),
    NOT_ACCEPTED_FOR_TEST("Non séléctionné");

    private String label;

    private DossierStatus(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
