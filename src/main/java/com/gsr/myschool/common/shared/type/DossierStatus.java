package com.gsr.myschool.common.shared.type;

import java.util.ArrayList;
import java.util.List;

public enum DossierStatus {
    CREATED("Créé"),
    SUBMITTED("Soumis"),
    RECEIVED("Reçu"),
    REJECTED("Rejeté"),
    ACCEPTED_FOR_STUDY("Accepté pour étude"),
    STANDBY("En attente"),
    ACCEPTED_FOR_TEST("Accepté pour test"),
    INVITED_TO_TEST("Convoqué pour test"),
    NOT_ACCEPTED_FOR_TEST("Refusé pour test"),
    AFFECTED("Affecté pour test"),
    NOT_ADMITTED("Non Admis"),
    TO_BE_REGISTERED("Admis à l'inscription");

    private String label;

    private DossierStatus(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

    public static List receptionValues() {
        List<DossierStatus> receptionStatus = new ArrayList<DossierStatus>();
        receptionStatus.add(SUBMITTED);
        receptionStatus.add(STANDBY);
        return receptionStatus;
    }

    public static List confirmationTestValues() {
        List<DossierStatus> receptionStatus = new ArrayList<DossierStatus>();
        receptionStatus.add(ACCEPTED_FOR_STUDY);
        receptionStatus.add(NOT_ACCEPTED_FOR_TEST);
        return receptionStatus;
    }

    public static List affectationValues() {
        List<DossierStatus> receptionStatus = new ArrayList<DossierStatus>();
        receptionStatus.add(ACCEPTED_FOR_TEST);
        receptionStatus.add(INVITED_TO_TEST);
        receptionStatus.add(AFFECTED);
        return receptionStatus;
    }

    public static List admissionValues() {
        List<DossierStatus> admissionStatus = new ArrayList<DossierStatus>();
        admissionStatus.add(INVITED_TO_TEST);
        admissionStatus.add(TO_BE_REGISTERED);
        admissionStatus.add(NOT_ADMITTED);
        return admissionStatus;
    }
}
