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

package com.gsr.myschool.front.client.web.welcome.widget;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.security.SecurityUtils;
import com.gsr.myschool.common.shared.dto.UserCredentials;
import com.gsr.myschool.front.client.BootstrapperImpl;
import com.gsr.myschool.front.client.request.FrontRequestFactory;
import com.gsr.myschool.front.client.web.welcome.popup.ForgotPasswordPresenter;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

public class LoginPresenter extends PresenterWidget<LoginPresenter.MyView> implements LoginUiHandlers {
    public interface MyView extends View, HasUiHandlers<LoginUiHandlers> {
        void edit(UserCredentials credentials);
    }

    private final FrontRequestFactory requestFactory;
    private final SecurityUtils securityUtils;
    private final BootstrapperImpl bootstrapper;
    private final PlaceManager placeManager;
    private final ForgotPasswordPresenter forgotPasswordPresenter;

    @Inject
    public LoginPresenter(final EventBus eventBus, final MyView view,
                          final FrontRequestFactory requestFactory, final SecurityUtils securityUtils,
                          final BootstrapperImpl bootstrapper, final PlaceManager placeManager,
                          final ForgotPasswordPresenter forgotPasswordPresenter) {
        super(eventBus, view);

        this.requestFactory = requestFactory;
        this.securityUtils = securityUtils;
        this.bootstrapper = bootstrapper;
        this.placeManager = placeManager;
        this.forgotPasswordPresenter = forgotPasswordPresenter;

        getView().setUiHandlers(this);
    }

    @Override
    public void login(final UserCredentials credentials) {
        String username = credentials.getUsername();
        String password = credentials.getPassword();

        requestFactory.authenticationService().authenticate(username, password).fire(new ReceiverImpl<Boolean>() {
            @Override
            public void onSuccess(Boolean authenticated) {
                if (authenticated) {
                    securityUtils.setCredentials(credentials.getUsername(), credentials.getPassword());
                    bootstrapper.onBootstrap();
                }
            }
        });
    }

    @Override
    public void forgotPassword() {
        addToPopupSlot(forgotPasswordPresenter);
    }

    @Override
    protected void onReveal() {
        getView().edit(new UserCredentials());
    }
}
