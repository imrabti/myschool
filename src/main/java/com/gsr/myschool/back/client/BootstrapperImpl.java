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
import com.gsr.myschool.back.client.resource.AdminResources;
import com.gsr.myschool.back.client.security.CurrentUserProvider;
import com.gsr.myschool.common.client.proxy.AdminUserProxy;
import com.gsr.myschool.common.client.resource.SharedResources;
import com.gsr.myschool.common.client.security.SecurityUtils;
import com.gsr.myschool.common.client.util.CallbackImpl;
import com.gsr.myschool.common.client.util.ValueList;
import com.gsr.myschool.common.shared.constants.GlobalParameters;
import com.gwtplatform.mvp.client.Bootstrapper;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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
                            final CurrentUserProvider adminUserProvider,
                            final ValueList valueList) {
        this.placeManager = placeManager;
        this.securityUtils = securityUtils;
        this.adminUserProvider = adminUserProvider;

        resources.adminStyleCss().ensureInjected();
        sharedResources.sharedStyleCss().ensureInjected();
        sharedResources.popupStyleCss().ensureInjected();
        sharedResources.suggestBoxStyleCss().ensureInjected();
        sharedResources.datePickerStyle().ensureInjected();

        valueList.initFiliereList(false);
        valueList.initEtablissementScolaireList();
        valueList.initNiveauEtudeMap(false);
        valueList.initValueListMap();

        getCurrentUserCallback = new CallbackImpl<AdminUserProxy>() {
            @Override
            public void onSuccess(AdminUserProxy userProxy) {
                onGetCurrentUser(userProxy);
            }
        };
    }

    @Override
    public void onBootstrap() {
        if (securityUtils.isLoggedIn()) {
            adminUserProvider.load(getCurrentUserCallback);
        } else {
            placeManager.revealCurrentPlace();
        }
    }

    private void onGetCurrentUser(AdminUserProxy currentUser) {
        if (currentUser == null) {
            logger.info("User is not authentified -- access denied...");
            placeManager.revealCurrentPlace();
        } else {
            List<String> authorities = new ArrayList<String>();
            authorities.add(currentUser.getAuthority().name());
            securityUtils.setAuthorities(authorities);

            if (securityUtils.hasAuthority(GlobalParameters.ROLE_ADMIN)) {
                bounceToHome();
            } else if (securityUtils.hasAuthority(GlobalParameters.ROLE_OPERATOR)) {
                bounceToReception();
            } else if (securityUtils.hasAuthority(GlobalParameters.ROLE_REPORTER)) {
                bounceToReporting();
            }
        }
    }

    private void bounceToHome() {
        PlaceRequest place = new PlaceRequest(NameTokens.getPreInscriptions());
        placeManager.revealPlace(place);
    }

    private void bounceToReception() {
        PlaceRequest place = new PlaceRequest(NameTokens.getReception());
        placeManager.revealPlace(place);
    }

    private void bounceToReporting() {
        PlaceRequest place = new PlaceRequest(NameTokens.getReporting());
        placeManager.revealPlace(place);
    }
}
