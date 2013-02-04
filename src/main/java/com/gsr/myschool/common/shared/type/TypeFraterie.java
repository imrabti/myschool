package com.gsr.myschool.common.shared.type;

public enum TypeFraterie {
    FRERE("Frère"),
    SOEUR("Soeur");

    private String label;

    private TypeFraterie(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
