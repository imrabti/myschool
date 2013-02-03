package com.gsr.myschool.front.client.web.application.inscription;

public enum WizardStep {
    STEP_1, STEP_2, STEP_3, STEP_4, STEP_5;

    private WizardStep() {
    }

    public static WizardStep value(Integer order) {
        switch (order) {
            case 0:
                return STEP_1;
            case 1:
                return STEP_2;
            case 2:
                return STEP_3;
            case 3:
                return STEP_4;
            case 4:
                return STEP_5;
            default:
                return STEP_1;
        }
    }
}
