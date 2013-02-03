package com.gsr.myschool.front.client.web.application.inscription;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.security.LoggedInGatekeeper;
import com.gsr.myschool.front.client.place.NameTokens;
import com.gsr.myschool.front.client.request.FrontRequestFactory;
import com.gsr.myschool.front.client.web.application.ApplicationPresenter;
import com.gsr.myschool.front.client.web.application.inscription.EditInscriptionPresenter.MyProxy;
import com.gsr.myschool.front.client.web.application.inscription.EditInscriptionPresenter.MyView;
import com.gsr.myschool.front.client.web.application.inscription.event.ChangeStepEvent;
import com.gsr.myschool.front.client.web.application.inscription.event.DisplayStepEvent;
import com.gsr.myschool.front.client.web.application.inscription.widget.CandidatPresenter;
import com.gsr.myschool.front.client.web.application.inscription.widget.FrateriePresenter;
import com.gsr.myschool.front.client.web.application.inscription.widget.NiveauScolairePresenter;
import com.gsr.myschool.front.client.web.application.inscription.widget.ParentPresenter;
import com.gsr.myschool.front.client.web.application.inscription.widget.ScolariteAnterieurPresenter;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

public class EditInscriptionPresenter extends Presenter<MyView, MyProxy>
        implements EditInscriptionUiHandlers, DisplayStepEvent.DisplayStepHandler {
    public interface MyView extends View, HasUiHandlers<EditInscriptionUiHandlers> {
        void goToStep(WizardStep step);
    }

    @ProxyStandard
    @NameToken(NameTokens.editinscription)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface MyProxy extends ProxyPlace<EditInscriptionPresenter> {
    }

    public static final Object TYPE_Step_1_Content = new Object();
    public static final Object TYPE_Step_2_Content = new Object();
    public static final Object TYPE_Step_3_Content = new Object();
    public static final Object TYPE_Step_4_Content = new Object();
    public static final Object TYPE_Step_5_Content = new Object();

    private final FrontRequestFactory requestFactory;
    private final ParentPresenter parentPresenter;
    private final CandidatPresenter candidatPresenter;
    private final FrateriePresenter frateriePresenter;
    private final ScolariteAnterieurPresenter scolariteAnterieurPresenter;
    private final NiveauScolairePresenter niveauScolairePresenter;

    private DossierProxy currentDossier;

    @Inject
    public EditInscriptionPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
                                    final FrontRequestFactory requestFactory,
                                    final ParentPresenter parentPresenter,
                                    final CandidatPresenter candidatPresenter,
                                    final FrateriePresenter frateriePresenter,
                                    final ScolariteAnterieurPresenter scolariteAnterieurPresenter,
                                    final NiveauScolairePresenter niveauScolairePresenter) {
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);

        this.requestFactory = requestFactory;
        this.parentPresenter = parentPresenter;
        this.candidatPresenter = candidatPresenter;
        this.frateriePresenter = frateriePresenter;
        this.scolariteAnterieurPresenter = scolariteAnterieurPresenter;
        this.niveauScolairePresenter = niveauScolairePresenter;

        getView().setUiHandlers(this);
    }

    @Override
    public void prepareFromRequest(PlaceRequest placeRequest) {
        Long dossierId = Long .parseLong(placeRequest.getParameter("id", null));
        requestFactory.inscriptionService().findDossierById(dossierId).fire(new ReceiverImpl<DossierProxy>() {
            @Override
            public void onSuccess(DossierProxy result) {
                currentDossier = result;
                parentPresenter.editData(currentDossier.getInfoParent());
                candidatPresenter.editData(currentDossier.getCandidat());
                frateriePresenter.editData(currentDossier);
                scolariteAnterieurPresenter.editData(currentDossier);
                niveauScolairePresenter.editData(currentDossier);
            }
        });
    }

    @Override
    public void onDisplayStep(DisplayStepEvent event) {
        getView().goToStep(event.getStep());
    }

    @Override
    public void changeStep(WizardStep currentStep, WizardStep nextStep) {
        ChangeStepEvent.fire(this, currentStep, nextStep);
    }

    @Override
    protected void onBind() {
        addRegisteredHandler(DisplayStepEvent.getType(), this);
    }

    @Override
    protected void onReveal() {
        getView().goToStep(WizardStep.STEP_1);

        setInSlot(TYPE_Step_1_Content, parentPresenter);
        setInSlot(TYPE_Step_2_Content, candidatPresenter);
        setInSlot(TYPE_Step_3_Content, frateriePresenter);
        setInSlot(TYPE_Step_4_Content, scolariteAnterieurPresenter);
        setInSlot(TYPE_Step_5_Content, niveauScolairePresenter);
    }
}
