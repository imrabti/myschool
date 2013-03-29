package com.gsr.myschool.common.shared.type;

public enum SessionStatus {
    CREATED("Créer"),
    OPEN("Ouverte"),
    CLOSED("Cloturée"),
    CANCELED("Annulée");

    private String label;

    private SessionStatus(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
