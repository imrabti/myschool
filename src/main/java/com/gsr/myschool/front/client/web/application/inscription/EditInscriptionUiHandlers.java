package com.gsr.myschool.front.client.web.application.inscription;

import com.gwtplatform.mvp.client.UiHandlers;
import com.gsr.myschool.front.client.web.application.inscription.EditInscriptionView.WizardStep;

public interface EditInscriptionUiHandlers extends UiHandlers {
    void changeStep(WizardStep currentStep, WizardStep nextStep);
}
