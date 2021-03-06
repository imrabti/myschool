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

package com.gsr.myschool.back.client.web.application.usergsr;

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.place.NameTokens;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.back.client.resource.message.MessageBundle;
import com.gsr.myschool.back.client.web.application.ApplicationPresenter;
import com.gsr.myschool.back.client.web.application.usergsr.event.AdminUserChangedEvent;
import com.gsr.myschool.back.client.web.application.usergsr.popup.AdminUserAccountEditPresenter;
import com.gsr.myschool.common.client.proxy.AdminUserProxy;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.security.HasRoleGatekeeper;
import com.gsr.myschool.common.client.widget.messages.CloseDelay;
import com.gsr.myschool.common.client.widget.messages.Message;
import com.gsr.myschool.common.client.widget.messages.event.MessageEvent;
import com.gsr.myschool.common.shared.constants.GlobalParameters;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.GatekeeperParams;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import java.util.List;

public class AdminUserAccountPresenter extends Presenter<AdminUserAccountPresenter.MyView, AdminUserAccountPresenter.MyProxy>
        implements AdminUserAccountUiHandlers, AdminUserChangedEvent.AdminUserChangedHandler {
    public interface MyView extends View, HasUiHandlers<AdminUserAccountUiHandlers> {
        void setData(List<AdminUserProxy> data);
    }

    @ProxyStandard
    @NameToken(NameTokens.userGsr)
    @UseGatekeeper(HasRoleGatekeeper.class)
    @GatekeeperParams({GlobalParameters.ROLE_ADMIN})
    public interface MyProxy extends ProxyPlace<AdminUserAccountPresenter> {
    }

    private final BackRequestFactory requestFactory;
    private final MessageBundle messageBundle;
    private final AdminUserAccountEditPresenter adminEditPresenter;

    @Inject
    public AdminUserAccountPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
                                     final BackRequestFactory requestFactory,
                                     final MessageBundle messageBundle,
                                     final AdminUserAccountEditPresenter adminEditPresenter) {
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);

        this.requestFactory = requestFactory;
        this.messageBundle = messageBundle;
        this.adminEditPresenter = adminEditPresenter;

        getView().setUiHandlers(this);
    }

    @Override
    public void addUser() {
        adminEditPresenter.initDatas();
        addToPopupSlot(adminEditPresenter);
    }

    @Override
    public void delete(AdminUserProxy currentUser) {
        if (Window.confirm(messageBundle.deleteConfirmation())) {
            requestFactory.userService().deleteAdminUser(currentUser.getId()).fire(new ReceiverImpl<Void>() {
                @Override
                public void onSuccess(Void response) {
                    Message message = new Message.Builder(messageBundle.deleteValueListSuccess())
                            .style(AlertType.SUCCESS)
                            .closeDelay(CloseDelay.DEFAULT)
                            .build();
                    MessageEvent.fire(this, message);

                    loadUsers();
                }
            });
        }
    }

    @Override
    public void update(AdminUserProxy currentUser) {
        adminEditPresenter.editDatas(currentUser);
        addToPopupSlot(adminEditPresenter);
    }

    @Override
    protected void onReveal() {
        loadUsers();
    }

    private void loadUsers() {
        requestFactory.userService().findAllAdminUser().fire(new ReceiverImpl<List<AdminUserProxy>>() {
            @Override
            public void onSuccess(List<AdminUserProxy> result) {
                getView().setData(result);
            }
        });
    }

    @Override
    public void onAdminUserChanged(AdminUserChangedEvent event) {
        loadUsers();
    }

    protected void onBind() {
        super.onBind();

        addRegisteredHandler(AdminUserChangedEvent.TYPE, this);
    }
}
