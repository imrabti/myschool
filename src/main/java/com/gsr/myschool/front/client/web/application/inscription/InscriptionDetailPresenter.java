package com.gsr.myschool.front.client.web.application.inscription;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.proxy.CandidatProxy;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.client.proxy.FraterieProxy;
import com.gsr.myschool.common.client.proxy.InfoParentProxy;
import com.gsr.myschool.common.client.proxy.ScolariteAnterieurProxy;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.security.LoggedInGatekeeper;
import com.gsr.myschool.front.client.place.NameTokens;
import com.gsr.myschool.front.client.request.FrontRequestFactory;
import com.gsr.myschool.front.client.web.application.ApplicationPresenter;
import com.gsr.myschool.front.client.web.application.inscription.InscriptionDetailPresenter.MyView;
import com.gsr.myschool.front.client.web.application.inscription.InscriptionDetailPresenter.MyProxy;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import java.util.List;

public class InscriptionDetailPresenter extends Presenter<MyView, MyProxy> {
    public interface MyView extends View {
        void setDossier(DossierProxy dossier);

        void setResponsable(InfoParentProxy infoParent);

        void setCandidat(CandidatProxy candidat);

        void setScolariteAnterieur(List<ScolariteAnterieurProxy> scolariteAnterieurList);

        void setFraterie(List<FraterieProxy> fraterieList);
    }

    @ProxyStandard
    @NameToken(NameTokens.inscriptiondetail)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface MyProxy extends ProxyPlace<InscriptionDetailPresenter> {
    }

    private final FrontRequestFactory requestFactory;

    private DossierProxy currentDossier;

    @Inject
    public InscriptionDetailPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
                                      final FrontRequestFactory requestFactory) {
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);

        this.requestFactory = requestFactory;
    }

    @Override
    public void prepareFromRequest(PlaceRequest placeRequest) {
        Long dossierId = Long .parseLong(placeRequest.getParameter("id", null));
        requestFactory.inscriptionService().findDossierById(dossierId).fire(new ReceiverImpl<DossierProxy>() {
            @Override
            public void onSuccess(DossierProxy result) {
                currentDossier = result;
                getView().setDossier(currentDossier);
                getView().setResponsable(currentDossier.getInfoParent());
                getView().setCandidat(currentDossier.getCandidat());

                loadFraterie();
                loadScolariteAnterieur();
            }
        });
    }

    private void loadScolariteAnterieur() {
        Long dossierId = currentDossier.getId();
        requestFactory.inscriptionService().findScolariteAnterieursByDossierId(dossierId).fire(
                new ReceiverImpl<List<ScolariteAnterieurProxy>>() {
            @Override
            public void onSuccess(List<ScolariteAnterieurProxy> result) {
                getView().setScolariteAnterieur(result);
            }
        });
    }

    private void loadFraterie() {
        Long dossierId = currentDossier.getId();
        requestFactory.inscriptionService().findFraterieByDossierId(dossierId).fire(
                new ReceiverImpl<List<FraterieProxy>>() {
            @Override
            public void onSuccess(List<FraterieProxy> result) {
                getView().setFraterie(result);
            }
        });
    }
}
