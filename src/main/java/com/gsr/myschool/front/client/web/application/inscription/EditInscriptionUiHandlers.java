package com.gsr.myschool.front.client.web.application.inscription;

import com.gwtplatform.mvp.client.UiHandlers;

public interface EditInscriptionUiHandlers extends UiHandlers {
    void changeStep(WizardStep currentStep, WizardStep nextStep);

    void saveAndExit(WizardStep currentStep);
}
