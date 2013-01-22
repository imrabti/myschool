package com.gsr.myschool.front.client.web.application.popup;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.mvp.ValidatedPopupView;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;

public class AccountSettingsPresenter extends PresenterWidget<AccountSettingsPresenter.MyView>
        implements AccountSettingsUiHandlers {
    public interface MyView extends ValidatedPopupView, HasUiHandlers<AccountSettingsUiHandlers> {
    }

    @Inject
    public AccountSettingsPresenter(final EventBus eventBus, final MyView view) {
        super(eventBus, view);

        getView().setUiHandlers(this);
    }
}
