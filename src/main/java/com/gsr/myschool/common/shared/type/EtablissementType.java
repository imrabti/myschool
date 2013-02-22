package com.gsr.myschool.common.shared.type;

public enum EtablissementType {
    PUBLIQUE("Publique"),
    PRIVE("Privé"),
    HOMOLOGUE("Homologué"),
    AEFE("AEFE"),
    OSUI("OSUI");

    private String label;

    private EtablissementType(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
