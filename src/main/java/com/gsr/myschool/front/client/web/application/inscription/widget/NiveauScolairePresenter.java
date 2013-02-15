package com.gsr.myschool.front.client.web.application.inscription.widget;

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.mvp.ValidatedView;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.client.request.ValidatedReceiverImpl;
import com.gsr.myschool.common.client.widget.messages.CloseDelay;
import com.gsr.myschool.common.client.widget.messages.Message;
import com.gsr.myschool.common.client.widget.messages.event.MessageEvent;
import com.gsr.myschool.front.client.place.NameTokens;
import com.gsr.myschool.front.client.request.FrontRequestFactory;
import com.gsr.myschool.front.client.request.InscriptionRequest;
import com.gsr.myschool.front.client.resource.message.MessageBundle;
import com.gsr.myschool.front.client.web.application.inscription.WizardStep;
import com.gsr.myschool.front.client.web.application.inscription.event.ChangeStepEvent;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class NiveauScolairePresenter extends PresenterWidget<NiveauScolairePresenter.MyView>
        implements ChangeStepEvent.ChangeStepHandler {
    public interface MyView extends ValidatedView {
        void editDossier(DossierProxy dossier);

        void flushDossier();
    }

    private final FrontRequestFactory requestFactory;
    private final PlaceManager placeManager;
    private final MessageBundle messageBundle;

    private InscriptionRequest currentContext;
    private DossierProxy currentDossier;
    private Boolean niveauScolaireViolation;

    @Inject
    public NiveauScolairePresenter(final EventBus eventBus, final MyView view,
                                   final FrontRequestFactory requestFactory,
                                   final PlaceManager placeManager,
                                   final MessageBundle messageBundle) {
        super(eventBus, view);

        this.requestFactory = requestFactory;
        this.placeManager = placeManager;
        this.messageBundle = messageBundle;
    }

    @Override
    public void onChangeStep(ChangeStepEvent event) {
        if (event.getCurrentStep() == WizardStep.STEP_5) {
            getView().flushDossier();

            if (currentDossier.getFiliere() != null) {
                currentDossier.setFiliere(currentContext.edit(currentDossier.getFiliere()));
            }

            if (currentDossier.getNiveauEtude() != null) {
                currentDossier.setNiveauEtude(currentContext.edit(currentDossier.getNiveauEtude()));
            }

            if (!niveauScolaireViolation) {
                currentContext.updateDossier(currentDossier).fire(new ValidatedReceiverImpl<DossierProxy>() {
                    @Override
                    public void onValidationError(Set<ConstraintViolation<?>> violations) {
                        getView().clearErrors();
                        getView().showErrors(violations);
                        niveauScolaireViolation = true;
                    }

                    @Override
                    public void onSuccess(DossierProxy result) {
                        currentContext = requestFactory.inscriptionService();
                        currentDossier = currentContext.edit(result);
                        niveauScolaireViolation = false;

                        getView().clearErrors();
                        getView().editDossier(currentDossier);

                        Message message = new Message.Builder(messageBundle.newInscriptionSuccess())
                                .style(AlertType.SUCCESS).closeDelay(CloseDelay.DEFAULT).build();
                        MessageEvent.fire(this, message);
                        placeManager.revealPlace(new PlaceRequest(NameTokens.getInscription()));
                    }
                });
            } else {
                currentContext.fire();
            }
        }
    }

    public void editData(DossierProxy dossierProxy) {
        currentContext = requestFactory.inscriptionService();
        currentDossier = currentContext.edit(dossierProxy);
        niveauScolaireViolation = false;

        getView().editDossier(currentDossier);
    }

    @Override
    protected void onBind() {
        addRegisteredHandler(ChangeStepEvent.getType(), this);
    }
}
