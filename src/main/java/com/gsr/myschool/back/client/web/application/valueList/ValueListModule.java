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

package com.gsr.myschool.back.client.web.application.valueList;

import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.TypeLiteral;
import com.gsr.myschool.back.client.web.application.valueList.popup.*;
import com.gsr.myschool.back.client.web.application.valueList.renderer.ValueListActionCellFactory;
import com.gsr.myschool.back.client.web.application.valueList.renderer.ValueTypeActionCellFactory;
import com.gsr.myschool.back.client.web.application.valueList.widget.ValueTypePresenter;
import com.gsr.myschool.back.client.web.application.valueList.widget.ValueTypeUiHandlers;
import com.gsr.myschool.back.client.web.application.valueList.widget.ValueTypeView;
import com.gsr.myschool.common.client.mvp.uihandler.SetterUiHandlersStrategy;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class ValueListModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bind(new TypeLiteral<UiHandlersStrategy<AddValueTypeUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<AddValueTypeUiHandlers>>() {});

        bind(new TypeLiteral<UiHandlersStrategy<ValueTypeUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<ValueTypeUiHandlers>>() {});

        bind(new TypeLiteral<UiHandlersStrategy<AddValueListUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<AddValueListUiHandlers>>() {});

        bind(new TypeLiteral<UiHandlersStrategy<ValueListUiHandlers>>(){})
                .to(new TypeLiteral<SetterUiHandlersStrategy<ValueListUiHandlers>>(){});

        bindPresenter(ValueListPresenter.class, ValueListPresenter.MyView.class, ValueListView.class,
                ValueListPresenter.MyProxy.class);

        bindPresenterWidget(ValueTypePresenter.class, ValueTypePresenter.MyView.class, ValueTypeView.class);
        bindPresenterWidget(AddValueListPresenter.class, AddValueListPresenter.MyView.class, AddValueListView.class);
        bindPresenterWidget(AddValueTypePresenter.class, AddValueTypePresenter.MyView.class, AddValueTypeView.class);

        install(new GinFactoryModuleBuilder().build(ValueListActionCellFactory.class));
        install(new GinFactoryModuleBuilder().build(ValueTypeActionCellFactory.class));
    }
}
