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

import com.google.gwt.http.client.*;
import com.google.inject.Inject;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.place.NameTokens;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.back.client.request.DossierServiceRequest;
import com.gsr.myschool.back.client.web.application.ApplicationPresenter;
import com.gsr.myschool.common.client.proxy.DossierFilterDTOProxy;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.client.request.ExcelRequestBuilder;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.security.HasRoleGatekeeper;
import com.gsr.myschool.common.client.util.URLUtils;
import com.gsr.myschool.common.shared.constants.GlobalParameters;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.GatekeeperParams;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import java.util.List;

public class PreInscriptionPresenter extends Presenter<PreInscriptionPresenter.MyView, PreInscriptionPresenter.MyProxy>
        implements PreInscriptionUiHandlers {
    public interface MyView extends View, HasUiHandlers<PreInscriptionUiHandlers> {
        void setData(List<DossierProxy> data);

        void editDossierFilter(DossierFilterDTOProxy dossierFilter);
    }

    @ProxyStandard
    @NameToken(NameTokens.preInscriptions)
    @UseGatekeeper(HasRoleGatekeeper.class)
    @GatekeeperParams({GlobalParameters.ROLE_ADMIN})
    public interface MyProxy extends ProxyPlace<PreInscriptionPresenter> {
    }

    private final BackRequestFactory requestFactory;
    private final PlaceManager placeManager;

    private DossierServiceRequest currentContext;
    private DossierFilterDTOProxy currentDossierFilter;

    @Inject
    public PreInscriptionPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
                                   final BackRequestFactory requestFactory,
                                   final PlaceManager placeManager) {
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);

        this.requestFactory = requestFactory;
        this.placeManager = placeManager;

        getView().setUiHandlers(this);
    }

    @Override
    public void viewDetails(DossierProxy dossier) {
        PlaceRequest placeRequest = new PlaceRequest(NameTokens.getInscriptiondetail());
        placeRequest = placeRequest.with("id", dossier.getId().toString());
        placeManager.revealPlace(placeRequest);
    }

    @Override
    public void searchWithFilter(DossierFilterDTOProxy dossierFilter) {
        currentDossierFilter.setFiliere(currentDossierFilter.getFiliere() != null ?
                currentContext.edit(currentDossierFilter.getFiliere()) : null);
        currentDossierFilter.setNiveauEtude(currentDossierFilter.getNiveauEtude() != null ?
                currentContext.edit(currentDossierFilter.getNiveauEtude()) : null);
        currentContext.findAllDossiersByCriteria(dossierFilter).fire(new ReceiverImpl<List<DossierProxy>>() {
            @Override
            public void onSuccess(List<DossierProxy> response) {
                currentContext = requestFactory.dossierService();
                currentDossierFilter = currentContext.edit(currentDossierFilter);

                getView().setData(response);
                getView().editDossierFilter(currentDossierFilter);
            }
        });
    }

    @Override
    public void init() {
        currentContext = requestFactory.dossierService();
        currentDossierFilter = currentContext.create(DossierFilterDTOProxy.class);
        getView().editDossierFilter(currentDossierFilter);
    }

    @Override
    public void export(DossierFilterDTOProxy dossierFilter) {
        ExcelRequestBuilder request = new ExcelRequestBuilder();
        request.sendRequest(dossierFilter);
    }

    @Override
    protected void onReveal() {
        currentContext = requestFactory.dossierService();
        currentDossierFilter = currentContext.create(DossierFilterDTOProxy.class);
        getView().editDossierFilter(currentDossierFilter);
    }
}
