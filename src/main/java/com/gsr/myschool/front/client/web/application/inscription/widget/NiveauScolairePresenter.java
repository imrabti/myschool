package com.gsr.myschool.front.client.web.application.inscription.widget;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.mvp.ValidatedView;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.client.request.ValidatedReceiverImpl;
import com.gsr.myschool.front.client.request.FrontRequestFactory;
import com.gsr.myschool.front.client.request.InscriptionRequest;
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

    private InscriptionRequest currentContext;
    private DossierProxy currentDossier;
    private Boolean niveauScolaireViolation;

    @Inject
    public NiveauScolairePresenter(final EventBus eventBus, final MyView view,
                                   final FrontRequestFactory requestFactory) {
        super(eventBus, view);

        this.requestFactory = requestFactory;
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
