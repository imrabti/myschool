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

package com.gsr.myschool.back.client.web.application.preinscription;

import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.gsr.myschool.back.client.web.application.preinscription.renderer.PreInscriptionActionCellFactory;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class PreInscriptionModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bind(PreInscriptionUiHandlers.class).to(PreInscriptionPresenter.class);

        bindPresenter(PreInscriptionPresenter.class, PreInscriptionPresenter.MyView.class, PreInscriptionView.class,
                PreInscriptionPresenter.MyProxy.class);

        install(new GinFactoryModuleBuilder().build(PreInscriptionActionCellFactory.class));
    }
}
