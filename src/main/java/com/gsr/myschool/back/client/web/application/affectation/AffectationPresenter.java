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

package com.gsr.myschool.back.client.web.application.affectation;

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.gsr.myschool.back.client.place.NameTokens;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.back.client.request.DossierRequest;
import com.gsr.myschool.back.client.web.application.ApplicationPresenter;
import com.gsr.myschool.back.client.web.application.affectation.event.DossierAffectedEvent;
import com.gsr.myschool.back.client.web.application.affectation.popup.SessionAffectationPresenter;
import com.gsr.myschool.common.client.proxy.DossierFilterDTOProxy;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.client.proxy.DossierSessionProxy;
import com.gsr.myschool.common.client.proxy.PagedDossiersProxy;
import com.gsr.myschool.common.client.request.ConvocationRequestBuilder;
import com.gsr.myschool.common.client.request.ExcelRequestBuilder;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;
import com.gsr.myschool.common.client.security.HasRoleGatekeeper;
import com.gsr.myschool.common.client.widget.messages.CloseDelay;
import com.gsr.myschool.common.client.widget.messages.Message;
import com.gsr.myschool.common.client.widget.messages.event.MessageEvent;
import com.gsr.myschool.common.shared.constants.GlobalParameters;
import com.gsr.myschool.common.shared.exception.AffectationClosedException;
import com.gsr.myschool.common.shared.exception.InscriptionClosedException;
import com.gsr.myschool.common.shared.type.DossierStatus;
import com.gsr.myschool.common.shared.type.SessionStatus;
import com.gsr.myschool.server.business.DossierSession;
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

public class AffectationPresenter extends Presenter<AffectationPresenter.MyView, AffectationPresenter.MyProxy>
        implements AffectationUiHandlers, DossierAffectedEvent.DossierAffectedHandler {
    public interface MyView extends View, HasUiHandlers<AffectationUiHandlers> {
        void reloadData();

        void setDossierCount(Integer result);

        void displayDossiers(Integer offset, List<DossierProxy> cars);

        void editDossierFilter(DossierFilterDTOProxy dossierFilter);
    }

    @ProxyStandard
    @NameToken(NameTokens.affectation)
    @UseGatekeeper(HasRoleGatekeeper.class)
    @GatekeeperParams({GlobalParameters.ROLE_ADMIN})
    public interface MyProxy extends ProxyPlace<AffectationPresenter> {
    }

    private final BackRequestFactory requestFactory;
    private final PlaceManager placeManager;
    private final SessionAffectationPresenter sessionAffectationPresenter;
    private final SharedMessageBundle messageBundle;

    private DossierRequest currentContext;
    private DossierFilterDTOProxy dossierFilter;

    @Inject
    public AffectationPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
                                final BackRequestFactory requestFactory,
                                final SessionAffectationPresenter sessionAffectationPresenter,
                                final PlaceManager placeManager,
                                final SharedMessageBundle messageBundle) {
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);

        this.requestFactory = requestFactory;
        this.placeManager = placeManager;
        this.sessionAffectationPresenter = sessionAffectationPresenter;
        this.messageBundle = messageBundle;

        getView().setUiHandlers(this);
    }

    @Override
    public void viewDetails(DossierProxy dossier) {
        PlaceRequest placeRequest = new PlaceRequest(NameTokens.getInscriptiondetail());
        placeRequest = placeRequest.with("id", dossier.getId().toString());
        placeManager.revealPlace(placeRequest);
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
    public void searchWithFilter(DossierFilterDTOProxy filer) {
        dossierFilter.setFiliere(dossierFilter.getFiliere() != null ?
                currentContext.edit(dossierFilter.getFiliere()) : null);
        dossierFilter.setNiveauEtude(dossierFilter.getNiveauEtude() != null ?
                currentContext.edit(dossierFilter.getNiveauEtude()) : null);
        dossierFilter.setSession(dossierFilter.getSession() != null ?
                currentContext.edit(dossierFilter.getSession()) : null);

        loadDossiersCounts();
    }

    @Override
    public void affecter(DossierProxy dossier) {
        sessionAffectationPresenter.setCurrentDossier(dossier);
        addToPopupSlot(sessionAffectationPresenter);
    }

    @Override
    public void desaffecter(DossierProxy inscription) {
        requestFactory.sessionService().desaffecter(inscription).fire(new ReceiverImpl<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                String content = aBoolean ? messageBundle.operationSuccess() : messageBundle.operationFailure();
                AlertType alertType = aBoolean ? AlertType.SUCCESS : AlertType.ERROR;
                Message message = new Message.Builder(content)
                        .style(alertType).build();

                MessageEvent.fire(this, message);
                loadDossiersCounts();
            }

            @Override
            public void onException(String type) {
                if (AffectationClosedException.class.getName().equals(type)) {
                    Message message = new Message.Builder(messageBundle.affectationClosed())
                            .style(AlertType.WARNING).closeDelay(CloseDelay.NEVER).build();
                    MessageEvent.fire(this, message);
                }
            }
        });
    }

    @Override
    public void imprimer(DossierProxy dossier) {
        final ConvocationRequestBuilder request = new ConvocationRequestBuilder();
        requestFactory.sessionService().findByDossier(dossier).fire(new Receiver<DossierSessionProxy>() {
            @Override
            public void onSuccess(DossierSessionProxy dossierSession) {
                if (!Strings.isNullOrEmpty(dossierSession.getGeneratedConvocationPDFPath())) {
                    request.buildData(dossierSession.getGeneratedConvocationPDFPath());
                    request.sendRequest();
                }
            }
        });
    }

    @Override
    public void init() {
        currentContext = requestFactory.dossierService();
        dossierFilter = currentContext.create(DossierFilterDTOProxy.class);
        dossierFilter.setStatusList(DossierStatus.affectationValues());

        getView().editDossierFilter(dossierFilter);
        loadDossiersCounts();
    }

    @Override
    public void export(DossierFilterDTOProxy dossierFilter) {
        ExcelRequestBuilder request = new ExcelRequestBuilder();
        request.sendRequest(dossierFilter);
    }

    @Override
    public void onDossierAffected(DossierAffectedEvent event) {
        loadDossiersCounts();
    }

    @Override
    protected void onBind() {
        addRegisteredHandler(DossierAffectedEvent.TYPE, this);
    }

    @Override
    protected void onReveal() {
        currentContext = requestFactory.dossierService();
        dossierFilter = currentContext.create(DossierFilterDTOProxy.class);
        dossierFilter.setStatusList(DossierStatus.affectationValues());

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
