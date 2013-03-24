package com.gsr.myschool.back.client.web.application.session.popup;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gsr.myschool.back.client.web.application.session.popup.EditSessionPresenter.MyView;

public class EditSessionPresenter extends PresenterWidget<MyView> implements EditSessionUiHandlers {
    public interface MyView extends PopupView, HasUiHandlers<EditSessionUiHandlers> {
    }

    private final BackRequestFactory requestFactory;

    @Inject
    public EditSessionPresenter(final EventBus eventBus, final MyView view,
                                final BackRequestFactory requestFactory) {
        super(eventBus, view);

        this.requestFactory = requestFactory;

        getView().setUiHandlers(this);
    }
}
