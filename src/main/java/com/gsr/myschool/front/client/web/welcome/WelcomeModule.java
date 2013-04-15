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

import com.gsr.myschool.front.client.web.welcome.popup.*;
import com.gsr.myschool.front.client.web.welcome.widget.ResetPasswordPresenter;
import com.gsr.myschool.front.client.web.welcome.widget.ResetPasswordUiHandlers;
import com.gsr.myschool.front.client.web.welcome.widget.ResetPasswordView;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gsr.myschool.front.client.web.welcome.widget.LoginPresenter;
import com.gsr.myschool.front.client.web.welcome.widget.LoginUiHandlers;
import com.gsr.myschool.front.client.web.welcome.widget.LoginView;
import com.gsr.myschool.front.client.web.welcome.widget.RegisterPresenter;
import com.gsr.myschool.front.client.web.welcome.widget.RegisterUiHandlers;
import com.gsr.myschool.front.client.web.welcome.widget.RegisterView;

public class WelcomeModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bind(LoginUiHandlers.class).to(LoginPresenter.class);
        bind(RegisterUiHandlers.class).to(RegisterPresenter.class);
        bind(ForgotPasswordUiHandlers.class).to(ForgotPasswordPresenter.class);
        bind(ResetPasswordUiHandlers.class).to(ResetPasswordUiHandlers.class);
        bind(ResendmailUiHandlers.class).to(ResendmailPresenter.class);

        bindPresenter(WelcomePresenter.class, WelcomePresenter.MyView.class, WelcomeView.class,
                WelcomePresenter.MyProxy.class);

        bindSingletonPresenterWidget(LoginPresenter.class, LoginPresenter.MyView.class,
                LoginView.class);
        bindSingletonPresenterWidget(RegisterPresenter.class, RegisterPresenter.MyView.class,
                RegisterView.class);
        bindSingletonPresenterWidget(ResetPasswordPresenter.class, ResetPasswordPresenter.MyView.class,
                ResetPasswordView.class);
        bindSingletonPresenterWidget(ForgotPasswordPresenter.class, ForgotPasswordPresenter.MyView.class,
                ForgotPasswordView.class);
        bindSingletonPresenterWidget(ResendmailPresenter.class, ResendmailPresenter.MyView.class,
                ResendmailView.class);
    }
}
