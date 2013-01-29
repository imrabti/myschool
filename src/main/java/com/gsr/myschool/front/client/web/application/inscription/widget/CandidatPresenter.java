package com.gsr.myschool.front.client.web.application.inscription.widget;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.mvp.ValidatedView;
import com.gsr.myschool.common.client.proxy.CandidatProxy;
import com.gsr.myschool.common.client.request.ValidatedReceiverImpl;
import com.gsr.myschool.front.client.request.FrontRequestFactory;
import com.gsr.myschool.front.client.request.InscriptionRequest;
import com.gsr.myschool.front.client.web.application.inscription.WizardStep;
import com.gsr.myschool.front.client.web.application.inscription.event.ChangeStepEvent;
import com.gsr.myschool.front.client.web.application.inscription.event.DisplayStepEvent;
import com.gwtplatform.mvp.client.PresenterWidget;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class CandidatPresenter extends PresenterWidget<CandidatPresenter.MyView>
        implements ChangeStepEvent.ChangeStepHandler {
    public interface MyView extends ValidatedView {
        void editCandidat(CandidatProxy candidat);

        void flushCandidat();
    }

    private final FrontRequestFactory requestFactory;

    private InscriptionRequest currentContext;
    private CandidatProxy currentCandidat;

    @Inject
    public CandidatPresenter(final EventBus eventBus, final MyView view,
                             final FrontRequestFactory requestFactory) {
        super(eventBus, view);

        this.requestFactory = requestFactory;
    }

    @Override
    public void onChangeStep(final ChangeStepEvent event) {
        if (event.getCurrentStep() == WizardStep.STEP_2) {
            getView().flushCandidat();

            currentContext.updateCandidat(currentCandidat).fire(new ValidatedReceiverImpl<CandidatProxy>() {
                @Override
                public void onValidationError(Set<ConstraintViolation<?>> violations) {
                    getView().clearErrors();
                    getView().showErrors(violations);
                }

                @Override
                public void onSuccess(CandidatProxy result) {
                    currentContext = requestFactory.inscriptionService();
                    currentCandidat = currentContext.edit(result);

                    getView().clearErrors();
                    getView().editCandidat(currentCandidat);

                    DisplayStepEvent.fire(this, event.getNextStep());
                }
            });
        }
    }

    public void editData(CandidatProxy candidatProxy) {
        currentContext = requestFactory.inscriptionService();
        currentCandidat = currentContext.edit(candidatProxy);

        getView().editCandidat(currentCandidat);
    }

    @Override
    protected void onBind() {
        addRegisteredHandler(ChangeStepEvent.getType(), this);
    }
}
