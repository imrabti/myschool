package com.gsr.myschool.front.client.web.application.inscription.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.gsr.myschool.front.client.web.application.inscription.WizardStep;

public class ChangeStepEvent extends GwtEvent<ChangeStepEvent.ChangeStepHandler> {
    public static Type<ChangeStepHandler> TYPE = new Type<ChangeStepHandler>();

    public interface ChangeStepHandler extends EventHandler {
        void onChangeStep(ChangeStepEvent event);
    }

    private WizardStep currentStep;
    private WizardStep nextStep;

    public ChangeStepEvent(WizardStep currentStep, WizardStep nextStep) {
        this.currentStep = currentStep;
        this.nextStep = nextStep;
    }

    public static Type<ChangeStepHandler> getType() {
        return TYPE;
    }

    public static void fire(HasHandlers source, WizardStep currentStep, WizardStep nextStep) {
        source.fireEvent(new ChangeStepEvent(currentStep, nextStep));
    }

    public WizardStep getCurrentStep() {
        return currentStep;
    }

    public WizardStep getNextStep() {
        return nextStep;
    }

    @Override
    public Type<ChangeStepHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ChangeStepHandler handler) {
        handler.onChangeStep(this);
    }
}
