package com.gsr.myschool.front.client.web.application.inscription.widget;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.mvp.ValidatedView;
import com.gsr.myschool.common.client.proxy.InfoParentProxy;
import com.gsr.myschool.common.client.request.ValidatedReceiverImpl;
import com.gsr.myschool.front.client.request.FrontRequestFactory;
import com.gsr.myschool.front.client.request.InscriptionRequest;
import com.gsr.myschool.front.client.web.application.inscription.WizardStep;
import com.gsr.myschool.front.client.web.application.inscription.event.ChangeStepEvent;
import com.gsr.myschool.front.client.web.application.inscription.event.DisplayStepEvent;
import com.gwtplatform.mvp.client.PresenterWidget;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class ParentPresenter extends PresenterWidget<ParentPresenter.MyView>
        implements ChangeStepEvent.ChangeStepHandler {
    public interface MyView extends ValidatedView {
        void editParent(InfoParentProxy infoParent);

        void flushParent();
    }

    private final FrontRequestFactory requestFactory;

    private InscriptionRequest currentContext;
    private InfoParentProxy currentInfoParent;

    @Inject
    public ParentPresenter(final EventBus eventBus, final MyView view,
                           final FrontRequestFactory requestFactory) {
        super(eventBus, view);

        this.requestFactory = requestFactory;
    }

    @Override
    public void onChangeStep(final ChangeStepEvent event) {
        if (event.getCurrentStep() == WizardStep.STEP_1) {
            getView().flushParent();

            currentContext.updateParent(currentInfoParent).fire(new ValidatedReceiverImpl<InfoParentProxy>() {
                @Override
                public void onValidationError(Set<ConstraintViolation<?>> violations) {
                    getView().clearErrors();
                    getView().showErrors(violations);
                }

                @Override
                public void onSuccess(InfoParentProxy result) {
                    currentContext = requestFactory.inscriptionService();
                    currentInfoParent = currentContext.edit(result);

                    getView().clearErrors();
                    getView().editParent(currentInfoParent);

                    DisplayStepEvent.fire(this, event.getNextStep());
                }
            });
        }
    }

    public void editData(InfoParentProxy infoParent) {
        currentContext = requestFactory.inscriptionService();
        currentInfoParent = currentContext.edit(infoParent);

        getView().editParent(currentInfoParent);
    }

    @Override
    protected void onBind() {
        addRegisteredHandler(ChangeStepEvent.getType(), this);
    }
}
