package com.gsr.myschool.common.shared.type;

public enum Authority {
    ROLE_USER("Utilisateur"),
    ROLE_ADMIN("Administrateur"),
    ROLE_OPERATOR("Operateur"),
    ROLE_REPORTER("Reporter");

    private String label;

    private Authority(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

    public static Authority[] adminRoles() {
        return new Authority[] {ROLE_ADMIN, ROLE_OPERATOR, ROLE_REPORTER};
    }
}
