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

package com.gsr.myschool.back.client.web.application.reception;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.gsr.myschool.back.client.place.NameTokens;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.back.client.request.DossierServiceRequest;
import com.gsr.myschool.back.client.web.application.ApplicationPresenter;
import com.gsr.myschool.common.client.proxy.DossierFilterProxy;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.security.LoggedInGatekeeper;
import com.gsr.myschool.common.client.widget.messages.MessagePresenter;
import com.gsr.myschool.common.shared.type.DossierStatus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import java.util.List;

public class ReceptionPresenter extends Presenter<ReceptionPresenter.MyView, ReceptionPresenter.MyProxy>
        implements ReceptionUiHandlers {
    public interface MyView extends View, HasUiHandlers<ReceptionUiHandlers> {
        void setData(List<DossierProxy> data);
    }

    @ProxyStandard
    @NameToken(NameTokens.reception)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface MyProxy extends ProxyPlace<ReceptionPresenter> {
    }

    private final BackRequestFactory requestFactory;
    private final MessagePresenter messagePresenter;

    private String numDossierFilter ;
    private String candidatFilter;

    @Inject
    public ReceptionPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
            final BackRequestFactory requestFactory, final MessagePresenter messagePresenter) {
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);

        this.requestFactory = requestFactory;
        this.messagePresenter = messagePresenter;

        getView().setUiHandlers(this);
    }

    @Override
    public void receive(DossierProxy dossier) {
        requestFactory.dossierService().receive(dossier).fire(new Receiver<Boolean>() {
            @Override
            public void onSuccess(Boolean response) {
                messagePresenter.alertCrudOperationResponse(response);
                loadDossiers();
            }
        });
    }

    @Override
    public void searchWithFilter(String numDossier, String nomCandidat) {
        numDossierFilter = numDossier;
        candidatFilter = nomCandidat;
        loadDossiers();
    }

    @Override
    protected void onReveal() {
        numDossierFilter = "%";
        candidatFilter = "%";
        loadDossiers();
    }

    private void loadDossiers() {
        DossierServiceRequest currentContext = requestFactory.dossierService();
        DossierFilterProxy filter = currentContext.create(DossierFilterProxy.class);
        filter.setNumDossier(numDossierFilter);
        filter.setNomCandidat(candidatFilter);
        filter.setStatus(DossierStatus.SUBMITTED);

        currentContext.findAllDossiersInStatusByCriteria(filter).fire(new ReceiverImpl<List<DossierProxy>>() {
            @Override
            public void onSuccess(List<DossierProxy> result) {
                getView().setData(result);
            }
        });
    }
}
