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

import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.TypeLiteral;
import com.gsr.myschool.back.client.web.application.user.popup.AdminUserAccountEditPresenter;
import com.gsr.myschool.back.client.web.application.user.popup.AdminUserAccountEditUiHandlers;
import com.gsr.myschool.back.client.web.application.user.popup.AdminUserAccountEditView;
import com.gsr.myschool.back.client.web.application.user.popup.UserAccountEditPresenter;
import com.gsr.myschool.back.client.web.application.user.popup.UserAccountEditUiHandlers;
import com.gsr.myschool.back.client.web.application.user.popup.UserAccountEditView;
import com.gsr.myschool.back.client.web.application.user.popup.UserInscriptionListPresenter;
import com.gsr.myschool.back.client.web.application.user.popup.UserInscriptionListUiHandlers;
import com.gsr.myschool.back.client.web.application.user.popup.UserInscriptionListView;
import com.gsr.myschool.back.client.web.application.user.renderer.AdminUserActionCellFactory;
import com.gsr.myschool.back.client.web.application.user.renderer.UserAccountActionCellFactory;
import com.gsr.myschool.common.client.mvp.uihandler.SetterUiHandlersStrategy;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class UserManagementModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bind(new TypeLiteral<UiHandlersStrategy<UserAccountUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<UserAccountUiHandlers>>() {});
        bind(new TypeLiteral<UiHandlersStrategy<AdminUserAccountUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<AdminUserAccountUiHandlers>>() {
        });
        bind(new TypeLiteral<UiHandlersStrategy<AdminUserAccountEditUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<AdminUserAccountEditUiHandlers>>() {
                });
        bind(new TypeLiteral<UiHandlersStrategy<UserInscriptionListUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<UserInscriptionListUiHandlers>>() {
                });
        bind(new TypeLiteral<UiHandlersStrategy<UserAccountEditUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<UserAccountEditUiHandlers>>() {
                });

        bindSingletonPresenterWidget(AdminUserAccountEditPresenter.class, AdminUserAccountEditPresenter.MyView.class,
                AdminUserAccountEditView.class);
        bindSingletonPresenterWidget(UserAccountEditPresenter.class, UserAccountEditPresenter.MyView.class,
                UserAccountEditView.class);
        bindSingletonPresenterWidget(UserInscriptionListPresenter.class, UserInscriptionListPresenter.MyView.class,
                UserInscriptionListView.class);
        bindPresenter(AdminUserAccountPresenter.class, AdminUserAccountPresenter.MyView.class, AdminUserAccountView.class,
                AdminUserAccountPresenter.MyProxy.class);
        bindPresenter(UserAccountPresenter.class, UserAccountPresenter.MyView.class, UserAccountView.class,
                UserAccountPresenter.MyProxy.class);

        install(new GinFactoryModuleBuilder().build(AdminUserActionCellFactory.class));
        install(new GinFactoryModuleBuilder().build(UserAccountActionCellFactory.class));
    }
}
