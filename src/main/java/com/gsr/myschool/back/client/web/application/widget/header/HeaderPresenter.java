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

package com.gsr.myschool.back.client.web.application.widget.header;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.place.NameTokens;
import com.gsr.myschool.back.client.security.CurrentUserProvider;
import com.gsr.myschool.common.client.security.SecurityUtils;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class HeaderPresenter extends PresenterWidget<HeaderPresenter.MyView>
		implements HeaderUiHandlers {
    public interface MyView extends View, HasUiHandlers<HeaderUiHandlers> {
        void displayUserInfo(String firstname, String lastname);
    }

    private final PlaceManager placeManager;
    private final SecurityUtils securityUtils;
    private final CurrentUserProvider adminUserProvider;

    @Inject
    public HeaderPresenter(EventBus eventBus, MyView view,
                           final PlaceManager placeManager,
                           final SecurityUtils securityUtils,
                           final CurrentUserProvider adminUserProvider) {
        super(eventBus, view);

        this.placeManager = placeManager;
        this.securityUtils = securityUtils;
        this.adminUserProvider = adminUserProvider;

        getView().setUiHandlers(this);
    }

    @Override
    public void logout() {
        securityUtils.clearCredentials();
        placeManager.revealPlace(new PlaceRequest(NameTokens.getLogin()));
    }

    @Override
    protected void onReveal() {
        getView().displayUserInfo(adminUserProvider.get().getFirstName(), adminUserProvider.get().getLastName());
    }
}
