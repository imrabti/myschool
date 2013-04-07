package com.gsr.myschool.back.client.web.application.settings.popup;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;

public class AddFilierePresenter extends PresenterWidget<AddFilierePresenter.MyView> implements AddFiliereUiHandlers {
    public interface MyView extends PopupView, HasUiHandlers<AddFiliereUiHandlers> {
    }

    @Inject
    public AddFilierePresenter(final EventBus eventBus, final MyView view) {
        super(eventBus, view);

        getView().setUiHandlers(this);
    }
}
