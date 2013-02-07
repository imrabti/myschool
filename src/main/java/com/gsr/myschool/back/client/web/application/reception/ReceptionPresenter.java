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

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.place.NameTokens;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.back.client.request.DossierServiceRequest;
import com.gsr.myschool.back.client.web.application.ApplicationPresenter;
import com.gsr.myschool.common.client.proxy.DossierFilterDTOProxy;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;
import com.gsr.myschool.common.client.security.LoggedInGatekeeper;
import com.gsr.myschool.common.client.widget.messages.CloseDelay;
import com.gsr.myschool.common.client.widget.messages.Message;
import com.gsr.myschool.common.client.widget.messages.event.MessageEvent;
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

        void editDossierFilter(DossierFilterDTOProxy dossierFilter);
    }

    @ProxyStandard
    @NameToken(NameTokens.reception)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface MyProxy extends ProxyPlace<ReceptionPresenter> {
    }

    private final BackRequestFactory requestFactory;
    private final SharedMessageBundle messageBundle;

    private DossierServiceRequest currentContext;
    private DossierFilterDTOProxy currentDossierFilter;

    @Inject
    public ReceptionPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
                              final BackRequestFactory requestFactory,
                              final SharedMessageBundle messageBundle) {
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);

        this.requestFactory = requestFactory;
        this.messageBundle = messageBundle;

        getView().setUiHandlers(this);
    }

    @Override
    public void receive(DossierProxy dossier) {
        currentContext.receive(dossier).fire(new ReceiverImpl<Boolean>() {
            @Override
            public void onSuccess(Boolean response) {
                String messageString = response ? messageBundle.operationSuccess() : messageBundle.operationFailure();
                AlertType alertType = response ? AlertType.SUCCESS : AlertType.ERROR;
                Message message = new Message.Builder(messageString)
                        .style(alertType)
                        .closeDelay(CloseDelay.DEFAULT)
                        .build();
                MessageEvent.fire(this, message);
            }
        });
    }

    @Override
    public void searchWithFilter(DossierFilterDTOProxy dossierFilter) {
        dossierFilter.setStatus(DossierStatus.SUBMITTED);
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
    protected void onReveal() {
        currentContext = requestFactory.dossierService();
        currentDossierFilter = currentContext.create(DossierFilterDTOProxy.class);
        getView().editDossierFilter(currentDossierFilter);

        searchWithFilter(currentDossierFilter);
    }
}
