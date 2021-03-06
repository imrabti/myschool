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

package com.gsr.myschool.back.client.web.welcome.login;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.BootstrapperImpl;
import com.gsr.myschool.back.client.place.NameTokens;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.back.client.web.RootPresenter;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.security.SecurityUtils;
import com.gsr.myschool.common.shared.dto.UserCredentials;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

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

    private final BackRequestFactory requestFactory;
    private final SecurityUtils securityUtils;
    private final BootstrapperImpl bootstrapper;

    @Inject
    public LoginPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
                          final BackRequestFactory requestFactory, final SecurityUtils securityUtils,
                          final BootstrapperImpl bootstrapper) {
        super(eventBus, view, proxy, RootPresenter.TYPE_SetMainContent);

        this.requestFactory = requestFactory;
        this.securityUtils = securityUtils;
        this.bootstrapper = bootstrapper;

        getView().setUiHandlers(this);
    }

    @Override
    public void login(final UserCredentials credentials) {
        String username = credentials.getUsername();
        String password = credentials.getPassword();

        requestFactory.adminAuthenticationService().authenticate(username, password).fire(new ReceiverImpl<Boolean>() {
            @Override
            public void onSuccess(Boolean authenticated) {
                if (authenticated) {
                    securityUtils.setCredentials(credentials.getUsername(), credentials.getPassword());
                    bootstrapper.onBootstrap();
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
