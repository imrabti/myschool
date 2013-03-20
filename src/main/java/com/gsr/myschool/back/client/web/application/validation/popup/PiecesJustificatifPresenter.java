package com.gsr.myschool.back.client.web.application.validation.popup;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.proxy.PieceJustifProxy;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gsr.myschool.back.client.web.application.validation.popup.PiecesJustificatifPresenter.MyView;

import java.util.List;

public class PiecesJustificatifPresenter extends PresenterWidget<MyView> implements PiecesJustificatifUiHandlers {
    public interface MyView extends PopupView, HasUiHandlers<PiecesJustificatifUiHandlers> {
        void editPieces(List<PieceJustifProxy> data);
    }

    @Inject
    public PiecesJustificatifPresenter(final EventBus eventBus, final MyView view) {
        super(eventBus, view);

        getView().setUiHandlers(this);
    }

    @Override
    public void checkPieces(List<PieceJustifProxy> pieces) {
        // TODO call the backend service in here...
    }
}
