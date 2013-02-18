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

package com.gsr.myschool.back.client.web.application.settings;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.place.NameTokens;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.back.client.web.application.ApplicationPresenter;
import com.gsr.myschool.back.client.web.application.settings.popup.NiveauEtudeInfosPresenter;
import com.gsr.myschool.common.client.proxy.NiveauEtudeProxy;
import com.gsr.myschool.common.client.security.HasRoleGatekeeper;
import com.gsr.myschool.common.shared.constants.GlobalParameters;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.GatekeeperParams;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

public class SettingsPresenter extends Presenter<SettingsPresenter.MyView, SettingsPresenter.MyProxy>
        implements SettingsUiHandlers {
    public interface MyView extends View, HasUiHandlers<SettingsUiHandlers> {
    }

    private final NiveauEtudeInfosPresenter niveauEtudeInfosPresenter;

    @ProxyStandard
    @NameToken(NameTokens.generalSettings)
    @UseGatekeeper(HasRoleGatekeeper.class)
    @GatekeeperParams({GlobalParameters.ROLE_ADMIN})
    public interface MyProxy extends ProxyPlace<SettingsPresenter> {
    }

    private final BackRequestFactory requestFactory;

    @Inject
    public SettingsPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
                             final BackRequestFactory requestFactory,
                             final NiveauEtudeInfosPresenter niveauEtudeInfosPresenter) {
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);

        this.requestFactory = requestFactory;
        this.niveauEtudeInfosPresenter = niveauEtudeInfosPresenter;

        getView().setUiHandlers(this);
    }

    @Override
    public void showDetails(NiveauEtudeProxy value) {
        niveauEtudeInfosPresenter.setCurrentNiveauEtude(value);
        addToPopupSlot(niveauEtudeInfosPresenter);
    }
}
