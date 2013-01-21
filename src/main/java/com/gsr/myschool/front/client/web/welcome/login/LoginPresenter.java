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

package com.gsr.myschool.front.client.web.welcome.login;

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.security.SecurityUtils;
import com.gsr.myschool.common.client.widget.messages.CloseDelay;
import com.gsr.myschool.common.client.widget.messages.Message;
import com.gsr.myschool.common.client.widget.messages.event.MessageEvent;
import com.gsr.myschool.common.shared.dto.UserCredentials;
import com.gsr.myschool.front.client.BootstrapperImpl;
import com.gsr.myschool.front.client.place.NameTokens;
import com.gsr.myschool.front.client.request.FrontRequestFactory;
import com.gsr.myschool.front.client.resource.message.MessageBundle;
import com.gsr.myschool.front.client.web.RootPresenter;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
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

    private final FrontRequestFactory requestFactory;
    private final SecurityUtils securityUtils;
    private final BootstrapperImpl bootstrapper;
    private final PlaceManager placeManager;
    private final MessageBundle messageBundle;

    @Inject
    public LoginPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
                          final FrontRequestFactory requestFactory, final SecurityUtils securityUtils,
                          final BootstrapperImpl bootstrapper, final PlaceManager placeManager,
                          final MessageBundle messageBundle) {
        super(eventBus, view, proxy, RootPresenter.TYPE_SetMainContent);

        this.messageBundle = messageBundle;
        this.requestFactory = requestFactory;
        this.securityUtils = securityUtils;
        this.bootstrapper = bootstrapper;
        this.placeManager = placeManager;

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
    public void register() {
        placeManager.revealPlace(new PlaceRequest(NameTokens.getRegister()));
    }

    @Override
    protected void onReveal() {
        getView().edit(new UserCredentials());
    }

    @Override
    public void prepareFromRequest(PlaceRequest placeRequest) {
        super.prepareFromRequest(placeRequest);
        String token = placeRequest.getParameter("token", "");
        if (!"".equals(token)) {
            requestFactory.registrationService().activateAccount(token).fire(new ReceiverImpl<Boolean>() {
                @Override
                public void onSuccess(Boolean aBoolean) {
                    String messageString = aBoolean ? messageBundle.activateAccountSucces() : messageBundle.activateAccountFaillure();
                    AlertType alertType = aBoolean ? AlertType.SUCCESS : AlertType.ERROR;
                    Message message = new Message.Builder(messageString)
                            .style(alertType)
                            .closeDelay(CloseDelay.NEVER)
                            .build();
                    MessageEvent.fire(this, message);
                }
            });
        }
    }
}
