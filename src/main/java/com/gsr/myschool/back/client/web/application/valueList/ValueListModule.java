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
import com.gsr.myschool.back.client.web.application.valueList.popup.*;
import com.gsr.myschool.back.client.web.application.valueList.renderer.ValueListActionCellFactory;
import com.gsr.myschool.back.client.web.application.valueList.renderer.ValueTypeActionCellFactory;
import com.gsr.myschool.back.client.web.application.valueList.widget.ValueTypePresenter;
import com.gsr.myschool.back.client.web.application.valueList.widget.ValueTypeUiHandlers;
import com.gsr.myschool.back.client.web.application.valueList.widget.ValueTypeView;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class ValueListModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bind(AddValueTypeUiHandlers.class).to(AddValueTypePresenter.class);
        bind(ValueTypeUiHandlers.class).to(ValueTypePresenter.class);
        bind(AddValueListUiHandlers.class).to(AddValueListPresenter.class);
        bind(ValueListUiHandlers.class).to(ValueListPresenter.class);

        bindPresenter(ValueListPresenter.class, ValueListPresenter.MyView.class, ValueListView.class,
                ValueListPresenter.MyProxy.class);

        bindSingletonPresenterWidget(ValueTypePresenter.class, ValueTypePresenter.MyView.class,
                ValueTypeView.class);
        bindSingletonPresenterWidget(AddValueListPresenter.class, AddValueListPresenter.MyView.class,
                AddValueListView.class);
        bindSingletonPresenterWidget(AddValueTypePresenter.class, AddValueTypePresenter.MyView.class,
                AddValueTypeView.class);

        install(new GinFactoryModuleBuilder().build(ValueListActionCellFactory.class));
        install(new GinFactoryModuleBuilder().build(ValueTypeActionCellFactory.class));
    }
}
