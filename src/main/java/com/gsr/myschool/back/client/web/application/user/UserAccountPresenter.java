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
import com.gsr.myschool.back.client.request.UserServiceRequest;
import com.gsr.myschool.back.client.web.application.ApplicationPresenter;
import com.gsr.myschool.back.client.web.application.user.event.UserAccountChangedEvent;
import com.gsr.myschool.back.client.web.application.user.popup.UserAccountEditPresenter;
import com.gsr.myschool.back.client.web.application.user.popup.UserInscriptionListPresenter;
import com.gsr.myschool.common.client.proxy.DossierFilterDTOProxy;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.client.proxy.UserFilterDTOProxy;
import com.gsr.myschool.common.client.proxy.UserProxy;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.security.LoggedInGatekeeper;
import com.gsr.myschool.common.shared.type.DossierStatus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import java.util.List;

public class UserAccountPresenter extends Presenter<UserAccountPresenter.MyView, UserAccountPresenter.MyProxy>
        implements UserAccountUiHandlers, UserAccountChangedEvent.UserAccountChangedHandler {
    public interface MyView extends View, HasUiHandlers<UserAccountUiHandlers> {
        void setData(List<UserProxy> data);

        void editUserFilter(UserFilterDTOProxy userFilter);
    }

    @ProxyStandard
    @NameToken(NameTokens.userPortal)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface MyProxy extends ProxyPlace<UserAccountPresenter> {
    }

    private final BackRequestFactory requestFactory;
    private final UserAccountEditPresenter userAccountEditPresenter;

    private UserServiceRequest currentContext;
    private UserFilterDTOProxy currentUserFilter;

    @Inject
    public UserAccountPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
                                final BackRequestFactory requestFactory,
                                final UserAccountEditPresenter userAccountEditPresenter) {
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);

        this.requestFactory = requestFactory;
        this.userAccountEditPresenter = userAccountEditPresenter;

        getView().setUiHandlers(this);
    }

    @Override
    public void searchWithFilter(UserFilterDTOProxy userFilter) {
        currentContext.findAllUsersByCriteria(userFilter).fire(new ReceiverImpl<List<UserProxy>>() {
            @Override
            public void onSuccess(List<UserProxy> response) {
                currentContext = requestFactory.userService();
                currentUserFilter = currentContext.edit(currentUserFilter);

                getView().setData(response);
                getView().editUserFilter(currentUserFilter);
            }
        });
    }

    @Override
    public void update(UserProxy currentUser) {
        userAccountEditPresenter.editDatas(currentUser);
        addToPopupSlot(userAccountEditPresenter);
    }

    @Override
    public void onUserAccountChanged(UserAccountChangedEvent event) {
        loadUsers();
    }

    @Override
    protected void onBind() {
        super.onBind();

        addRegisteredHandler(UserAccountChangedEvent.TYPE, this);
    }

    @Override
    protected void onReveal() {
        currentContext = requestFactory.userService();
        currentUserFilter = currentContext.create(UserFilterDTOProxy.class);
        getView().editUserFilter(currentUserFilter);
    }

    private void loadUsers() {
        requestFactory.userService().findAllPortalUser().fire(new ReceiverImpl<List<UserProxy>>() {
            @Override
            public void onSuccess(List<UserProxy> result) {
                getView().setData(result);
            }
        });
    }
}
