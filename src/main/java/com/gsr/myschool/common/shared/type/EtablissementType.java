package com.gsr.myschool.common.shared.type;

public enum EtablissementType {
    PRIVE("Privé"), PUBLIQUE("Publique");

    private String label;

    private EtablissementType(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
