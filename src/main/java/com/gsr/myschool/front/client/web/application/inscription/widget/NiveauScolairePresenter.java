package com.gsr.myschool.front.client.web.application.inscription.widget;

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.mvp.ValidatedView;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.client.request.ValidatedReceiverImpl;
import com.gsr.myschool.common.client.ui.dossier.NiveauScolaireEditor;
import com.gsr.myschool.common.client.widget.messages.CloseDelay;
import com.gsr.myschool.common.client.widget.messages.Message;
import com.gsr.myschool.common.client.widget.messages.event.MessageEvent;
import com.gsr.myschool.front.client.request.FrontRequestFactory;
import com.gsr.myschool.front.client.request.InscriptionRequest;
import com.gsr.myschool.front.client.resource.message.MessageBundle;
import com.gsr.myschool.front.client.web.application.inscription.WizardStep;
import com.gsr.myschool.front.client.web.application.inscription.event.ChangeStepEvent;
import com.gsr.myschool.front.client.web.application.inscription.event.DisplayStepEvent;
import com.gwtplatform.mvp.client.PresenterWidget;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class NiveauScolairePresenter extends PresenterWidget<NiveauScolairePresenter.MyView>
        implements ChangeStepEvent.ChangeStepHandler {
    public interface MyView extends ValidatedView {
        void editDossier(DossierProxy dossier);

        void flushDossier();
    }

    private final FrontRequestFactory requestFactory;
    private final MessageBundle messageBundle;

    private InscriptionRequest currentContext;
    private DossierProxy currentDossier;
    private Boolean niveauScolaireViolation;

    @Inject
    public NiveauScolairePresenter(final EventBus eventBus, final MyView view,
                                   final FrontRequestFactory requestFactory,
                                   final MessageBundle messageBundle) {
        super(eventBus, view);

        this.requestFactory = requestFactory;
        this.messageBundle = messageBundle;
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

            if ((NiveauScolaireEditor.MISSION.equals(currentDossier.getFiliere().getNom())
                    || NiveauScolaireEditor.BILINGUE.equals(currentDossier.getFiliere().getNom())) && currentDossier.getNiveauEtude2() == null) {
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

                        DisplayStepEvent.fire(this, event.getNextStep());
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
