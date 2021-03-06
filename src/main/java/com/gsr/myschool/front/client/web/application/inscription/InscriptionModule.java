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

package com.gsr.myschool.front.client.web.application.inscription;

import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.gsr.myschool.front.client.web.application.inscription.popup.ConfirmationInscriptionPresenter;
import com.gsr.myschool.front.client.web.application.inscription.popup.ConfirmationInscriptionUiHandlers;
import com.gsr.myschool.front.client.web.application.inscription.popup.ConfirmationInscriptionView;
import com.gsr.myschool.front.client.web.application.inscription.popup.EtablissementFilterPresenter;
import com.gsr.myschool.front.client.web.application.inscription.popup.EtablissementFilterUiHandlers;
import com.gsr.myschool.front.client.web.application.inscription.popup.EtablissementFilterView;
import com.gsr.myschool.front.client.web.application.inscription.renderer.InscriptionActionCellFactory;
import com.gsr.myschool.front.client.web.application.inscription.widget.CandidatPresenter;
import com.gsr.myschool.front.client.web.application.inscription.widget.CandidatView;
import com.gsr.myschool.front.client.web.application.inscription.widget.FrateriePresenter;
import com.gsr.myschool.front.client.web.application.inscription.widget.FraterieUiHandlers;
import com.gsr.myschool.front.client.web.application.inscription.widget.FraterieView;
import com.gsr.myschool.front.client.web.application.inscription.widget.ScolariteSouhaiteView;
import com.gsr.myschool.front.client.web.application.inscription.widget.SolariteSouhaitePresenter;
import com.gsr.myschool.front.client.web.application.inscription.widget.ParentPresenter;
import com.gsr.myschool.front.client.web.application.inscription.widget.ParentView;
import com.gsr.myschool.front.client.web.application.inscription.widget.ScolariteActuellePresenter;
import com.gsr.myschool.front.client.web.application.inscription.widget.ScolariteActuelleUiHandlers;
import com.gsr.myschool.front.client.web.application.inscription.widget.ScolariteActuelleView;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class InscriptionModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bind(InscriptionUiHandlers.class).to(InscriptionPresenter.class);
        bind(EditInscriptionUiHandlers.class).to(EditInscriptionPresenter.class);
        bind(ScolariteActuelleUiHandlers.class).to(ScolariteActuellePresenter.class);
        bind(FraterieUiHandlers.class).to(FrateriePresenter.class);
        bind(EtablissementFilterUiHandlers.class).to(EtablissementFilterPresenter.class);
        bind(InscriptionDetailUiHandlers.class).to(InscriptionDetailPresenter.class);
        bind(ConfirmationInscriptionUiHandlers.class).to(ConfirmationInscriptionPresenter.class);

        bindPresenter(InscriptionPresenter.class, InscriptionPresenter.MyView.class, InscriptionView.class,
                InscriptionPresenter.MyProxy.class);
        bindPresenter(EditInscriptionPresenter.class, EditInscriptionPresenter.MyView.class, EditInscriptionView.class,
                EditInscriptionPresenter.MyProxy.class);
        bindPresenter(InscriptionDetailPresenter.class, InscriptionDetailPresenter.MyView.class,
                InscriptionDetailView.class, InscriptionDetailPresenter.MyProxy.class);

        bindSingletonPresenterWidget(ParentPresenter.class, ParentPresenter.MyView.class,
                ParentView.class);
        bindSingletonPresenterWidget(SolariteSouhaitePresenter.class, SolariteSouhaitePresenter.MyView.class,
                ScolariteSouhaiteView.class);
        bindSingletonPresenterWidget(CandidatPresenter.class, CandidatPresenter.MyView.class,
                CandidatView.class);
        bindSingletonPresenterWidget(ScolariteActuellePresenter.class, ScolariteActuellePresenter.MyView.class,
                ScolariteActuelleView.class);
        bindSingletonPresenterWidget(FrateriePresenter.class, FrateriePresenter.MyView.class,
                FraterieView.class);
        bindSingletonPresenterWidget(EtablissementFilterPresenter.class, EtablissementFilterPresenter.MyView.class,
                EtablissementFilterView.class);
        bindSingletonPresenterWidget(ConfirmationInscriptionPresenter.class, ConfirmationInscriptionPresenter.MyView.class,
                ConfirmationInscriptionView.class);

        install(new GinFactoryModuleBuilder().build(InscriptionActionCellFactory.class));
    }
}
