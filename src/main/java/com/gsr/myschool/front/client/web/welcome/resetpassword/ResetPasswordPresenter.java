package com.gsr.myschool.front.client.web.welcome.resetpassword;

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.mvp.ValidatedView;
import com.gsr.myschool.common.client.proxy.ResetPasswordDTOProxy;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.request.ValidatedReceiverImpl;
import com.gsr.myschool.common.client.widget.messages.CloseDelay;
import com.gsr.myschool.common.client.widget.messages.Message;
import com.gsr.myschool.common.client.widget.messages.event.MessageEvent;
import com.gsr.myschool.front.client.place.NameTokens;
import com.gsr.myschool.front.client.request.FrontRequestFactory;
import com.gsr.myschool.front.client.request.RegistrationRequest;
import com.gsr.myschool.front.client.resource.message.MessageBundle;
import com.gsr.myschool.front.client.web.RootPresenter;
import com.gsr.myschool.front.client.web.welcome.resetpassword.ResetPasswordPresenter.MyView;
import com.gsr.myschool.front.client.web.welcome.resetpassword.ResetPasswordPresenter.MyProxy;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class ResetPasswordPresenter extends Presenter<MyView, MyProxy> implements ResetPasswordUiHandlers {
    public interface MyView extends ValidatedView, HasUiHandlers<ResetPasswordUiHandlers> {
        void edit(ResetPasswordDTOProxy object);
    }

    @ProxyStandard
    @NameToken(NameTokens.resetpassword)
    public interface MyProxy extends ProxyPlace<ResetPasswordPresenter> {
    }

    private final FrontRequestFactory requestFactory;
    private final MessageBundle messageBundle;
    private final PlaceManager placeManager;

    private RegistrationRequest currentContext;
    private ResetPasswordDTOProxy currentResetPassword;
    private String token;
    private String userEmail;

    @Inject
    public ResetPasswordPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
                                  final FrontRequestFactory requestFactory, final MessageBundle messageBundle,
                                  final PlaceManager placeManager) {
        super(eventBus, view, proxy, RootPresenter.TYPE_SetMainContent);

        this.messageBundle = messageBundle;
        this.requestFactory = requestFactory;
        this.placeManager = placeManager;

        getView().setUiHandlers(this);
    }

    @Override
    public void prepareFromRequest(PlaceRequest placeRequest) {
        token = placeRequest.getParameter("token", null);
        if (!Strings.isNullOrEmpty(token)) {
            requestFactory.registrationService().verifyForgotPassword(token).fire(new ReceiverImpl<String>() {
                @Override
                public void onSuccess(String response) {
                    if (Strings.isNullOrEmpty(response)) {
                        bounceToLogin();
                    } else {
                        userEmail = response;
                    }
                }
            });
        } else {
            bounceToLogin();
        }
    }

    @Override
    public void saveNewPassword(ResetPasswordDTOProxy resetPassword) {
        currentContext.resetPassword(resetPassword, token, userEmail).fire(new ValidatedReceiverImpl<Boolean>() {
            @Override
            public void onValidationError(Set<ConstraintViolation<?>> violations) {
                getView().clearErrors();
                getView().showErrors(violations);
            }

            @Override
            public void onSuccess(Boolean response) {
                String messageString = response ? messageBundle.resetPasswordSuccess()
                        : messageBundle.resetPasswordFailure();
                AlertType alertType = response ? AlertType.SUCCESS : AlertType.ERROR;
                Message message = new Message.Builder(messageString)
                        .style(alertType)
                        .closeDelay(CloseDelay.LONG)
                        .build();
                MessageEvent.fire(this, message);
                getView().clearErrors();

                if (response) {
                    placeManager.revealPlace(new PlaceRequest(NameTokens.getLogin()));
                } else {
                    currentContext = requestFactory.registrationService();
                    currentResetPassword = currentContext.edit(currentResetPassword);
                    getView().edit(currentResetPassword);
                }
            }
        });
    }

    @Override
    protected void onReveal() {
        currentContext = requestFactory.registrationService();
        currentResetPassword = currentContext.create(ResetPasswordDTOProxy.class);
        getView().edit(currentResetPassword);
    }

    private void bounceToLogin() {
        Message message = new Message.Builder(messageBundle.forgotPasswordWrongToken())
                .style(AlertType.ERROR)
                .closeDelay(CloseDelay.LONG)
                .build();
        MessageEvent.fire(this, message);
        placeManager.revealPlace(new PlaceRequest(NameTokens.getLogin()));
    }
}
