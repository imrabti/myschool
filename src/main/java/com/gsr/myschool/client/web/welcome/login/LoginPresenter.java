package com.gsr.myschool.client.web.welcome.login;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gsr.myschool.client.BootstrapperImpl;
import com.gsr.myschool.client.place.NameTokens;
import com.gsr.myschool.client.request.MyRequestFactory;
import com.gsr.myschool.client.request.ReceiverImpl;
import com.gsr.myschool.client.security.SecurityUtils;
import com.gsr.myschool.client.web.RootPresenter;
import com.gsr.myschool.shared.dto.UserCredentials;

public class LoginPresenter extends Presenter<LoginPresenter.MyView, LoginPresenter.MyProxy>
        implements LoginUiHandlers {
    public interface MyView extends View, HasUiHandlers<LoginUiHandlers> {
        void edit(UserCredentials credentials);

        void displayLoginError(Boolean visible);
    }

    @ProxyStandard
    @NameToken(NameTokens.login)
    public interface MyProxy extends ProxyPlace<LoginPresenter> {
    }

    private final MyRequestFactory requestFactory;
    private final SecurityUtils securityUtils;
    private final BootstrapperImpl bootstrapper;

    @Inject
    public LoginPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
                          final MyRequestFactory requestFactory, final SecurityUtils securityUtils,
                          final BootstrapperImpl bootstrapper) {
        super(eventBus, view, proxy, RootPresenter.TYPE_SetMainContent);

        this.requestFactory = requestFactory;
        this.securityUtils = securityUtils;
        this.bootstrapper = bootstrapper;

        getView().setUiHandlers(this);
    }

    @Override
    public void login(final UserCredentials credentials) {
        requestFactory.authenticationService().authenticate(credentials.getUsername(), credentials.getPassword())
                .fire(new ReceiverImpl<Boolean>() {
            @Override
            public void onSuccess(Boolean authenticated) {
                if (authenticated) {
                    securityUtils.setCredentials(credentials.getUsername(), credentials.getPassword());
                    bootstrapper.init();
                } else {
                    getView().displayLoginError(true);
                }
            }
        });
    }

    @Override
    protected void onReveal() {
        getView().edit(new UserCredentials());
    }
}
