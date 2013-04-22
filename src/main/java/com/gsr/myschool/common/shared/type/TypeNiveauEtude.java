package com.gsr.myschool.common.shared.type;

public enum TypeNiveauEtude {
    MATERNELLE("Maternelle"),
    PRIMAIRE("Primaire"),
    COLLEGE("Collège"),
    LYCEE("Lycée"),
    SUPERIEUR("Supérieur");

    private String label;

    private TypeNiveauEtude(String label) {
        this.label = label;
    }

    public String toString() {
        return label;
    }
}
