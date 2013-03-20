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

package com.gsr.myschool.back.client.web.application.validation;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.place.NameTokens;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.back.client.request.DossierServiceRequest;
import com.gsr.myschool.back.client.web.application.ApplicationPresenter;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidationPresenter extends Presenter<ValidationPresenter.MyView, ValidationPresenter.MyProxy>
        implements ValidationUiHandlers {
    public interface MyView extends View, HasUiHandlers<ValidationUiHandlers> {
        void reloadData();

        void setDossierCount(Integer result);

        void displayDossiers(Integer offset, List<DossierProxy> cars);

        void editDossierFilter(DossierFilterDTOProxy dossierFilter);
    }

    @ProxyStandard
    @NameToken(NameTokens.validation)
    @UseGatekeeper(HasRoleGatekeeper.class)
    @GatekeeperParams({GlobalParameters.ROLE_ADMIN, GlobalParameters.ROLE_OPERATOR})
    public interface MyProxy extends ProxyPlace<ValidationPresenter> {
    }

    private final BackRequestFactory requestFactory;
    private final SharedMessageBundle messageBundle;
    private final PlaceManager placeManager;

    private DossierServiceRequest currentContext;
    private DossierFilterDTOProxy dossierFilter;
    private Map<Long, Integer> dossierPiecesMap;

    @Inject
    public ValidationPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
            final BackRequestFactory requestFactory,
            final PlaceManager placeManager,
            final SharedMessageBundle messageBundle) {
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);

        this.requestFactory = requestFactory;
        this.messageBundle = messageBundle;
        this.placeManager = placeManager;
        this.dossierPiecesMap = new HashMap<Long, Integer>();

        getView().setUiHandlers(this);
    }

    @Override
    public void viewDetails(DossierProxy dossier) {
        PlaceRequest placeRequest = new PlaceRequest(NameTokens.getInscriptiondetail());
        placeRequest = placeRequest.with("id", dossier.getId().toString());
        placeManager.revealPlace(placeRequest);
    }

    @Override
    public void verify(DossierProxy dossier) {
        // TODO: Open the verification popup for applicant folder
    }

    @Override
    public void confirmVerify(DossierProxy dossier) {
        // TODO: call the validation service
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
        dossierFilter.setStatus(DossierStatus.RECEIVED);
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
        dossierFilter.setStatus(DossierStatus.RECEIVED);

        getView().editDossierFilter(dossierFilter);
        loadDossiersCounts();
    }

    @Override
    public void export(DossierFilterDTOProxy dossierFilter) {
        dossierFilter.setStatus(DossierStatus.RECEIVED);

        ExcelRequestBuilder request = new ExcelRequestBuilder();
        request.sendRequest(dossierFilter);
    }

    @Override
    public String getCurrentRatio(DossierProxy dossier) {
        String verified = "0/"; // TODO evaluate this using process
        return verified + dossierPiecesMap.get(dossier.getId()).toString();
    }

    @Override
    protected void onReveal() {
        currentContext = requestFactory.dossierService();
        dossierFilter = currentContext.create(DossierFilterDTOProxy.class);
        dossierFilter.setStatus(DossierStatus.RECEIVED);

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

                        for (DossierProxy dossierProxy : result.getDossiers()) {
                            loadPieces(dossierProxy);
                        }

                        getView().setDossierCount(result.getTotalElements());
                        getView().editDossierFilter(dossierFilter);
                        getView().reloadData();
                    }
                });
    }

    private void loadPieces(final DossierProxy dossier) {
        dossierPiecesMap.clear();
        requestFactory.dossierService().findPiecesByNiveauEtude(dossier.getNiveauEtude().getId())
                .fire(new ReceiverImpl<Integer>() {
                    @Override
                    public void onSuccess(Integer response) {
                        dossierPiecesMap.put(dossier.getId(), response);
                    }
                });
    }
}
