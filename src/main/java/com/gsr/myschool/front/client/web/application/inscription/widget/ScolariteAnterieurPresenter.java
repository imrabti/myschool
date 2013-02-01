package com.gsr.myschool.front.client.web.application.inscription.widget;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.mvp.ValidatedView;
import com.gsr.myschool.common.client.proxy.ScolariteAnterieurProxy;
import com.gsr.myschool.front.client.request.FrontRequestFactory;
import com.gsr.myschool.front.client.web.application.inscription.WizardStep;
import com.gsr.myschool.front.client.web.application.inscription.event.ChangeStepEvent;
import com.gsr.myschool.front.client.web.application.inscription.event.DisplayStepEvent;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;

import java.util.List;

public class ScolariteAnterieurPresenter extends PresenterWidget<ScolariteAnterieurPresenter.MyView>
        implements ScolariteAnterieurUiHandlers, ChangeStepEvent.ChangeStepHandler  {
    public interface MyView extends ValidatedView, HasUiHandlers<ScolariteAnterieurUiHandlers> {
        void setData(List<ScolariteAnterieurProxy> data);
    }

    private final FrontRequestFactory requestFactory;

    @Inject
    public ScolariteAnterieurPresenter(final EventBus eventBus, final MyView view,
                                       final FrontRequestFactory requestFactory) {
        super(eventBus, view);

        this.requestFactory = requestFactory;

        getView().setUiHandlers(this);
    }

    @Override
    public void onChangeStep(ChangeStepEvent event) {
        if (event.getCurrentStep() == WizardStep.STEP_3) {
            DisplayStepEvent.fire(this, event.getNextStep());
        }
    }

    @Override
    protected void onBind() {
        addRegisteredHandler(ChangeStepEvent.getType(), this);
    }
}
