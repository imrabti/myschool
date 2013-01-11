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

package com.gsr.myschool.client;

import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Bootstrapper;
import com.gwtplatform.mvp.client.annotations.IsTheBootstrapper;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gsr.myschool.client.place.NameTokens;
import com.gsr.myschool.client.request.proxy.UserProxy;
import com.gsr.myschool.client.resource.Resources;
import com.gsr.myschool.client.security.CurrentUserProvider;
import com.gsr.myschool.client.security.SecurityUtils;
import com.gsr.myschool.client.util.CallbackImpl;

import java.util.logging.Logger;

@IsTheBootstrapper
public class BootstrapperImpl implements Bootstrapper {
    private final static Logger logger = Logger.getLogger(BootstrapperImpl.class.getName());

    private final PlaceManager placeManager;
    private final SecurityUtils securityUtils;
    private final CurrentUserProvider currentUserProvider;
    private final CallbackImpl<UserProxy> getCurrentUserCallback;

    @Inject
    public BootstrapperImpl(final PlaceManager placeManager, final Resources resources,
                            final SecurityUtils securityUtils,
                            final CurrentUserProvider currentUserProvider) {
        this.placeManager = placeManager;
        this.securityUtils = securityUtils;
        this.currentUserProvider = currentUserProvider;

        resources.generalStyleCss().ensureInjected();

        getCurrentUserCallback = new CallbackImpl<UserProxy>() {
            @Override
            public void onSuccess(UserProxy userProxy) {
                onGetCurrentUser(userProxy);
            }
        };
    }

    @Override
    public void init() {
        if (securityUtils.isLoggedIn()) {
            currentUserProvider.load(getCurrentUserCallback);
        } else {
            bounceToLogin();
        }
    }

    private void onGetCurrentUser(UserProxy currentUser) {
        if (currentUser == null) {
            logger.info("User is not authentified -- access denied...");
            bounceToLogin();
        } else {
            bounceToHome();
        }
    }

    private void bounceToHome() {
        PlaceRequest place = new PlaceRequest(NameTokens.getHome());
        placeManager.revealPlace(place);
    }

    private void bounceToLogin() {
        PlaceRequest place = new PlaceRequest(NameTokens.getLogin());
        placeManager.revealPlace(place);
    }
}
