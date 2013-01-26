package com.gsr.myschool.back.client.web.application.user.popup;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.gsr.myschool.back.client.request.UserServiceRequest;
import com.gsr.myschool.common.client.mvp.ValidatedPopupView;
import com.gsr.myschool.common.client.proxy.UserProxy;
import com.gsr.myschool.common.client.widget.messages.MessagePresenter;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;

public class UserAccountEditPresenter extends PresenterWidget<UserAccountEditPresenter.MyView>  {
    public interface MyView extends ValidatedPopupView, HasUiHandlers<UserAccountEditUiHandlers> {
        void edit(UserProxy userProxy, UserServiceRequest userService);

        void refreshUserList();
    }

    @Inject
    public UserAccountEditPresenter(final EventBus eventBus, final MyView view) {
        super(eventBus, view);
    }

    public void editAccount(UserProxy userProxy, final MessagePresenter messagePresenter,
            UserServiceRequest userService) {
        userService.saveUserAccount(userProxy).to(new Receiver<Boolean>() {
            @Override
            public void onSuccess(Boolean response) {
                messagePresenter.alertCrudOperationResponse(response);
                getView().refreshUserList();
            }
        });

        getView().edit(userProxy, userService);
    }
}
