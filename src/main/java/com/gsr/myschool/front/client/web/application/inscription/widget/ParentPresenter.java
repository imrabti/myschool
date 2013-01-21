package com.gsr.myschool.front.client.web.application.inscription.widget;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.front.client.web.application.inscription.EditInscriptionView.WizardStep;
import com.gsr.myschool.front.client.web.application.inscription.event.ChangeStepEvent;
import com.gsr.myschool.front.client.web.application.inscription.event.DisplayStepEvent;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class ParentPresenter extends PresenterWidget<ParentPresenter.MyView>
        implements ChangeStepEvent.ChangeStepHandler {
    public interface MyView extends View {
    }

    @Inject
    public ParentPresenter(final EventBus eventBus, final MyView view) {
        super(eventBus, view);
    }

    @Override
    public void onChangeStep(ChangeStepEvent event) {
        if (event.getCurrentStep() == WizardStep.STEP_1) {
            // TODO : Save parent info.
            DisplayStepEvent.fire(this, event.getNextStep());
        }
    }

    @Override
    protected void onBind() {
        addRegisteredHandler(ChangeStepEvent.getType(), this);
    }
}