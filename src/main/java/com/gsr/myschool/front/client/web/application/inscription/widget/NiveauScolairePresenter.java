package com.gsr.myschool.front.client.web.application.inscription.widget;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.front.client.request.FrontRequestFactory;
import com.gsr.myschool.front.client.web.application.inscription.WizardStep;
import com.gsr.myschool.front.client.web.application.inscription.event.ChangeStepEvent;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class NiveauScolairePresenter extends PresenterWidget<NiveauScolairePresenter.MyView>
        implements ChangeStepEvent.ChangeStepHandler {
    public interface MyView extends View {
    }

    private final FrontRequestFactory requestFactory;

    @Inject
    public NiveauScolairePresenter(final EventBus eventBus, final MyView view,
                                   final FrontRequestFactory requestFactory) {
        super(eventBus, view);

        this.requestFactory = requestFactory;
    }

    @Override
    public void onChangeStep(ChangeStepEvent event) {
        if (event.getCurrentStep() == WizardStep.STEP_5) {
            // TODO : Save niveau scolaire info and forward to list inscriptions.
        }
    }

    @Override
    protected void onBind() {
        addRegisteredHandler(ChangeStepEvent.getType(), this);
    }
}
