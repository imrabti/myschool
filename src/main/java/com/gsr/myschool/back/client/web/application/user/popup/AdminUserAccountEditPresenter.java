package com.gsr.myschool.back.client.web.application.user.popup;

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.back.client.request.UserServiceRequest;
import com.gsr.myschool.common.client.mvp.ValidatedPopupView;
import com.gsr.myschool.common.client.proxy.AdminUserProxy;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.request.ValidatedReceiverImpl;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;
import com.gsr.myschool.common.client.widget.messages.CloseDelay;
import com.gsr.myschool.common.client.widget.messages.Message;
import com.gsr.myschool.common.client.widget.messages.MessagePresenter;
import com.gsr.myschool.common.client.widget.messages.event.MessageEvent;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class AdminUserAccountEditPresenter extends PresenterWidget<AdminUserAccountEditPresenter.MyView>  {
    public interface MyView extends ValidatedPopupView, HasUiHandlers<AdminUserAccountEditUiHandlers> {
        void edit(AdminUserProxy userProxy, UserServiceRequest userService);

        void updateAccountStatus(AdminUserProxy edit, UserServiceRequest userService);

        void refreshUserList();
    }

    private final SharedMessageBundle messageBundle;

    @Inject
    public AdminUserAccountEditPresenter(final EventBus eventBus, final MyView view,
                                         final SharedMessageBundle messageBundle) {
        super(eventBus, view);

        this.messageBundle = messageBundle;
    }

    public void addAccount(BackRequestFactory requestFactory) {
        UserServiceRequest userService = requestFactory.userService();
        AdminUserProxy userProxy = userService.create(AdminUserProxy.class);
        editAccount(userProxy, userService);
    }

    public void updateAccountStatus(AdminUserProxy userProxy, UserServiceRequest userService) {
        userService.saveAdminAccount(userProxy).to(new ReceiverImpl<Boolean>() {
            @Override
            public void onSuccess(Boolean response) {
                String messageString = response ? messageBundle.operationSuccess() : messageBundle.operationFailure();
                AlertType alertType = response ? AlertType.SUCCESS : AlertType.ERROR;
                Message message = new Message.Builder(messageString)
                        .style(alertType)
                        .closeDelay(CloseDelay.DEFAULT)
                        .build();
                MessageEvent.fire(this, message);
                getView().refreshUserList();
            }
        });

        getView().updateAccountStatus(userProxy, userService);
    }

    public void editAccount(AdminUserProxy userProxy, UserServiceRequest userService) {
        userService.saveAdminAccount(userProxy).to(new ValidatedReceiverImpl<Boolean>() {
            @Override
            public void onSuccess(Boolean response) {
                String messageString = response ? messageBundle.operationSuccess() : messageBundle.operationFailure();
                AlertType alertType = response ? AlertType.SUCCESS : AlertType.ERROR;
                Message message = new Message.Builder(messageString)
                        .style(alertType)
                        .closeDelay(CloseDelay.DEFAULT)
                        .build();
                MessageEvent.fire(this, message);

                getView().clearErrors();
                getView().hide();
                getView().refreshUserList();
            }

            @Override
            public void onValidationError(Set<ConstraintViolation<?>> violations) {
                getView().clearErrors();
                getView().showErrors(violations);
            }
        });

        getView().clearErrors();
        getView().edit(userProxy, userService);
    }
}
