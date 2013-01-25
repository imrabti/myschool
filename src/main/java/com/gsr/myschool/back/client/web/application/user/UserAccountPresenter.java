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

package com.gsr.myschool.back.client.web.application.user;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.place.NameTokens;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.back.client.security.CurrentUserProvider;
import com.gsr.myschool.back.client.web.application.ApplicationPresenter;
import com.gsr.myschool.back.client.web.application.preinscription.popup.PreInscriptionDetailsPresenter;
import com.gsr.myschool.common.client.proxy.UserProxy;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import java.util.List;

public class UserAccountPresenter extends Presenter<UserAccountPresenter.MyView, UserAccountPresenter.MyProxy>
        implements UserAccountUiHandlers {
    public interface MyView extends View, HasUiHandlers<UserAccountUiHandlers> {
        void setData(List<UserProxy> data);
    }

    @ProxyStandard
    @NameToken(NameTokens.userPortal)
    //@UseGatekeeper(LoggedInGatekeeper.class)
    public interface MyProxy extends ProxyPlace<UserAccountPresenter> {
    }

    private final CurrentUserProvider currentUserProvider;
    private final BackRequestFactory requestFactory;
    private final PreInscriptionDetailsPresenter detailsPresenter;

    @Inject
    public UserAccountPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
			final BackRequestFactory requestFactory,
			final CurrentUserProvider currentUserProvider, final PreInscriptionDetailsPresenter detailsPresenter) {
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);

        this.requestFactory = requestFactory;
        this.currentUserProvider = currentUserProvider;
        this.detailsPresenter = detailsPresenter;

        getView().setUiHandlers(this);
    }

    @Override
    public void accountDetails(UserProxy user) {
        // detailsPresenter.editDrivers(user);
        addToPopupSlot(detailsPresenter);
    }

    @Override
    protected void onReveal() {
        requestFactory.userService().findAllPortalUser()
                .fire(new ReceiverImpl<List<UserProxy>>() {
                    @Override
                    public void onSuccess(List<UserProxy> result) {
                        getView().setData(result);
                    }
                });
    }
}
