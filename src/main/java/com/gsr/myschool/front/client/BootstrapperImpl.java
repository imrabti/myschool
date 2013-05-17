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

package com.gsr.myschool.front.client;

import com.google.inject.Inject;
import com.gsr.myschool.common.client.proxy.UserProxy;
import com.gsr.myschool.common.client.resource.SharedResources;
import com.gsr.myschool.common.client.security.SecurityUtils;
import com.gsr.myschool.common.client.util.CallbackImpl;
import com.gsr.myschool.common.client.util.ValueList;
import com.gsr.myschool.common.shared.type.Authority;
import com.gsr.myschool.front.client.place.NameTokens;
import com.gsr.myschool.front.client.resource.FrontResources;
import com.gsr.myschool.front.client.security.CurrentUserProvider;
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
    private final CurrentUserProvider currentUserProvider;
    private final CallbackImpl<UserProxy> getCurrentUserCallback;

    @Inject
    public BootstrapperImpl(final PlaceManager placeManager,
                            final SharedResources sharedResources,
                            final FrontResources frontResources,
                            final SecurityUtils securityUtils,
                            final CurrentUserProvider currentUserProvider,
                            final ValueList valueList) {
        this.placeManager = placeManager;
        this.securityUtils = securityUtils;
        this.currentUserProvider = currentUserProvider;

        frontResources.frontStyleCss().ensureInjected();
        sharedResources.popupStyleCss().ensureInjected();
        sharedResources.sharedStyleCss().ensureInjected();
        sharedResources.suggestBoxStyleCss().ensureInjected();
        sharedResources.datePickerStyle().ensureInjected();

        valueList.initFiliereList(securityUtils.isSuperUser()
                || securityUtils.hasAuthority(Authority.ROLE_USER_VIP.name()));
        valueList.initEtablissementScolaireList();
        valueList.initNiveauEtudeMap(securityUtils.isSuperUser()
                || securityUtils.hasAuthority(Authority.ROLE_USER_VIP.name()));
        valueList.initValueListMap();

        getCurrentUserCallback = new CallbackImpl<UserProxy>() {
            @Override
            public void onSuccess(UserProxy userProxy) {
                onGetCurrentUser(userProxy);
            }
        };
    }

    @Override
    public void onBootstrap() {
        if (securityUtils.isLoggedIn()) {
            currentUserProvider.load(getCurrentUserCallback);
        } else {
            placeManager.revealCurrentPlace();
        }
    }

    private void onGetCurrentUser(UserProxy currentUser) {
        if (currentUser == null) {
            logger.info("User is not authentified -- access denied...");
            placeManager.revealCurrentPlace();
        } else {
            List<String> authorities = new ArrayList<String>();
            authorities.add(currentUser.getAuthority().name());
            securityUtils.setAuthorities(authorities);

            bounceToHome();
        }
    }

    private void bounceToHome() {
        PlaceRequest place = new PlaceRequest(NameTokens.getInscription());
        placeManager.revealPlace(place);
    }
}
