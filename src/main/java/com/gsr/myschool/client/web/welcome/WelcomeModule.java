package com.gsr.myschool.client.web.welcome;

import com.arcbees.core.client.mvp.uihandlers.SetterUiHandlersStrategy;
import com.arcbees.core.client.mvp.uihandlers.UiHandlersStrategy;
import com.google.inject.TypeLiteral;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gsr.myschool.client.web.welcome.login.LoginPresenter;
import com.gsr.myschool.client.web.welcome.login.LoginUiHandlers;
import com.gsr.myschool.client.web.welcome.login.LoginView;
import com.gsr.myschool.client.web.welcome.register.RegisterPresenter;
import com.gsr.myschool.client.web.welcome.register.RegisterUiHandlers;
import com.gsr.myschool.client.web.welcome.register.RegisterView;

public class WelcomeModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bind(new TypeLiteral<UiHandlersStrategy<LoginUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<LoginUiHandlers>>() {});
        bind(new TypeLiteral<UiHandlersStrategy<RegisterUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<RegisterUiHandlers>>() {});

        bindPresenter(LoginPresenter.class, LoginPresenter.MyView.class, LoginView.class,
                LoginPresenter.MyProxy.class);
        bindPresenter(RegisterPresenter.class, RegisterPresenter.MyView.class, RegisterView.class,
                RegisterPresenter.MyProxy.class);
    }
}
