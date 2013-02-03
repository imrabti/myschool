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

import com.google.gwt.view.client.HasData;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.place.NameTokens;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.back.client.request.DossierServiceRequest;
import com.gsr.myschool.back.client.security.CurrentUserProvider;
import com.gsr.myschool.back.client.web.application.ApplicationPresenter;
import com.gsr.myschool.back.client.web.application.preinscription.popup.PreInscriptionDetailsPresenter;
import com.gsr.myschool.common.client.proxy.DataPageProxy;
import com.gsr.myschool.common.client.proxy.DossierFilterProxy;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.client.proxy.PagedDossiersProxy;
import com.gsr.myschool.common.client.request.ReceiverImpl;
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
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import java.util.Date;
import java.util.List;

public class PreInscriptionPresenter extends Presenter<PreInscriptionPresenter.MyView, PreInscriptionPresenter.MyProxy>
        implements PreInscriptionUiHandlers {
    public interface MyView extends View, HasUiHandlers<PreInscriptionUiHandlers> {
        void setData(List<DossierProxy> data, Integer start, Integer rowCount);

        HasData<DossierProxy> getInscriptionsDisplay();

        void initDataProvider();
    }

    @ProxyStandard
    @NameToken(NameTokens.preInscriptions)
    @UseGatekeeper(HasRoleGatekeeper.class)
    @GatekeeperParams("ROLE_ADMIN")
    public interface MyProxy extends ProxyPlace<PreInscriptionPresenter> {
    }

    private final CurrentUserProvider currentUserProvider;
    private final BackRequestFactory requestFactory;
    private final PreInscriptionDetailsPresenter detailsPresenter;

    private Integer paginationStart;
    private String numDossierFilter;
    private Date creationDateFilter;
    private DossierStatus statusFilter;

    @Inject
    public PreInscriptionPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
            final BackRequestFactory requestFactory,
            final CurrentUserProvider currentUserProvider, final PreInscriptionDetailsPresenter detailsPresenter) {
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);

        this.requestFactory = requestFactory;
        this.currentUserProvider = currentUserProvider;
        this.detailsPresenter = detailsPresenter;

        getView().setUiHandlers(this);
    }

    @Override
    public void viewDetails(DossierProxy dossier) {
        detailsPresenter.editDrivers(dossier);
        addToPopupSlot(detailsPresenter);
    }

    @Override
    public void searchWithFilter(String numDossier, DossierStatus dossierStatus, Date dateCreation) {
        numDossierFilter = numDossier;
        statusFilter = dossierStatus;
        creationDateFilter = dateCreation;
        loadDossiers(GlobalParameters.defaultPageNumber, GlobalParameters.defaultPageLength);
    }

    @Override
    public void loadDossiers(Integer start, Integer length) {
        paginationStart = start;
        Integer pageNumber = (start / length) + (start % length);
        fireLoadDossierDataRequest(pageNumber, length);
    }

	@Override
    protected void onReveal() {
        getView().initDataProvider();
        paginationStart = 0;
        numDossierFilter = "%";
        loadDossiers(GlobalParameters.defaultPageNumber, GlobalParameters.defaultPageLength);
    }

    private void fireLoadDossierDataRequest(final Integer start, Integer length) {
        DossierServiceRequest currentContext = requestFactory.dossierService();
        DataPageProxy page = currentContext.create(DataPageProxy.class);
        page.setPageNumber(start);
        page.setLength(length);

        DossierFilterProxy filter = currentContext.create(DossierFilterProxy.class);
        filter.setNumDossier(numDossierFilter);
        filter.setStatus(statusFilter);
        filter.setDateCreation(creationDateFilter);

        currentContext.findAllDossiersByCriteria(filter, page).fire(new ReceiverImpl<PagedDossiersProxy>() {
            @Override
            public void onSuccess(PagedDossiersProxy result) {
                getView().setData(result.getDossiers(), start, result.getTotalElements());
            }
        });
    }
}
