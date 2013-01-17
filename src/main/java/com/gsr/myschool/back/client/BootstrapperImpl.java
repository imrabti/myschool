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

package com.gsr.myschool.back.client;

import com.google.inject.Inject;
import com.gsr.myschool.back.client.place.NameTokens;
import com.gsr.myschool.back.client.request.proxy.AdminUserProxy;
import com.gsr.myschool.back.client.resource.AdminResources;
import com.gsr.myschool.back.client.security.CurrentUserProvider;
import com.gsr.myschool.common.client.resource.SharedResources;
import com.gsr.myschool.common.client.security.SecurityUtils;
import com.gsr.myschool.common.client.util.CallbackImpl;
import com.gwtplatform.mvp.client.Bootstrapper;
import com.gwtplatform.mvp.client.annotations.IsTheBootstrapper;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

import java.util.logging.Logger;

@IsTheBootstrapper
public class BootstrapperImpl implements Bootstrapper {
    private final static Logger logger = Logger.getLogger(BootstrapperImpl.class.getName());

    private final PlaceManager placeManager;
    private final SecurityUtils securityUtils;
    private final CurrentUserProvider adminUserProvider;
    private final CallbackImpl<AdminUserProxy> getCurrentUserCallback;

    @Inject
    public BootstrapperImpl(final PlaceManager placeManager,
                            final SharedResources sharedResources,
                            final AdminResources resources,
                            final SecurityUtils securityUtils,
                            final CurrentUserProvider adminUserProvider) {
        this.placeManager = placeManager;
        this.securityUtils = securityUtils;
        this.adminUserProvider = adminUserProvider;

        sharedResources.sharedStyleCss().ensureInjected();
        resources.adminStyleCss().ensureInjected();

        getCurrentUserCallback = new CallbackImpl<AdminUserProxy>() {
            @Override
            public void onSuccess(AdminUserProxy userProxy) {
                onGetCurrentUser(userProxy);
            }
        };
    }

    @Override
    public void init() {
        if (securityUtils.isLoggedIn()) {
            adminUserProvider.load(getCurrentUserCallback);
        } else {
            bounceToLogin();
        }
    }

    private void onGetCurrentUser(AdminUserProxy currentUser) {
        if (currentUser == null) {
            logger.info("User is not authentified -- access denied...");
            bounceToLogin();
        } else {
            bounceToHome();
        }
    }

    private void bounceToHome() {
        PlaceRequest place = new PlaceRequest(NameTokens.getAdministration());
        placeManager.revealPlace(place);
    }

    private void bounceToLogin() {
		// TODO: change this place to login when the stateless security will be fixed
        PlaceRequest place = new PlaceRequest(NameTokens.getAdministration());
        placeManager.revealPlace(place);
    }
}