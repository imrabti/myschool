package com.gsr.myschool.back.client.web.application.user.popup;

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.back.client.request.UserRequest;
import com.gsr.myschool.back.client.web.application.user.event.UserAccountChangedEvent;
import com.gsr.myschool.common.client.mvp.ValidatedPopupView;
import com.gsr.myschool.common.client.proxy.UserProxy;
import com.gsr.myschool.common.client.request.ValidatedReceiverImpl;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;
import com.gsr.myschool.common.client.widget.messages.CloseDelay;
import com.gsr.myschool.common.client.widget.messages.Message;
import com.gsr.myschool.common.client.widget.messages.event.MessageEvent;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class UserAccountEditPresenter extends PresenterWidget<UserAccountEditPresenter.MyView>
        implements UserAccountEditUiHandlers {
    public interface MyView extends ValidatedPopupView, HasUiHandlers<UserAccountEditUiHandlers> {
        void edit(UserProxy userProxy);
    }

    private final SharedMessageBundle messageBundle;
    private final BackRequestFactory requestFactory;

    private UserRequest currentContext;
    private UserProxy currentUser;
    private Boolean userViolation;

    @Inject
    public UserAccountEditPresenter(final EventBus eventBus, final MyView view,
                                    final SharedMessageBundle messageBundle,
                                    final BackRequestFactory requestFactory) {
        super(eventBus, view);

        this.messageBundle = messageBundle;
        this.requestFactory = requestFactory;

        getView().setUiHandlers(this);
    }

    @Override
    public void saveAccount(UserProxy userProxy) {
        if (!userViolation) {
            currentUser.setPasswordConfirmation(currentUser.getPassword());
            currentContext.saveUserAccount(currentUser).fire(new ValidatedReceiverImpl<Boolean>() {
                @Override
                public void onSuccess(Boolean response) {
                    userViolation = true;

                    String messageString = response ? messageBundle.operationSuccess() : messageBundle.operationFailure();
                    AlertType alertType = response ? AlertType.SUCCESS : AlertType.ERROR;
                    Message message = new Message.Builder(messageString)
                            .style(alertType).closeDelay(CloseDelay.DEFAULT).build();
                    MessageEvent.fire(this, message);
                    UserAccountChangedEvent.fire(this);

                    getView().clearErrors();
                    getView().hide();
                }

                @Override
                public void onValidationError(Set<ConstraintViolation<?>> violations) {
                    userViolation = false;

                    getView().clearErrors();
                    getView().showErrors(violations);
                }
            });
        } else {
            currentContext.fire();
        }
    }

    public void editDatas(UserProxy userProxy) {
        currentContext = requestFactory.userService();
        currentUser = currentContext.edit(userProxy);
        userViolation = false;

        getView().edit(currentUser);
    }
}
