package com.gsr.myschool.back.client.web.application.session.popup;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gsr.myschool.back.client.web.application.session.popup.EditNiveauEtudeTimePresenter.MyView;
import com.gwtplatform.mvp.client.PresenterWidget;

public class EditNiveauEtudeTimePresenter extends PresenterWidget<MyView> implements EditNiveauEtudeTimeUiHandlers {
    public interface MyView extends PopupView, HasUiHandlers<EditNiveauEtudeTimeUiHandlers> {
    }

    private final BackRequestFactory requestFactory;

    @Inject
    public EditNiveauEtudeTimePresenter(final EventBus eventBus, final MyView view,
                                        final BackRequestFactory requestFactory) {
        super(eventBus, view);

        this.requestFactory = requestFactory;

        getView().setUiHandlers(this);
    }
}
