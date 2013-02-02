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
import com.google.inject.TypeLiteral;
import com.gsr.myschool.common.client.mvp.uihandler.SetterUiHandlersStrategy;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gsr.myschool.front.client.web.application.inscription.renderer.InscriptionActionCellFactory;
import com.gsr.myschool.front.client.web.application.inscription.widget.CandidatPresenter;
import com.gsr.myschool.front.client.web.application.inscription.widget.CandidatView;
import com.gsr.myschool.front.client.web.application.inscription.widget.FrateriePresenter;
import com.gsr.myschool.front.client.web.application.inscription.widget.FraterieUiHandlers;
import com.gsr.myschool.front.client.web.application.inscription.widget.FraterieView;
import com.gsr.myschool.front.client.web.application.inscription.widget.NiveauScolairePresenter;
import com.gsr.myschool.front.client.web.application.inscription.widget.NiveauScolaireView;
import com.gsr.myschool.front.client.web.application.inscription.widget.ParentPresenter;
import com.gsr.myschool.front.client.web.application.inscription.widget.ParentView;
import com.gsr.myschool.front.client.web.application.inscription.widget.ScolariteAnterieurPresenter;
import com.gsr.myschool.front.client.web.application.inscription.widget.ScolariteAnterieurUiHandlers;
import com.gsr.myschool.front.client.web.application.inscription.widget.ScolariteAnterieurView;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class InscriptionModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bind(new TypeLiteral<UiHandlersStrategy<InscriptionUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<InscriptionUiHandlers>>() {});
        bind(new TypeLiteral<UiHandlersStrategy<EditInscriptionUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<EditInscriptionUiHandlers>>() {});
        bind(new TypeLiteral<UiHandlersStrategy<ScolariteAnterieurUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<ScolariteAnterieurUiHandlers>>() {});
        bind(new TypeLiteral<UiHandlersStrategy<FraterieUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<FraterieUiHandlers>>() {});

        bindPresenter(InscriptionPresenter.class, InscriptionPresenter.MyView.class, InscriptionView.class,
                InscriptionPresenter.MyProxy.class);
        bindPresenter(EditInscriptionPresenter.class, EditInscriptionPresenter.MyView.class, EditInscriptionView.class,
                EditInscriptionPresenter.MyProxy.class);

        bindSingletonPresenterWidget(ParentPresenter.class, ParentPresenter.MyView.class,
                ParentView.class);
        bindSingletonPresenterWidget(NiveauScolairePresenter.class, NiveauScolairePresenter.MyView.class,
                NiveauScolaireView.class);
        bindSingletonPresenterWidget(CandidatPresenter.class, CandidatPresenter.MyView.class,
                CandidatView.class);
        bindSingletonPresenterWidget(ScolariteAnterieurPresenter.class, ScolariteAnterieurPresenter.MyView.class,
                ScolariteAnterieurView.class);
        bindSingletonPresenterWidget(FrateriePresenter.class, FrateriePresenter.MyView.class,
                FraterieView.class);

        install(new GinFactoryModuleBuilder().build(InscriptionActionCellFactory.class));
    }
}
