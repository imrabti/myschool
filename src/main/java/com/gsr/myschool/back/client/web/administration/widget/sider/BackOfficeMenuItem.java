package com.gsr.myschool.back.client.web.administration.widget.sider;

public enum BackOfficeMenuItem {
    PRE_INSCRIPTION("PRE-INSCRIPTIONS"),
    RECEPTION("RECEPTION"),
    VALIDATION("VALIDATION");

    private String label;

    private BackOfficeMenuItem(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
