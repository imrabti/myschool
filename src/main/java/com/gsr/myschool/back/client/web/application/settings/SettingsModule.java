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
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class SettingsModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bind(SettingsUiHandlers.class).to(SettingsPresenter.class);
        bind(SystemScolaireUiHandlers.class).to(SystemScolairePresenter.class);
        bind(AddFiliereUiHandlers.class).to(AddFilierePresenter.class);
        bind(AddNiveauEtudeUiHandlers.class).to(AddNiveauEtudePresenter.class);
        bind(PiecesJustifUiHandlers.class).to(PiecesJustifPresenter.class);
        bind(MatiereExamenUiHandlers.class).to(MatiereExamenPresenter.class);
        bind(EmailTemplateUiHandlers.class).to(EmailTemplatePresenter.class);
        bind(NiveauEtudeSetupUiHandlers.class).to(NiveauEtudeInfosPresenter.class);

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
