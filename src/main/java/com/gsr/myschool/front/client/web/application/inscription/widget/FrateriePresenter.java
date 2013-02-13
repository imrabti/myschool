package com.gsr.myschool.front.client.web.application.inscription.widget;

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.mvp.ValidatedView;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.client.proxy.FraterieProxy;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.request.ValidatedReceiverImpl;
import com.gsr.myschool.common.client.widget.messages.CloseDelay;
import com.gsr.myschool.common.client.widget.messages.Message;
import com.gsr.myschool.common.client.widget.messages.event.MessageEvent;
import com.gsr.myschool.front.client.request.FrontRequestFactory;
import com.gsr.myschool.front.client.request.InscriptionRequest;
import com.gsr.myschool.front.client.resource.message.MessageBundle;
import com.gsr.myschool.front.client.web.application.inscription.WizardStep;
import com.gsr.myschool.front.client.web.application.inscription.event.ChangeStepEvent;
import com.gsr.myschool.front.client.web.application.inscription.event.DisplayStepEvent;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;

public class FrateriePresenter extends PresenterWidget<FrateriePresenter.MyView>
        implements FraterieUiHandlers, ChangeStepEvent.ChangeStepHandler {
    public interface MyView extends ValidatedView, HasUiHandlers<FraterieUiHandlers> {
        void setData(List<FraterieProxy> data);

        void editFraterie(FraterieProxy fraterie);
    }

    private final FrontRequestFactory requestFactory;
    private final MessageBundle messageBundle;

    private InscriptionRequest currentContext;
    private DossierProxy currentDossier;
    private FraterieProxy currentFraterie;
    private Boolean fraterieViolation;

    @Inject
    public FrateriePresenter(final EventBus eventBus, final MyView view,
                             final FrontRequestFactory requestFactory,
                             final MessageBundle messageBundle) {
        super(eventBus, view);

        this.requestFactory = requestFactory;
        this.messageBundle = messageBundle;

        getView().setUiHandlers(this);
    }

    @Override
    public void onChangeStep(ChangeStepEvent event) {
        if (event.getCurrentStep() == WizardStep.STEP_3) {
            DisplayStepEvent.fire(this, event.getNextStep());
        }
    }

    @Override
    public void addFraterie(FraterieProxy fraterie) {
        Long dossierId = currentDossier.getId();
        if (!fraterieViolation) {
            currentContext.createNewFraterie(fraterie, dossierId).fire(new ValidatedReceiverImpl<Void>() {
                @Override
                public void onValidationError(Set<ConstraintViolation<?>> violations) {
                    getView().clearErrors();
                    getView().showErrors(violations);
                    fraterieViolation = true;
                }

                @Override
                public void onSuccess(Void response) {
                    currentContext = requestFactory.inscriptionService();
                    currentFraterie = currentContext.create(FraterieProxy.class);
                    fraterieViolation = false;

                    getView().editFraterie(currentFraterie);
                    getView().clearErrors();
                    loadFraterie();
                }
            });
        } else {
            currentContext.fire();
        }
    }

    @Override
    public void deleteFraterie(FraterieProxy fraterie) {
        if (Window.confirm(messageBundle.deleteFraterieConf())) {
            Long fraterieId = fraterie.getId();
            requestFactory.inscriptionService().deleteFraterie(fraterieId).fire(new ReceiverImpl<Void>() {
                @Override
                public void onSuccess(Void response) {
                    Message message = new Message.Builder(messageBundle.deleteFraterieSuccess())
                            .style(AlertType.SUCCESS).closeDelay(CloseDelay.DEFAULT).build();
                    MessageEvent.fire(this, message);
                    loadFraterie();
                }
            });
        }
    }

    public void editData(DossierProxy dossierProxy) {
        currentDossier = dossierProxy;
        currentContext = requestFactory.inscriptionService();
        currentFraterie = currentContext.create(FraterieProxy.class);
        fraterieViolation = false;

        getView().editFraterie(currentFraterie);
        loadFraterie();
    }

    @Override
    protected void onBind() {
        addRegisteredHandler(ChangeStepEvent.getType(), this);
    }

    private void loadFraterie() {
        Long dossierId = currentDossier.getId();
        requestFactory.inscriptionService().findFraterieByDossierId(dossierId).fire(new ReceiverImpl<List<FraterieProxy>>() {
            @Override
            public void onSuccess(List<FraterieProxy> result) {
                getView().setData(result);
            }
        });
    }
}
