package com.gsr.myschool.back.client.web.application.user.popup;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.mvp.ValidatedPopupView;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;

public class AdminUserAccountEditPresenter extends PresenterWidget<AdminUserAccountEditPresenter.MyView>
        implements AdminUserAccountEditUiHandlers {
    public interface MyView extends ValidatedPopupView, HasUiHandlers<AdminUserAccountEditUiHandlers> {
	}

    @Inject
    public AdminUserAccountEditPresenter(final EventBus eventBus, final MyView view) {
        super(eventBus, view);

        getView().setUiHandlers(this);
    }

    public void editDrivers(DossierProxy dossier) {
    }
}
