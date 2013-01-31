package com.gsr.myschool.common.shared.type;

public enum ValueTypeCode {
    REGEXP("Expression régulière"), BAC_YEAR("Année du Bac"), BAC_SERIE("Série du Bac");

    private String label;

    private ValueTypeCode(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
