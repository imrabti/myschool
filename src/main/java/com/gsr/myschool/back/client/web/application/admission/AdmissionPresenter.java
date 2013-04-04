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

package com.gsr.myschool.back.client.web.application.admission;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.place.NameTokens;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.back.client.request.DossierRequest;
import com.gsr.myschool.back.client.web.application.ApplicationPresenter;
import com.gsr.myschool.back.client.web.application.admission.event.CloseCompletedEvent;
import com.gsr.myschool.back.client.web.application.admission.popup.CommentAdmissionPresenter;
import com.gsr.myschool.common.client.proxy.DossierFilterDTOProxy;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.client.proxy.PagedDossiersProxy;
import com.gsr.myschool.common.client.request.ExcelRequestBuilder;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;
import com.gsr.myschool.common.client.security.HasRoleGatekeeper;
import com.gsr.myschool.common.shared.constants.GlobalParameters;
import com.gsr.myschool.common.shared.type.DossierStatus;
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

public class AdmissionPresenter extends Presenter<AdmissionPresenter.MyView, AdmissionPresenter.MyProxy>
        implements AdmissionUiHandlers, CloseCompletedEvent.CloseCompletedHandler {
    public interface MyView extends View, HasUiHandlers<AdmissionUiHandlers> {
        void reloadData();

        void setDossierCount(Integer result);

        void displayDossiers(Integer offset, List<DossierProxy> cars);

        void editDossierFilter(DossierFilterDTOProxy dossierFilter);
    }

    @ProxyStandard
    @NameToken(NameTokens.admission)
    @UseGatekeeper(HasRoleGatekeeper.class)
    @GatekeeperParams({GlobalParameters.ROLE_ADMIN, GlobalParameters.ROLE_OPERATOR})
    public interface MyProxy extends ProxyPlace<AdmissionPresenter> {
    }

    private final BackRequestFactory requestFactory;
    private final SharedMessageBundle messageBundle;
    private final PlaceManager placeManager;
    private final CommentAdmissionPresenter commentAdmissionPresenter;

    private DossierRequest currentContext;
    private DossierFilterDTOProxy dossierFilter;

    @Inject
    public AdmissionPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
            final BackRequestFactory requestFactory,
            final PlaceManager placeManager,
            final SharedMessageBundle messageBundle,
            final CommentAdmissionPresenter commentAdmissionPresenter) {
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);

        this.requestFactory = requestFactory;
        this.messageBundle = messageBundle;
        this.placeManager = placeManager;
        this.commentAdmissionPresenter = commentAdmissionPresenter;

        getView().setUiHandlers(this);
    }

    @Override
    public void editStatusComment(DossierProxy currentDossier, Boolean accept) {
        commentAdmissionPresenter.loadDossier(currentDossier, accept);
        addToPopupSlot(commentAdmissionPresenter);
    }

    @Override
    public void viewDetails(DossierProxy dossier) {
        PlaceRequest placeRequest = new PlaceRequest(NameTokens.getInscriptiondetail());
        placeRequest = placeRequest.with("id", dossier.getId().toString());
        placeManager.revealPlace(placeRequest);
    }

    @Override
    public void onCloseCompleted(CloseCompletedEvent event) {
        loadDossiersCounts();
    }

    @Override
    public void fetchData(final Integer offset, Integer limit) {
        Integer pageNumber = (offset / limit) + (offset % limit);
        currentContext.findAllDossiersByCriteria(dossierFilter, pageNumber, limit)
                .fire(new ReceiverImpl<PagedDossiersProxy>() {
            @Override
            public void onSuccess(PagedDossiersProxy result) {
                currentContext = requestFactory.dossierService();
                dossierFilter = currentContext.edit(dossierFilter);

                getView().displayDossiers(offset, result.getDossiers());
                getView().editDossierFilter(dossierFilter);
            }
        });
    }

    @Override
    public void searchWithFilter(DossierFilterDTOProxy filter) {
        dossierFilter.getStatus();
        dossierFilter.setFiliere(dossierFilter.getFiliere() != null ?
                currentContext.edit(dossierFilter.getFiliere()) : null);
        dossierFilter.setNiveauEtude(dossierFilter.getNiveauEtude() != null ?
                currentContext.edit(dossierFilter.getNiveauEtude()) : null);

        loadDossiersCounts();
    }

    @Override
    public void init() {
        currentContext = requestFactory.dossierService();
        dossierFilter = currentContext.create(DossierFilterDTOProxy.class);
        dossierFilter.setStatus(DossierStatus.INVITED_TO_TEST);

        getView().editDossierFilter(dossierFilter);
        loadDossiersCounts();
    }

    @Override
    public void export(DossierFilterDTOProxy dossierFilter) {
        dossierFilter.getStatus();

        ExcelRequestBuilder request = new ExcelRequestBuilder();
        request.sendRequest(dossierFilter);
    }

    @Override
    protected void onBind() {
        addRegisteredHandler(CloseCompletedEvent.getType(), this);
    }

    @Override
    protected void onReveal() {
        currentContext = requestFactory.dossierService();
        dossierFilter = currentContext.create(DossierFilterDTOProxy.class);
        dossierFilter.setStatus(DossierStatus.INVITED_TO_TEST);

        getView().editDossierFilter(dossierFilter);
        loadDossiersCounts();
    }

    private void loadDossiersCounts() {
        currentContext.findAllDossiersByCriteria(dossierFilter, 0, GlobalParameters.PAGE_SIZE)
                .fire(new ReceiverImpl<PagedDossiersProxy>() {
            @Override
            public void onSuccess(PagedDossiersProxy result) {
                currentContext = requestFactory.dossierService();
                dossierFilter = currentContext.edit(dossierFilter);

                getView().setDossierCount(result.getTotalElements());
                getView().editDossierFilter(dossierFilter);
                getView().reloadData();
            }
        });
    }
}
