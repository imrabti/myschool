package com.gsr.myschool.front.client.web.application.inscription.widget;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.mvp.ValidatedView;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.client.proxy.ScolariteActuelleDTOProxy;
import com.gsr.myschool.common.client.proxy.ScolariteActuelleProxy;
import com.gsr.myschool.common.shared.type.TypeEnseignement;
import com.gsr.myschool.front.client.request.FrontRequestFactory;
import com.gsr.myschool.front.client.request.InscriptionRequest;
import com.gsr.myschool.front.client.web.application.inscription.WizardStep;
import com.gsr.myschool.front.client.web.application.inscription.event.ChangeStepEvent;
import com.gsr.myschool.front.client.web.application.inscription.event.DisplayStepEvent;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;

public class ScolariteActuellePresenter extends PresenterWidget<ScolariteActuellePresenter.MyView>
        implements ScolariteActuelleUiHandlers, ChangeStepEvent.ChangeStepHandler  {
    public interface MyView extends ValidatedView, HasUiHandlers<ScolariteActuelleUiHandlers> {
        void editScolariteAnterieur(ScolariteActuelleDTOProxy scolariteAnterieur);

        void flushScolariteActuelle();
    }

    private final FrontRequestFactory requestFactory;

    private InscriptionRequest currentContext;
    private ScolariteActuelleDTOProxy currentScolarite;
    private Boolean scolariteAnterieurViolation;

    @Inject
    public ScolariteActuellePresenter(final EventBus eventBus, final MyView view,
                                      final FrontRequestFactory requestFactory) {
        super(eventBus, view);

        this.requestFactory = requestFactory;

        getView().setUiHandlers(this);
    }

    @Override
    public void onChangeStep(ChangeStepEvent event) {
        if (event.getCurrentStep() == WizardStep.STEP_3) {
            getView().flushScolariteActuelle();

            DisplayStepEvent.fire(this, event.getNextStep());
        }
    }

    public void editData(DossierProxy dossierProxy) {
        currentContext = requestFactory.inscriptionService();
        currentScolarite = currentContext.create(ScolariteActuelleDTOProxy.class);
        scolariteAnterieurViolation = false;

        prepareScolariteActuelleDTO(dossierProxy.getScolariteActuelle());
        getView().editScolariteAnterieur(currentScolarite);
    }

    @Override
    protected void onBind() {
        addRegisteredHandler(ChangeStepEvent.getType(), this);
    }

    private void prepareScolariteActuelleDTO(ScolariteActuelleProxy scolariteActuelle) {
        scolariteActuelle = currentContext.edit(scolariteActuelle);

        currentScolarite.setId(scolariteActuelle.getId());
        currentScolarite.setEtablissement(scolariteActuelle.getEtablissement());
        currentScolarite.setNiveauEtude(scolariteActuelle.getNiveauEtude());

        if (scolariteActuelle.getFiliere() != null) {
            String nomFiliere = scolariteActuelle.getFiliere().getNom();
            currentScolarite.setTypeEnseignement(TypeEnseignement.getByNomFilere(nomFiliere));
        }
    }
}
