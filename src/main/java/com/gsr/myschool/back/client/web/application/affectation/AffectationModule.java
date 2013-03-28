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

package com.gsr.myschool.back.client.web.application.affectation;

import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.TypeLiteral;
import com.gsr.myschool.back.client.web.application.affectation.popup.SessionAffectationPresenter;
import com.gsr.myschool.back.client.web.application.affectation.popup.SessionAffectationUiHandlers;
import com.gsr.myschool.back.client.web.application.affectation.popup.SessionAffectationView;
import com.gsr.myschool.back.client.web.application.affectation.renderer.AffectationActionCellFactory;
import com.gsr.myschool.common.client.mvp.uihandler.SetterUiHandlersStrategy;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class AffectationModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bind(new TypeLiteral<UiHandlersStrategy<AffectationUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<AffectationUiHandlers>>() {});
        bind(new TypeLiteral<UiHandlersStrategy<SessionAffectationUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<SessionAffectationUiHandlers>>() {});

        bindPresenter(AffectationPresenter.class, AffectationPresenter.MyView.class, AffectationView.class,
                AffectationPresenter.MyProxy.class);
        bindPresenterWidget(SessionAffectationPresenter.class, SessionAffectationPresenter.MyView.class, SessionAffectationView.class);

        install(new GinFactoryModuleBuilder().build(AffectationActionCellFactory.class));
    }
}
