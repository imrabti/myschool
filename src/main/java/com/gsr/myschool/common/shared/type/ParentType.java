package com.gsr.myschool.common.shared.type;

public enum ParentType {
    PERE("Père"),
    MERE("Mère"),
    TUTEUR("Tuteur légal");

    private String label;

    private ParentType(String label) {
        this.label = label;
    }

    public String toString() {
        return label;
    }
}
