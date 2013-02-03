package com.gsr.myschool.back.client.web.application.preinscription.popup;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.back.client.request.DossierServiceRequest;
import com.gsr.myschool.common.client.mvp.ValidatedPopupView;
import com.gsr.myschool.common.client.proxy.CandidatProxy;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.client.proxy.InfoParentProxy;
import com.gsr.myschool.common.client.proxy.ScolariteAnterieurProxy;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;

import java.util.List;

public class PreInscriptionDetailsPresenter extends PresenterWidget<PreInscriptionDetailsPresenter.MyView>
        implements PreInscriptionDetailsUiHandlers {
    public interface MyView extends ValidatedPopupView, HasUiHandlers<PreInscriptionDetailsUiHandlers> {
        void editParent(InfoParentProxy currentInfoParent);

        void editCandidat(CandidatProxy currentCandidat);

        void setScolariteData(List<ScolariteAnterieurProxy> data);

        void editScolarite(DossierProxy dossierProxy);
    }

    private final BackRequestFactory requestFactory;
    private DossierServiceRequest currentContext;
    private InfoParentProxy currentInfoParent;
    private CandidatProxy currentCandidat;

    @Inject
    public PreInscriptionDetailsPresenter(final EventBus eventBus, final MyView view,
            final BackRequestFactory requestFactory) {
        super(eventBus, view);
        this.requestFactory = requestFactory;

        getView().setUiHandlers(this);
    }

    public void editInscriptionData(DossierProxy dossier) {
        currentContext = requestFactory.dossierService();
        currentInfoParent = currentContext.edit(dossier.getInfoParent());
        currentCandidat = currentContext.edit(dossier.getCandidat());
        loadScolariteAnterieur(dossier);

        getView().editCandidat(currentCandidat);
        getView().editParent(currentInfoParent);
        getView().editScolarite(dossier);
    }

    private void loadScolariteAnterieur(DossierProxy currentDossier) {
        Long dossierId = currentDossier.getId();
        requestFactory.dossierService().findScolariteAnterieursByDossierId(dossierId).fire(
                new ReceiverImpl<List<ScolariteAnterieurProxy>>() {
                    @Override
                    public void onSuccess(List<ScolariteAnterieurProxy> result) {
                        getView().setScolariteData(result);
                    }
                });
    }
}
