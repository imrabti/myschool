package com.gsr.myschool.back.client.web.application.preinscription.popup;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.back.client.request.DossierServiceRequest;
import com.gsr.myschool.common.client.mvp.ValidatedPopupView;
import com.gsr.myschool.common.client.proxy.CandidatProxy;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.client.proxy.InfoParentProxy;
import com.gsr.myschool.common.client.proxy.NiveauEtudeProxy;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;

public class PreInscriptionDetailsPresenter extends PresenterWidget<PreInscriptionDetailsPresenter.MyView>
        implements PreInscriptionDetailsUiHandlers {
    public interface MyView extends ValidatedPopupView, HasUiHandlers<PreInscriptionDetailsUiHandlers> {
        void editParent(InfoParentProxy currentInfoParent);

        void editCandidat(CandidatProxy currentCandidat);
    }

    private final BackRequestFactory requestFactory;
    private DossierServiceRequest currentContext;
    private InfoParentProxy currentInfoParent;
    private CandidatProxy currentCandidat;
    private NiveauEtudeProxy currentNiveau;

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
        // currentNiveau = currentContext.edit(dossier.getNiveauEtude());

        getView().editCandidat(currentCandidat);
        getView().editParent(currentInfoParent);
        // TODO: load niveau scolaire et scolarite anterieure
    }
}
