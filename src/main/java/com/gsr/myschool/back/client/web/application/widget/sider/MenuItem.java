package com.gsr.myschool.back.client.web.application.widget.sider;

public enum MenuItem {
    PRE_INSCRIPTION("PRE-INSCRIPTIONS"),
    RECEPTION("RECEPTION"),
    VALIDATION("VALIDATION");

    private String label;

    private MenuItem(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}