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
import com.gsr.myschool.back.client.web.application.settings.popup.AddFilierePresenter;
import com.gsr.myschool.back.client.web.application.settings.popup.AddFiliereUiHandlers;
import com.gsr.myschool.back.client.web.application.settings.popup.AddFiliereView;
import com.gsr.myschool.back.client.web.application.settings.popup.AddNiveauEtudePresenter;
import com.gsr.myschool.back.client.web.application.settings.popup.AddNiveauEtudeUiHandlers;
import com.gsr.myschool.back.client.web.application.settings.popup.AddNiveauEtudeView;
import com.gsr.myschool.back.client.web.application.settings.popup.NiveauEtudeInfosPresenter;
import com.gsr.myschool.back.client.web.application.settings.popup.NiveauEtudeInfosView;
import com.gsr.myschool.back.client.web.application.settings.popup.NiveauEtudeSetupUiHandlers;
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
        bind(new TypeLiteral<UiHandlersStrategy<AddFiliereUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<AddFiliereUiHandlers>>() {});
        bind(new TypeLiteral<UiHandlersStrategy<AddNiveauEtudeUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<AddNiveauEtudeUiHandlers>>() {});
        bind(new TypeLiteral<UiHandlersStrategy<PiecesJustifUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<PiecesJustifUiHandlers>>() {});
        bind(new TypeLiteral<UiHandlersStrategy<MatiereExamenUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<MatiereExamenUiHandlers>>() {});
        bind(new TypeLiteral<UiHandlersStrategy<EmailTemplateUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<EmailTemplateUiHandlers>>() {});

        bind(new TypeLiteral<UiHandlersStrategy<NiveauEtudeSetupUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<NiveauEtudeSetupUiHandlers>>() {});

        bindPresenter(SettingsPresenter.class, SettingsPresenter.MyView.class, SettingsView.class,
                SettingsPresenter.MyProxy.class);

        bindSingletonPresenterWidget(NiveauEtudeInfosPresenter.class, NiveauEtudeInfosPresenter.MyView.class,
                NiveauEtudeInfosView.class);
        bindSingletonPresenterWidget(SystemScolairePresenter.class, SystemScolairePresenter.MyView.class,
                SystemScolaireView.class);
        bindSingletonPresenterWidget(AddFilierePresenter.class, AddFilierePresenter.MyView.class,
                AddFiliereView.class);
        bindSingletonPresenterWidget(AddNiveauEtudePresenter.class, AddNiveauEtudePresenter.MyView.class,
                AddNiveauEtudeView.class);
        bindSingletonPresenterWidget(MatiereExamenPresenter.class, MatiereExamenPresenter.MyView.class,
                MatiereExamenView.class);
        bindSingletonPresenterWidget(PiecesJustifPresenter.class, PiecesJustifPresenter.MyView.class,
                PiecesJustifView.class);
        bindSingletonPresenterWidget(EmailTemplatePresenter.class, EmailTemplatePresenter.MyView.class,
                EmailTemplateView.class);

        install(new GinFactoryModuleBuilder().build(NiveauEtudeInfosTreeFactory.class));
    }
}
