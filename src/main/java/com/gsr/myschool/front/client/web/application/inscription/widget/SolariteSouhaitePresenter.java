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
import com.gsr.myschool.common.shared.type.TypeEnseignement;
import com.gsr.myschool.front.client.place.NameTokens;
import com.gsr.myschool.front.client.request.FrontRequestFactory;
import com.gsr.myschool.front.client.request.InscriptionRequest;
import com.gsr.myschool.front.client.resource.message.MessageBundle;
import com.gsr.myschool.front.client.web.application.inscription.WizardStep;
import com.gsr.myschool.front.client.web.application.inscription.event.ChangeStepEvent;
import com.gsr.myschool.front.client.web.application.inscription.event.DisplayStepEvent;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class SolariteSouhaitePresenter extends PresenterWidget<SolariteSouhaitePresenter.MyView>
        implements ChangeStepEvent.ChangeStepHandler {
    public interface MyView extends ValidatedView {
        void editDossier(DossierProxy dossier);

        void flushDossier();
    }

    private final FrontRequestFactory requestFactory;
    private final MessageBundle messageBundle;
    private final PlaceManager placeManager;

    private InscriptionRequest currentContext;
    private DossierProxy currentDossier;
    private Boolean niveauScolaireViolation;

    @Inject
    public SolariteSouhaitePresenter(final EventBus eventBus, final MyView view,
                                     final FrontRequestFactory requestFactory,
                                     final MessageBundle messageBundle,
                                     final PlaceManager placeManager) {
        super(eventBus, view);

        this.requestFactory = requestFactory;
        this.messageBundle = messageBundle;
        this.placeManager = placeManager;
    }

    @Override
    public void onChangeStep(final ChangeStepEvent event) {
        if (event.getCurrentStep() == WizardStep.STEP_4) {
            getView().flushDossier();

            if (currentDossier.getFiliere() != null) {
                currentDossier.setFiliere(currentContext.edit(currentDossier.getFiliere()));
            }

            if (currentDossier.getNiveauEtude() != null) {
                currentDossier.setNiveauEtude(currentContext.edit(currentDossier.getNiveauEtude()));
            }

            if (currentDossier.getFiliere2() != null) {
                currentDossier.setFiliere2(currentContext.edit(currentDossier.getFiliere2()));
            }

            if (currentDossier.getNiveauEtude2() != null) {
                currentDossier.setNiveauEtude2(currentContext.edit(currentDossier.getNiveauEtude2()));
            }

            if ((TypeEnseignement.MISSION.getId() == currentDossier.getFiliere().getId()
                    || TypeEnseignement.BILINGUE.getId() == currentDossier.getFiliere().getId())
                    && currentDossier.getNiveauEtude2() == null) {
                Message message = new Message.Builder(messageBundle.niveauScolaireFaillure())
                        .style(AlertType.ERROR).closeDelay(CloseDelay.DEFAULT).build();
                MessageEvent.fire(this, message);

                return;
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

                        if (event.getNextStep() == null) {
                            placeManager.revealPlace(new PlaceRequest(NameTokens.getInscription()));
                        } else {
                            DisplayStepEvent.fire(this, event.getNextStep());
                        }
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
