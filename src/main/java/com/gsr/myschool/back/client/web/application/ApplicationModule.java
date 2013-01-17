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

package com.gsr.myschool.back.client.web.application;

import com.google.inject.TypeLiteral;
import com.gsr.myschool.back.client.web.application.widget.header.HeaderPresenter;
import com.gsr.myschool.back.client.web.application.widget.header.HeaderUiHandlers;
import com.gsr.myschool.back.client.web.application.widget.header.HeaderView;
import com.gsr.myschool.back.client.web.application.widget.sider.MenuPresenter;
import com.gsr.myschool.back.client.web.application.widget.sider.MenuUiHandlers;
import com.gsr.myschool.back.client.web.application.widget.sider.MenuView;
import com.gsr.myschool.common.client.mvp.uihandler.SetterUiHandlersStrategy;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class ApplicationModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bind(new TypeLiteral<UiHandlersStrategy<ApplicationUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<ApplicationUiHandlers>>() {});
        bind(new TypeLiteral<UiHandlersStrategy<HeaderUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<HeaderUiHandlers>>() {});
        bind(new TypeLiteral<UiHandlersStrategy<MenuUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<MenuUiHandlers>>() {});

        bindPresenter(ApplicationPresenter.class, ApplicationPresenter.MyView.class, ApplicationView.class,
                ApplicationPresenter.MyProxy.class);
        bindSingletonPresenterWidget(HeaderPresenter.class, HeaderPresenter.MyView.class,
                HeaderView.class);
        bindSingletonPresenterWidget(MenuPresenter.class, MenuPresenter.MyView.class,
                MenuView.class);
    }
}
