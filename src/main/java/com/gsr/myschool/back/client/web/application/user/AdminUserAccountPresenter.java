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
import com.gsr.myschool.back.client.web.application.user.popup.AdminUserAccountEditPresenter;
import com.gsr.myschool.back.client.web.application.user.popup.AdminUserAccountEditUiHandlers;
import com.gsr.myschool.common.client.proxy.AdminUserProxy;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.widget.messages.MessagePresenter;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import java.util.List;

public class AdminUserAccountPresenter extends Presenter<AdminUserAccountPresenter.MyView, AdminUserAccountPresenter.MyProxy>
        implements AdminUserAccountUiHandlers, AdminUserAccountEditUiHandlers {
    public interface MyView extends View, HasUiHandlers<AdminUserAccountUiHandlers> {
        void setData(List<AdminUserProxy> data);
    }

    @ProxyStandard
    @NameToken(NameTokens.userGsr)
    //@UseGatekeeper(LoggedInGatekeeper.class)
    public interface MyProxy extends ProxyPlace<AdminUserAccountPresenter> {
    }

    private final CurrentUserProvider currentUserProvider;
    private final BackRequestFactory requestFactory;
    private final MessagePresenter messagePresenter;
    private final AdminUserAccountEditPresenter adminEditPresenter;

    @Inject
    public AdminUserAccountPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
            final BackRequestFactory requestFactory, final CurrentUserProvider currentUserProvider,
            final MessagePresenter messagePresenter, final AdminUserAccountEditPresenter adminEditPresenter) {
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);

        this.requestFactory = requestFactory;
        this.currentUserProvider = currentUserProvider;
        this.messagePresenter = messagePresenter;
        this.adminEditPresenter = adminEditPresenter;

        getView().setUiHandlers(this);
        adminEditPresenter.getView().setUiHandlers(this);
    }

    @Override
    public void accountDetails(AdminUserProxy adminUser) {
        adminEditPresenter.editAccount(adminUser, messagePresenter, requestFactory.userService());
        addToPopupSlot(adminEditPresenter);

    }

    @Override
    public void addAccount() {
        adminEditPresenter.addAccount(requestFactory, messagePresenter);
        addToPopupSlot(adminEditPresenter);
    }

    @Override
    public void reloadUsers() {
        loadUsers();
    }

    @Override
    protected void onReveal() {
        loadUsers();
    }

    private void loadUsers() {
        requestFactory.userService().findAllAdminUser()
                .fire(new ReceiverImpl<List<AdminUserProxy>>() {
                    @Override
                    public void onSuccess(List<AdminUserProxy> result) {
                        getView().setData(result);
                    }
                });
    }
}
