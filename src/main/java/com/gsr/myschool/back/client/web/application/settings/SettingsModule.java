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

package com.gsr.myschool.back.client.web.application.settings;

import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.TypeLiteral;
import com.gsr.myschool.back.client.web.application.settings.popup.NiveauEtudeInfosPresenter;
import com.gsr.myschool.back.client.web.application.settings.popup.NiveauEtudeInfosView;
import com.gsr.myschool.back.client.web.application.settings.renderer.NiveauEtudeInfosTreeFactory;
import com.gsr.myschool.back.client.web.application.settings.widget.*;
import com.gsr.myschool.common.client.mvp.uihandler.SetterUiHandlersStrategy;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class SettingsModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bind(new TypeLiteral<UiHandlersStrategy<SettingsUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<SettingsUiHandlers>>() {});
        bind(new TypeLiteral<UiHandlersStrategy<SystemScolaireUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<SystemScolaireUiHandlers>>() {});
        bind(new TypeLiteral<UiHandlersStrategy<PiecesJustifUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<PiecesJustifUiHandlers>>() {});
        bind(new TypeLiteral<UiHandlersStrategy<MatiereExamenUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<MatiereExamenUiHandlers>>() {});

        bindPresenter(SettingsPresenter.class, SettingsPresenter.MyView.class, SettingsView.class,
                SettingsPresenter.MyProxy.class);

        bindSingletonPresenterWidget(NiveauEtudeInfosPresenter.class, NiveauEtudeInfosPresenter.MyView.class,
                NiveauEtudeInfosView.class);
        bindSingletonPresenterWidget(SystemScolairePresenter.class, SystemScolairePresenter.MyView.class,
                SystemScolaireView.class);
        bindSingletonPresenterWidget(MatiereExamenPresenter.class, MatiereExamenPresenter.MyView.class,
                MatiereExamenView.class);
        bindSingletonPresenterWidget(PiecesJustifPresenter.class, PiecesJustifPresenter.MyView.class,
                PiecesJustifView.class);

        install(new GinFactoryModuleBuilder().build(NiveauEtudeInfosTreeFactory.class));
    }
}
