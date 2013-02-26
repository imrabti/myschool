package com.gsr.myschool.front.client.web.welcome.popup;

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.mvp.ValidatedPopupView;
import com.gsr.myschool.common.client.proxy.ForgotPasswordDTOProxy;
import com.gsr.myschool.common.client.request.ValidatedReceiverImpl;
import com.gsr.myschool.common.client.util.URLUtils;
import com.gsr.myschool.common.client.widget.messages.CloseDelay;
import com.gsr.myschool.common.client.widget.messages.Message;
import com.gsr.myschool.common.client.widget.messages.event.MessageEvent;
import com.gsr.myschool.front.client.place.NameTokens;
import com.gsr.myschool.front.client.request.FrontRequestFactory;
import com.gsr.myschool.front.client.request.RegistrationRequest;
import com.gsr.myschool.front.client.resource.message.MessageBundle;
import com.gsr.myschool.front.client.web.welcome.popup.ForgotPasswordPresenter.MyView;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class ForgotPasswordPresenter extends PresenterWidget<MyView> implements ForgotPasswordUiHandlers {
    public interface MyView extends ValidatedPopupView, HasUiHandlers<ForgotPasswordUiHandlers> {
        void edit(ForgotPasswordDTOProxy forgotPassword);
    }

    private final FrontRequestFactory requestFactory;
    private final MessageBundle messageBundle;

    private RegistrationRequest currentContext;
    private ForgotPasswordDTOProxy currentForgotPassword;
    private Boolean forgotPasswordViolation;

    @Inject
    public ForgotPasswordPresenter(final EventBus eventBus, final MyView view,
                                   final FrontRequestFactory requestFactory,
                                   final MessageBundle messageBundle) {
        super(eventBus, view);

        this.requestFactory = requestFactory;
        this.messageBundle = messageBundle;

        getView().setUiHandlers(this);
    }

    @Override
    public void forgotPassword(ForgotPasswordDTOProxy forgotPassword) {
        if (!forgotPasswordViolation) {
            String passwordLink = URLUtils.generateURL(NameTokens.getWelcome());
            currentContext.startForgotPasswordProcess(forgotPassword, passwordLink).fire(
                    new ValidatedReceiverImpl<Boolean>() {
                @Override
                public void onValidationError(Set<ConstraintViolation<?>> violations) {
                    forgotPasswordViolation = true;
                    getView().clearErrors();
                    getView().showErrors(violations);
                }

                @Override
                public void onSuccess(Boolean response) {
                    String messageString = response ? messageBundle.forgotPasswordSuccess()
                            : messageBundle.forgotPasswordFailure();
                    AlertType alertType = response ? AlertType.SUCCESS : AlertType.ERROR;
                    Message message = new Message.Builder(messageString).style(alertType).build();
                    MessageEvent.fire(this, message);

                    forgotPasswordViolation = false;

                    getView().clearErrors();
                    getView().hide();
                }
            });
        } else {
            currentContext.fire();
        }
    }

    @Override
    protected void onReveal() {
        currentContext = requestFactory.registrationService();
        currentForgotPassword = currentContext.create(ForgotPasswordDTOProxy.class);
        forgotPasswordViolation = false;
        getView().edit(currentForgotPassword);
    }
}
