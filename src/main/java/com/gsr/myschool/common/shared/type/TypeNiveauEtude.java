package com.gsr.myschool.common.shared.type;

public enum TypeNiveauEtude {
    PRIMAIRE("Primaire"),
    SECONDAIRE("Secondaire");

    private String label;

    private TypeNiveauEtude(String label) {
        this.label = label;
    }

    public String toString() {
        return label;
    }
}
