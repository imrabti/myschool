package com.gsr.myschool.back.client.web.application.validation.popup;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.client.proxy.PiecejustifDTOProxy;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gsr.myschool.back.client.web.application.validation.popup.PiecesJustificatifPresenter.MyView;

import java.util.List;

public class PiecesJustificatifPresenter extends PresenterWidget<MyView> implements PiecesJustificatifUiHandlers {
    public interface MyView extends PopupView, HasUiHandlers<PiecesJustificatifUiHandlers> {
        void editPieces(List<PiecejustifDTOProxy> data);
    }

    private final BackRequestFactory requestFactory;

    private List<PiecejustifDTOProxy> pieces;
    private DossierProxy currentDossier;

    @Inject
    public PiecesJustificatifPresenter(final EventBus eventBus, final MyView view,
                                       final BackRequestFactory requestFactory) {
        super(eventBus, view);

        this.requestFactory = requestFactory;

        getView().setUiHandlers(this);
    }

    public void setCurrentDossier(DossierProxy currentDossier) {
        this.currentDossier = currentDossier;
    }

    @Override
    public void checkPieces(List<PiecejustifDTOProxy> pieces) {
        // TODO call the backend service in here...
    }

    @Override
    protected void onReveal() {
        requestFactory.dossierService().getPiecejustifFromProcess(currentDossier)
                .fire(new ReceiverImpl<List<PiecejustifDTOProxy>>() {
            @Override
            public void onSuccess(List<PiecejustifDTOProxy> result) {
                pieces = result;
                getView().editPieces(pieces);
            }
        });
    }
}
