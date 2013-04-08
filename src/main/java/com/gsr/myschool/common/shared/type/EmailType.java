package com.gsr.myschool.common.shared.type;

public enum EmailType {
    REGISTRATION("Enregistrement"),
    ACTIVATION("Compte activé"),
    RELANCE("Relance Activation du compte"),
    FORGOTTEN_PASSWORD("Modifier votre mot de passe"),
    DOSSIER_RECEIVED("Dossier reçus"),
    DOSSIER_INCOMPLETE("Dossier incomplet"),
    DOSSIER_COMPLETE("Dossier complet"),
    PIECES_RECEIVED("Pièces reçues"),
    NOT_ACCEPTED_FOR_TEST("Dossier refusé pour test"),
    SESSION_CANCELED("Annulation de session de test concours"),
    CONVOCATED_FOR_TEST("Convocation au test concours d’admission"),
    FINAL_ADMISSION("Acceptation finale"),
    FINAL_REJECTION("Acceptation finale");

    private String label;

    private EmailType(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
