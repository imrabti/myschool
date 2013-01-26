package com.gsr.myschool.back.client.web.application.user.popup;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.mvp.ValidatedPopupView;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;

import java.util.List;

public class UserInscriptionListPresenter extends PresenterWidget<UserInscriptionListPresenter.MyView>  {
    public interface MyView extends ValidatedPopupView, HasUiHandlers<UserInscriptionListUiHandlers> {
        void setData(List<DossierProxy> data);
    }

    @Inject
    public UserInscriptionListPresenter(final EventBus eventBus, final MyView view) {
        super(eventBus, view);
    }
}
