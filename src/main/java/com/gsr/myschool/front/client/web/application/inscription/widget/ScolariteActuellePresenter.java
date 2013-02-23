package com.gsr.myschool.front.client.web.application.inscription.widget;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.mvp.ValidatedView;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.client.proxy.EtablissementScolaireProxy;
import com.gsr.myschool.common.client.proxy.ScolariteActuelleDTOProxy;
import com.gsr.myschool.common.client.proxy.ScolariteActuelleProxy;
import com.gsr.myschool.common.client.request.ValidatedReceiverImpl;
import com.gsr.myschool.common.shared.type.TypeEnseignement;
import com.gsr.myschool.front.client.place.NameTokens;
import com.gsr.myschool.front.client.request.FrontRequestFactory;
import com.gsr.myschool.front.client.request.InscriptionRequest;
import com.gsr.myschool.front.client.web.application.inscription.WizardStep;
import com.gsr.myschool.front.client.web.application.inscription.event.ChangeStepEvent;
import com.gsr.myschool.front.client.web.application.inscription.event.DisplayStepEvent;
import com.gsr.myschool.front.client.web.application.inscription.event.EtablissementSelectedEvent;
import com.gsr.myschool.front.client.web.application.inscription.popup.EtablissementFilterPresenter;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class ScolariteActuellePresenter extends PresenterWidget<ScolariteActuellePresenter.MyView>
        implements ScolariteActuelleUiHandlers, ChangeStepEvent.ChangeStepHandler,
        EtablissementSelectedEvent.EtablissementSelectedHandler  {
    public interface MyView extends ValidatedView, HasUiHandlers<ScolariteActuelleUiHandlers> {
        void editScolariteAnterieur(ScolariteActuelleDTOProxy scolariteAnterieur);

        void flushScolariteActuelle();

        void setEtablissement(EtablissementScolaireProxy selectedEtablissement);
    }

    private final FrontRequestFactory requestFactory;
    private final EtablissementFilterPresenter etablissementFilterPresenter;
    private final PlaceManager placeManager;

    private InscriptionRequest currentContext;
    private ScolariteActuelleDTOProxy currentScolarite;
    private EtablissementScolaireProxy selectedEtablissement;
    private Boolean scolariteActuelleViolation;

    @Inject
    public ScolariteActuellePresenter(final EventBus eventBus, final MyView view,
                                      final FrontRequestFactory requestFactory,
                                      final PlaceManager placeManager,
                                      final EtablissementFilterPresenter etablissementFilterPresenter) {
        super(eventBus, view);

        this.requestFactory = requestFactory;
        this.placeManager = placeManager;
        this.etablissementFilterPresenter = etablissementFilterPresenter;

        getView().setUiHandlers(this);
    }

    @Override
    public void onChangeStep(final ChangeStepEvent event) {
        if (event.getCurrentStep() == WizardStep.STEP_3) {
            getView().flushScolariteActuelle();

            if (selectedEtablissement != null) {
                currentScolarite.setEtablissement(currentContext.edit(selectedEtablissement));
            } else {
                currentScolarite.setEtablissement(null);
            }

            if (currentScolarite.getNiveauEtude() != null) {
                currentScolarite.setNiveauEtude(currentContext.edit(currentScolarite.getNiveauEtude()));
            } else {
                currentScolarite.setNiveauEtude(null);
            }

            if (!scolariteActuelleViolation) {
                currentContext.updateScolariteActuelle(currentScolarite).fire(
                        new ValidatedReceiverImpl<ScolariteActuelleProxy>() {
                    @Override
                    public void onValidationError(Set<ConstraintViolation<?>> violations) {
                        getView().clearErrors();
                        getView().showErrors(violations);
                        scolariteActuelleViolation = true;
                    }

                    @Override
                    public void onSuccess(ScolariteActuelleProxy response) {
                        currentContext = requestFactory.inscriptionService();
                        currentScolarite = currentContext.create(ScolariteActuelleDTOProxy.class);
                        scolariteActuelleViolation = false;

                        prepareScolariteActuelleDTO(response);
                        getView().clearErrors();
                        getView().editScolariteAnterieur(currentScolarite);

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

    @Override
    public void onEtablissementSelected(EtablissementSelectedEvent event) {
        getView().setEtablissement(event.getSelectedEtablissement());
        selectedEtablissement = event.getSelectedEtablissement();
    }

    @Override
    public void selectEtablissement() {
        addToPopupSlot(etablissementFilterPresenter);
    }

    public void editData(DossierProxy dossierProxy) {
        currentContext = requestFactory.inscriptionService();
        currentScolarite = currentContext.create(ScolariteActuelleDTOProxy.class);
        scolariteActuelleViolation = false;

        prepareScolariteActuelleDTO(dossierProxy.getScolariteActuelle());
        getView().editScolariteAnterieur(currentScolarite);
    }

    @Override
    protected void onBind() {
        addRegisteredHandler(ChangeStepEvent.getType(), this);
        addRegisteredHandler(EtablissementSelectedEvent.getType(), this);
    }

    private void prepareScolariteActuelleDTO(ScolariteActuelleProxy scolariteActuelle) {
        scolariteActuelle = currentContext.edit(scolariteActuelle);
        selectedEtablissement = scolariteActuelle.getEtablissement();

        currentScolarite.setId(scolariteActuelle.getId());
        currentScolarite.setNiveauEtude(scolariteActuelle.getNiveauEtude());

        if (scolariteActuelle.getFiliere() != null) {
            String nomFiliere = scolariteActuelle.getFiliere().getNom();
            currentScolarite.setTypeEnseignement(TypeEnseignement.getByNomFilere(nomFiliere));
        }
    }
}
