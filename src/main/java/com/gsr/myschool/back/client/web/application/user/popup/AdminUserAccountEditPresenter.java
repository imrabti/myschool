package com.gsr.myschool.back.client.web.application.user.popup;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.back.client.request.UserServiceRequest;
import com.gsr.myschool.common.client.mvp.ValidatedPopupView;
import com.gsr.myschool.common.client.proxy.AdminUserProxy;
import com.gsr.myschool.common.client.widget.messages.MessagePresenter;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;

public class AdminUserAccountEditPresenter extends PresenterWidget<AdminUserAccountEditPresenter.MyView>  {
    public interface MyView extends ValidatedPopupView, HasUiHandlers<AdminUserAccountEditUiHandlers> {
        void edit(AdminUserProxy userProxy, UserServiceRequest userService);

        void refreshUserList();
    }

    @Inject
    public AdminUserAccountEditPresenter(final EventBus eventBus, final MyView view) {
        super(eventBus, view);
    }

    public void addAccount(BackRequestFactory requestFactory, MessagePresenter messagePresenter) {
        UserServiceRequest userService = requestFactory.userService();
        AdminUserProxy userProxy = userService.create(AdminUserProxy.class);
        editAccount(userProxy, messagePresenter, userService);
    }

    public void editAccount(AdminUserProxy userProxy, final MessagePresenter messagePresenter,
            UserServiceRequest userService) {
        userService.saveAdminAccount(userProxy).to(new Receiver<Boolean>() {
            @Override
            public void onSuccess(Boolean response) {
                messagePresenter.alertCrudOperationResponse(response);
                getView().refreshUserList();
            }
        });

        getView().edit(userProxy, userService);
    }
}
