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

package com.gsr.myschool.back.client.web.administration.widget.header;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.place.NameTokens;
import com.gsr.myschool.common.client.security.AdminUserProvider;
import com.gsr.myschool.common.client.security.SecurityUtils;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class BackOfficeHeaderPresenter extends PresenterWidget<BackOfficeHeaderPresenter.MyView>
		implements BackOfficeHeaderUiHandlers {
    public interface MyView extends View, HasUiHandlers<BackOfficeHeaderUiHandlers> {
        void displayUserInfo(String username);
    }

    private final PlaceManager placeManager;
    private final SecurityUtils securityUtils;
    private final AdminUserProvider adminUserProvider;

    @Inject
    public BackOfficeHeaderPresenter(EventBus eventBus, MyView view,
			final PlaceManager placeManager,
			final SecurityUtils securityUtils,
			final AdminUserProvider adminUserProvider) {
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
    public void setting() {
        // TODO : here display admin settings popup...
    }

    @Override
    protected void onReveal() {
        getView().displayUserInfo(adminUserProvider.get().getUsername());
    }
}
