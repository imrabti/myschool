package com.gsr.myschool.common.shared.type;

public enum ValueTypeCode {
    REGEXP("Expression régulière"),
    BAC_YEAR("Année baccalauréat"),
    BAC_SERIE("Serie baccalauréat"),
    NATIONALITY("Nationnalité"),
    SCHOOL_YEAR("Année scolaire");

    private String label;

    private ValueTypeCode(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
