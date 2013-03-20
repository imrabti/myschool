package com.gsr.myschool.back.client.web.application.validation.popup;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gsr.myschool.back.client.web.application.validation.popup.PiecesJustificatifPresenter.MyView;

public class PiecesJustificatifPresenter extends PresenterWidget<MyView> {
    public interface MyView extends PopupView {
    }

    @Inject
    public PiecesJustificatifPresenter(final EventBus eventBus, final MyView view) {
        super(eventBus, view);
    }
}
