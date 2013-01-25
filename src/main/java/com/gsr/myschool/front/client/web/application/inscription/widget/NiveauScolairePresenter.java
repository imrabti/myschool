package com.gsr.myschool.front.client.web.application.inscription.widget;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.front.client.web.application.inscription.WizardStep;
import com.gsr.myschool.front.client.web.application.inscription.event.ChangeStepEvent;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class NiveauScolairePresenter extends PresenterWidget<NiveauScolairePresenter.MyView>
        implements ChangeStepEvent.ChangeStepHandler {
    public interface MyView extends View {
    }

    @Inject
    public NiveauScolairePresenter(final EventBus eventBus, final MyView view) {
        super(eventBus, view);
    }

    @Override
    public void onChangeStep(ChangeStepEvent event) {
        if (event.getCurrentStep() == WizardStep.STEP_3) {
            // TODO : Save niveau scolaire info and forward to list inscriptions.
        }
    }

    @Override
    protected void onBind() {
        addRegisteredHandler(ChangeStepEvent.getType(), this);
    }
}
