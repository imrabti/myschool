package com.gsr.myschool.front.client.web.welcome.widget;

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.mvp.ValidatedView;
import com.gsr.myschool.common.client.proxy.ResetPasswordDTOProxy;
import com.gsr.myschool.common.client.widget.messages.CloseDelay;
import com.gsr.myschool.common.client.widget.messages.Message;
import com.gsr.myschool.common.client.widget.messages.event.MessageEvent;
import com.gsr.myschool.front.client.request.FrontRequestFactory;
import com.gsr.myschool.front.client.request.RegistrationRequest;
import com.gsr.myschool.front.client.resource.message.MessageBundle;
import com.gsr.myschool.front.client.web.welcome.widget.ResetPasswordPresenter.MyView;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

public class ResetPasswordPresenter extends PresenterWidget<MyView> implements ResetPasswordUiHandlers {
    public interface MyView extends ValidatedView, HasUiHandlers<ResetPasswordUiHandlers> {
        void edit(ResetPasswordDTOProxy object);
    }

    private final FrontRequestFactory requestFactory;
    private final MessageBundle messageBundle;
    private final PlaceManager placeManager;

    private RegistrationRequest currentContext;
    private ResetPasswordDTOProxy currentResetPassword;
    private Boolean resetPasswordViolation;

    @Inject
    public ResetPasswordPresenter(final EventBus eventBus, final MyView view,
                                  final FrontRequestFactory requestFactory,
                                  final MessageBundle messageBundle,
                                  final PlaceManager placeManager) {
        super(eventBus, view);

        this.messageBundle = messageBundle;
        this.requestFactory = requestFactory;
        this.placeManager = placeManager;

        getView().setUiHandlers(this);
    }

    @Override
    public void saveNewPassword(ResetPasswordDTOProxy resetPassword) {
        /*if (!resetPasswordViolation) {
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
                    Message message = new Message.Builder(messageString)
                            .style(alertType).closeDelay(CloseDelay.LONG).build();
                    MessageEvent.fire(this, message);
                    getView().clearErrors();

                    resetPasswordViolation = false;

                    if (response) {
                        placeManager.revealPlace(new PlaceRequest(NameTokens.getLogin()));
                    } else {
                        currentContext = requestFactory.registrationService();
                        currentResetPassword = currentContext.edit(currentResetPassword);
                        getView().edit(currentResetPassword);
                    }
                }
            });
        } else {
            currentContext.fire();
        }*/
    }

    @Override
    protected void onReveal() {
        currentContext = requestFactory.registrationService();
        currentResetPassword = currentContext.create(ResetPasswordDTOProxy.class);
        resetPasswordViolation = false;
        getView().edit(currentResetPassword);
    }

    private void bounceToLogin() {
        Message message = new Message.Builder(messageBundle.forgotPasswordWrongToken())
                .style(AlertType.ERROR).closeDelay(CloseDelay.LONG).build();
        MessageEvent.fire(this, message);
        //placeManager.revealPlace(new PlaceRequest(NameTokens.getLogin()));
    }
}
