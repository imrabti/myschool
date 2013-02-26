package com.gsr.myschool.front.client.web.welcome.widget;

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.mvp.ValidatedView;
import com.gsr.myschool.common.client.proxy.ResetPasswordDTOProxy;
import com.gsr.myschool.common.client.request.ValidatedReceiverImpl;
import com.gsr.myschool.common.client.widget.messages.Message;
import com.gsr.myschool.common.client.widget.messages.event.MessageEvent;
import com.gsr.myschool.front.client.request.FrontRequestFactory;
import com.gsr.myschool.front.client.request.RegistrationRequest;
import com.gsr.myschool.front.client.resource.message.MessageBundle;
import com.gsr.myschool.front.client.web.welcome.event.PasswordResetEvent;
import com.gsr.myschool.front.client.web.welcome.widget.ResetPasswordPresenter.MyView;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class ResetPasswordPresenter extends PresenterWidget<MyView> implements ResetPasswordUiHandlers {
    public interface MyView extends ValidatedView, HasUiHandlers<ResetPasswordUiHandlers> {
        void edit(ResetPasswordDTOProxy object);
    }

    private final FrontRequestFactory requestFactory;
    private final MessageBundle messageBundle;

    private RegistrationRequest currentContext;
    private ResetPasswordDTOProxy currentResetPassword;
    private Boolean resetPasswordViolation;
    private String token;
    private String userEmail;

    @Inject
    public ResetPasswordPresenter(final EventBus eventBus, final MyView view,
                                  final FrontRequestFactory requestFactory,
                                  final MessageBundle messageBundle) {
        super(eventBus, view);

        this.messageBundle = messageBundle;
        this.requestFactory = requestFactory;

        getView().setUiHandlers(this);
    }

    public void initResetPassword(String token, String userEmail) {
        this.token = token;
        this.userEmail = userEmail;
    }

    @Override
    public void saveNewPassword(ResetPasswordDTOProxy resetPassword) {
        if (!resetPasswordViolation) {
            currentContext.resetPassword(resetPassword, token, userEmail).fire(new ValidatedReceiverImpl<Boolean>() {
                @Override
                public void onValidationError(Set<ConstraintViolation<?>> violations) {
                    resetPasswordViolation = true;
                    getView().clearErrors();
                    getView().showErrors(violations);
                }

                @Override
                public void onSuccess(Boolean response) {
                    String messageString = response ? messageBundle.resetPasswordSuccess()
                            : messageBundle.resetPasswordFailure();
                    AlertType alertType = response ? AlertType.SUCCESS : AlertType.ERROR;
                    Message message = new Message.Builder(messageString).style(alertType).build();
                    MessageEvent.fire(this, message);
                    getView().clearErrors();

                    resetPasswordViolation = false;
                    currentContext = requestFactory.registrationService();
                    currentResetPassword = currentContext.edit(currentResetPassword);
                    getView().edit(currentResetPassword);

                    if (response) {
                        PasswordResetEvent.fire(this);
                    }
                }
            });
        } else {
            currentContext.fire();
        }
    }

    @Override
    protected void onReveal() {
        currentContext = requestFactory.registrationService();
        currentResetPassword = currentContext.create(ResetPasswordDTOProxy.class);
        resetPasswordViolation = false;
        getView().edit(currentResetPassword);
    }
}
