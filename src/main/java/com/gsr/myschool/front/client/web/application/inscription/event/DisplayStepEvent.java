package com.gsr.myschool.front.client.web.application.inscription.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.gsr.myschool.front.client.web.application.inscription.WizardStep;

public class DisplayStepEvent extends GwtEvent<DisplayStepEvent.DisplayStepHandler> {
    public static Type<DisplayStepHandler> TYPE = new Type<DisplayStepHandler>();

    public interface DisplayStepHandler extends EventHandler {
        void onDisplayStep(DisplayStepEvent event);
    }

    private WizardStep step;

    public DisplayStepEvent(WizardStep step) {
        this.step = step;
    }

    public static Type<DisplayStepHandler> getType() {
        return TYPE;
    }

    public static void fire(HasHandlers source, WizardStep step) {
        source.fireEvent(new DisplayStepEvent(step));
    }

    public WizardStep getStep() {
        return step;
    }

    @Override
    public Type<DisplayStepHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(DisplayStepHandler handler) {
        handler.onDisplayStep(this);
    }
}
