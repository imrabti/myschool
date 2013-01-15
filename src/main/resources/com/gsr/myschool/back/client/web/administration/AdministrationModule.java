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

package com.gsr.myschool.back.client.web.administration;

import com.google.inject.TypeLiteral;
import com.gsr.myschool.common.client.mvp.uihandler.SetterUiHandlersStrategy;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gsr.myschool.back.client.web.administration.widget.header.BackOfficeHeaderPresenter;
import com.gsr.myschool.back.client.web.administration.widget.header.BackOfficeHeaderUiHandlers;
import com.gsr.myschool.back.client.web.administration.widget.header.BackOfficeHeaderView;
import com.gsr.myschool.back.client.web.administration.widget.sider.BackOfficeMenuPresenter;
import com.gsr.myschool.back.client.web.administration.widget.sider.BackOfficeMenuUiHandlers;
import com.gsr.myschool.back.client.web.administration.widget.sider.BackOfficeMenuView;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class AdministrationModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bind(new TypeLiteral<UiHandlersStrategy<AdministrationUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<AdministrationUiHandlers>>() {});
		bind(new TypeLiteral<UiHandlersStrategy<BackOfficeHeaderUiHandlers>>() {})
				.to(new TypeLiteral<SetterUiHandlersStrategy<BackOfficeHeaderUiHandlers>>() {});
		bind(new TypeLiteral<UiHandlersStrategy<BackOfficeMenuUiHandlers>>() {})
				.to(new TypeLiteral<SetterUiHandlersStrategy<BackOfficeMenuUiHandlers>>() {});

        bindPresenter(AdministrationPresenter.class, AdministrationPresenter.MyView.class, AdministrationView.class,
                AdministrationPresenter.MyProxy.class);
		bindSingletonPresenterWidget(BackOfficeHeaderPresenter.class, BackOfficeHeaderPresenter.MyView.class,
				BackOfficeHeaderView.class);
		bindSingletonPresenterWidget(BackOfficeMenuPresenter.class, BackOfficeMenuPresenter.MyView.class,
				BackOfficeMenuView.class);
    }
}
