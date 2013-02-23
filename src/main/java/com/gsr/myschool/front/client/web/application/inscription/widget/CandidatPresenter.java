package com.gsr.myschool.front.client.web.application.inscription.widget;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.mvp.ValidatedView;
import com.gsr.myschool.common.client.proxy.CandidatProxy;
import com.gsr.myschool.common.client.request.ValidatedReceiverImpl;
import com.gsr.myschool.front.client.place.NameTokens;
import com.gsr.myschool.front.client.request.FrontRequestFactory;
import com.gsr.myschool.front.client.request.InscriptionRequest;
import com.gsr.myschool.front.client.web.application.inscription.WizardStep;
import com.gsr.myschool.front.client.web.application.inscription.event.ChangeStepEvent;
import com.gsr.myschool.front.client.web.application.inscription.event.DisplayStepEvent;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class CandidatPresenter extends PresenterWidget<CandidatPresenter.MyView>
        implements ChangeStepEvent.ChangeStepHandler {
    public interface MyView extends ValidatedView {
        void editCandidat(CandidatProxy candidat);

        void flushCandidat();
    }

    private final FrontRequestFactory requestFactory;
    private final PlaceManager placeManager;

    private InscriptionRequest currentContext;
    private CandidatProxy currentCandidat;
    private Boolean candidatViolation;

    @Inject
    public CandidatPresenter(final EventBus eventBus, final MyView view,
                             final FrontRequestFactory requestFactory,
                             final PlaceManager placeManager) {
        super(eventBus, view);

        this.requestFactory = requestFactory;
        this.placeManager = placeManager;
    }

    @Override
    public void onChangeStep(final ChangeStepEvent event) {
        if (event.getCurrentStep() == WizardStep.STEP_2) {
            getView().flushCandidat();

            if (currentCandidat.getBacSerie() != null) {
                currentCandidat.setBacSerie(currentContext.edit(currentCandidat.getBacSerie()));
            }

            if (currentCandidat.getBacYear() != null) {
                currentCandidat.setBacYear(currentContext.edit(currentCandidat.getBacYear()));
            }

            if (currentCandidat.getNationality() != null) {
                currentCandidat.setNationality(currentContext.edit(currentCandidat.getNationality()));
            }

            if (!candidatViolation) {
                currentContext.updateCandidat(currentCandidat).fire(new ValidatedReceiverImpl<CandidatProxy>() {
                    @Override
                    public void onValidationError(Set<ConstraintViolation<?>> violations) {
                        getView().clearErrors();
                        getView().showErrors(violations);
                        candidatViolation = true;
                    }

                    @Override
                    public void onSuccess(CandidatProxy result) {
                        currentContext = requestFactory.inscriptionService();
                        currentCandidat = currentContext.edit(result);
                        candidatViolation = false;

                        getView().clearErrors();
                        getView().editCandidat(currentCandidat);

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

    public void editData(CandidatProxy candidatProxy) {
        currentContext = requestFactory.inscriptionService();
        currentCandidat = currentContext.edit(candidatProxy);
        candidatViolation = false;

        getView().editCandidat(currentCandidat);
    }

    @Override
    protected void onBind() {
        addRegisteredHandler(ChangeStepEvent.getType(), this);
    }
}
