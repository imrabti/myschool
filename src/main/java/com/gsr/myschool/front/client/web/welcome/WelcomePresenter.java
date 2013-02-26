package com.gsr.myschool.front.client.web.welcome;

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.widget.messages.Message;
import com.gsr.myschool.common.client.widget.messages.event.MessageEvent;
import com.gsr.myschool.front.client.place.NameTokens;
import com.gsr.myschool.front.client.request.FrontRequestFactory;
import com.gsr.myschool.front.client.resource.message.MessageBundle;
import com.gsr.myschool.front.client.web.RootPresenter;
import com.gsr.myschool.front.client.web.welcome.widget.LoginPresenter;
import com.gsr.myschool.front.client.web.welcome.widget.RegisterPresenter;
import com.gsr.myschool.front.client.web.welcome.widget.ResetPasswordPresenter;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

public class WelcomePresenter extends Presenter<WelcomePresenter.MyView, WelcomePresenter.MyProxy> {
    public interface MyView extends View {
    }

    @ProxyStandard
    @NameToken(NameTokens.welcome)
    public interface MyProxy extends ProxyPlace<WelcomePresenter> {
    }

    public static final Object TYPE_SetLoginContent = new Object();
    public static final Object TYPE_SetRegisterContent = new Object();

    private static final String ACTIVATE_TOKEN = "activate";
    private static final String FORGOT_TOKEN = "forgot";

    private final FrontRequestFactory requestFactory;
    private final MessageBundle messageBundle;
    private final LoginPresenter loginPresenter;
    private final RegisterPresenter registerPresenter;
    private final ResetPasswordPresenter resetPasswordPresenter;

    @Inject
    public WelcomePresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
                            final FrontRequestFactory requestFactory,
                            final MessageBundle messageBundle,
                            final LoginPresenter loginPresenter,
                            final RegisterPresenter registerPresenter,
                            final ResetPasswordPresenter resetPasswordPresenter) {
        super(eventBus, view, proxy, RootPresenter.TYPE_SetMainContent);

        this.requestFactory = requestFactory;
        this.messageBundle = messageBundle;
        this.loginPresenter = loginPresenter;
        this.registerPresenter = registerPresenter;
        this.resetPasswordPresenter = resetPasswordPresenter;
    }

    @Override
    public void prepareFromRequest(PlaceRequest place) {
        for (String parameter : place.getParameterNames()) {
            if (parameter.equals(ACTIVATE_TOKEN)) {
                processAccountActivation(place.getParameter(parameter, null));
                return;
            } else if (parameter.equals(FORGOT_TOKEN)) {
                processForgotPassword(place.getParameter(parameter, null));
                return;
            }
        }

        setInSlot(TYPE_SetRegisterContent, registerPresenter);
    }

    @Override
    protected void onReveal() {
        setInSlot(TYPE_SetLoginContent, loginPresenter);
    }

    private void processAccountActivation(String token) {
        requestFactory.registrationService().activateAccount(token).fire(new ReceiverImpl<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                String messageString = aBoolean ? messageBundle.activateAccountSuccess()
                        : messageBundle.activateAccountFailure();
                AlertType alertType = aBoolean ? AlertType.SUCCESS : AlertType.ERROR;
                Message message = new Message.Builder(messageString).style(alertType).build();
                MessageEvent.fire(this, message);
            }
        });
    }

    private void processForgotPassword(String token) {
    }
}
