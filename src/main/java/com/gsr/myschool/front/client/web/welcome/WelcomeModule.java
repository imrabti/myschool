/**
 * Copyright 2012 Nuvola Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.gsr.myschool.front.client.web.welcome;

import com.google.inject.TypeLiteral;
import com.gsr.myschool.common.client.mvp.uihandler.SetterUiHandlersStrategy;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gsr.myschool.front.client.web.welcome.popup.ForgotPasswordPresenter;
import com.gsr.myschool.front.client.web.welcome.popup.ForgotPasswordUiHandlers;
import com.gsr.myschool.front.client.web.welcome.popup.ForgotPasswordView;
import com.gsr.myschool.front.client.web.welcome.resetpassword.ResetPasswordPresenter;
import com.gsr.myschool.front.client.web.welcome.resetpassword.ResetPasswordUiHandlers;
import com.gsr.myschool.front.client.web.welcome.resetpassword.ResetPasswordView;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gsr.myschool.front.client.web.welcome.login.LoginPresenter;
import com.gsr.myschool.front.client.web.welcome.login.LoginUiHandlers;
import com.gsr.myschool.front.client.web.welcome.login.LoginView;
import com.gsr.myschool.front.client.web.welcome.register.RegisterPresenter;
import com.gsr.myschool.front.client.web.welcome.register.RegisterUiHandlers;
import com.gsr.myschool.front.client.web.welcome.register.RegisterView;

public class WelcomeModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bind(new TypeLiteral<UiHandlersStrategy<LoginUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<LoginUiHandlers>>() {});
        bind(new TypeLiteral<UiHandlersStrategy<RegisterUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<RegisterUiHandlers>>() {});
        bind(new TypeLiteral<UiHandlersStrategy<ForgotPasswordUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<ForgotPasswordUiHandlers>>() {});
        bind(new TypeLiteral<UiHandlersStrategy<ResetPasswordUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<ResetPasswordUiHandlers>>() {});

        bindPresenter(LoginPresenter.class, LoginPresenter.MyView.class, LoginView.class,
                LoginPresenter.MyProxy.class);
        bindPresenter(RegisterPresenter.class, RegisterPresenter.MyView.class, RegisterView.class,
                RegisterPresenter.MyProxy.class);
        bindPresenter(ResetPasswordPresenter.class, ResetPasswordPresenter.MyView.class, ResetPasswordView.class,
                ResetPasswordPresenter.MyProxy.class);

        bindSingletonPresenterWidget(ForgotPasswordPresenter.class, ForgotPasswordPresenter.MyView.class,
                ForgotPasswordView.class);
    }
}
