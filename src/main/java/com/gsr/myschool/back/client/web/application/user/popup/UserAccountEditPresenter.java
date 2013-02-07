package com.gsr.myschool.back.client.web.application.user.popup;

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.request.UserServiceRequest;
import com.gsr.myschool.common.client.mvp.ValidatedPopupView;
import com.gsr.myschool.common.client.proxy.UserProxy;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;
import com.gsr.myschool.common.client.widget.messages.CloseDelay;
import com.gsr.myschool.common.client.widget.messages.Message;
import com.gsr.myschool.common.client.widget.messages.event.MessageEvent;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;

public class UserAccountEditPresenter extends PresenterWidget<UserAccountEditPresenter.MyView>  {
    public interface MyView extends ValidatedPopupView, HasUiHandlers<UserAccountEditUiHandlers> {
        void edit(UserProxy userProxy);

        void refreshUserList();
    }

    private final SharedMessageBundle messageBundle;

    @Inject
    public UserAccountEditPresenter(final EventBus eventBus, final MyView view,
                                    final SharedMessageBundle messageBundle) {
        super(eventBus, view);

        this.messageBundle = messageBundle;
    }

    public void editAccount(UserProxy userProxy, UserServiceRequest userService) {
        userService.saveUserAccount(userProxy).to(new ReceiverImpl<Boolean>() {
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

        getView().edit(userProxy);
    }
}
