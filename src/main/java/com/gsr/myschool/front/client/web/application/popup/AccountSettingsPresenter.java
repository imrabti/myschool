package com.gsr.myschool.front.client.web.application.popup;

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.mvp.ValidatedPopupView;
import com.gsr.myschool.common.client.proxy.PasswordDTOProxy;
import com.gsr.myschool.common.client.proxy.UserProxy;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.request.ValidatedReceiverImpl;
import com.gsr.myschool.common.client.security.SecurityUtils;
import com.gsr.myschool.common.client.widget.messages.CloseDelay;
import com.gsr.myschool.common.client.widget.messages.Message;
import com.gsr.myschool.common.client.widget.messages.event.MessageEvent;
import com.gsr.myschool.front.client.request.FrontRequestFactory;
import com.gsr.myschool.front.client.request.UserAccountRequest;
import com.gsr.myschool.front.client.resource.message.MessageBundle;
import com.gsr.myschool.front.client.security.CurrentUserProvider;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class AccountSettingsPresenter extends PresenterWidget<AccountSettingsPresenter.MyView>
        implements AccountSettingsUiHandlers {
    public interface MyView extends ValidatedPopupView, HasUiHandlers<AccountSettingsUiHandlers> {
        void editUser(UserProxy user);

        void editPassword(PasswordDTOProxy password);
    }

    private final FrontRequestFactory requestFactory;
    private final MessageBundle messageBundle;
    private final SecurityUtils securityUtils;
    private final CurrentUserProvider currentUserProvider;

    private UserAccountRequest currentUserContext;
    private UserAccountRequest currentPasswordContext;
    private UserProxy currentUser;
    private PasswordDTOProxy currentPassword;

    private Boolean userViolation;
    private Boolean passwordViolation;

    @Inject
    public AccountSettingsPresenter(final EventBus eventBus, final MyView view,
                                    final FrontRequestFactory requestFactory,
                                    final MessageBundle messageBundle,
                                    final SecurityUtils securityUtils,
                                    final CurrentUserProvider currentUserProvider) {
        super(eventBus, view);

        this.requestFactory = requestFactory;
        this.messageBundle = messageBundle;
        this.securityUtils = securityUtils;
        this.currentUserProvider = currentUserProvider;

        getView().setUiHandlers(this);
    }

    @Override
    public void saveUser(UserProxy user) {
        if (currentUserContext.isChanged()) {
            if (!userViolation) {
                currentUser.setPasswordConfirmation(currentUser.getPassword());
                currentUserContext.updateAccount(user).fire(new ValidatedReceiverImpl<UserProxy>() {
                    @Override
                    public void onValidationError(Set<ConstraintViolation<?>> violations) {
                        getView().clearErrors();
                        getView().showErrors(violations);
                        userViolation = true;
                    }

                    @Override
                    public void onSuccess(UserProxy result) {
                        Message message = new Message.Builder(messageBundle.accountUpdatedSuccess())
                                .style(AlertType.SUCCESS).closeDelay(CloseDelay.DEFAULT).build();
                        MessageEvent.fire(this, message);

                        securityUtils.updateUsername(result.getEmail());
                        currentUserProvider.reload(result);
                        userViolation = false;

                        getView().clearErrors();
                        getView().hide();
                    }
                });
            } else {
                currentUserContext.fire();
            }
        }
    }

    @Override
    public void savePassword(PasswordDTOProxy password) {
        if (currentPasswordContext.isChanged()) {
            if (!passwordViolation) {
                currentPasswordContext.updatePassword(password).fire(new ValidatedReceiverImpl<Void>() {
                    @Override
                    public void onValidationError(Set<ConstraintViolation<?>> violations) {
                        getView().clearErrors();
                        getView().showErrors(violations);
                        passwordViolation = true;
                    }

                    @Override
                    public void onSuccess(Void response) {
                        Message message = new Message.Builder(messageBundle.passwordUpdatedSuccess())
                                .style(AlertType.SUCCESS).closeDelay(CloseDelay.DEFAULT).build();
                        MessageEvent.fire(this, message);

                        securityUtils.updatePassword(currentPassword.getPassword());
                        passwordViolation = false;

                        getView().clearErrors();
                        getView().hide();
                    }
                });
            } else {
                currentPasswordContext.fire();
            }
        }
    }

    protected void onReveal() {
        requestFactory.authenticationService().currentUser().fire(new ReceiverImpl<UserProxy>() {
            @Override
            public void onSuccess(UserProxy result) {
                currentUserContext = requestFactory.userAccountService();
                currentUser = currentUserContext.edit(result);
                userViolation = false;
                getView().editUser(currentUser);
            }
        });

        currentPasswordContext = requestFactory.userAccountService();
        currentPassword = currentPasswordContext.create(PasswordDTOProxy.class);
        passwordViolation = false;
        getView().editPassword(currentPassword);
    }
}
