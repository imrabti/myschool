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

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.request.ReportRequestBuilder;
import com.gsr.myschool.common.client.security.LoggedInGatekeeper;
import com.gsr.myschool.common.client.widget.messages.CloseDelay;
import com.gsr.myschool.common.client.widget.messages.Message;
import com.gsr.myschool.common.client.widget.messages.event.MessageEvent;
import com.gsr.myschool.front.client.place.NameTokens;
import com.gsr.myschool.front.client.request.FrontRequestFactory;
import com.gsr.myschool.front.client.resource.message.MessageBundle;
import com.gsr.myschool.front.client.web.application.ApplicationPresenter;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import java.util.List;

public class InscriptionPresenter extends Presenter<InscriptionPresenter.MyView, InscriptionPresenter.MyProxy>
        implements InscriptionUiHandlers {
    public interface MyView extends View, HasUiHandlers<InscriptionUiHandlers> {
        void setData(List<DossierProxy> data);

        void showErrors(List<String> errors);

        void clearErrors();
    }

    @ProxyStandard
    @NameToken(NameTokens.inscription)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface MyProxy extends ProxyPlace<InscriptionPresenter> {
    }

    private final FrontRequestFactory requestFactory;
    private final PlaceManager placeManager;
    private final MessageBundle messageBundle;

    @Inject
    public InscriptionPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
                                final FrontRequestFactory requestFactory,
                                final PlaceManager placeManager,
                                final MessageBundle messageBundle) {
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);

        this.requestFactory = requestFactory;
        this.placeManager = placeManager;
        this.messageBundle = messageBundle;

        getView().setUiHandlers(this);
    }

    @Override
    public void addNewInscription() {
        requestFactory.inscriptionService().createNewInscription().fire(new ReceiverImpl<DossierProxy>() {
            @Override
            public void onSuccess(DossierProxy newDossier) {
                PlaceRequest placeRequest = new PlaceRequest(NameTokens.getEditInscription());
                placeRequest = placeRequest.with("id", newDossier.getId().toString());
                placeManager.revealPlace(placeRequest);
            }
        });
    }

    @Override
    public void previewInscription(DossierProxy dossier) {
        PlaceRequest placeRequest = new PlaceRequest(NameTokens.getInscriptionDetail());
        placeRequest = placeRequest.with("id", dossier.getId().toString());
        placeManager.revealPlace(placeRequest);
    }

    @Override
    public void editInscription(DossierProxy dossier) {
        PlaceRequest placeRequest = new PlaceRequest(NameTokens.getEditInscription());
        placeRequest = placeRequest.with("id", dossier.getId().toString());
        placeManager.revealPlace(placeRequest);
    }

    @Override
    public void deleteInscription(DossierProxy dossier) {
        if (Window.confirm(messageBundle.deleteInscriptionConf())) {
            requestFactory.inscriptionService().deleteInscription(dossier.getId()).fire(new ReceiverImpl<Void>() {
                @Override
                public void onSuccess(Void response) {
                    Message message = new Message.Builder(messageBundle.deleteInscriptionSuccess())
                            .style(AlertType.SUCCESS)
                            .closeDelay(CloseDelay.DEFAULT)
                            .build();
                    MessageEvent.fire(this, message);
                    loadAllInscriptions();
                }
            });
        }
    }

    @Override
    public void submitInscription(DossierProxy dossier) {
        if (Window.confirm(messageBundle.inscriptionSubmitConf())) {
            Long dossierId = dossier.getId();
            requestFactory.inscriptionService().submitInscription(dossierId).fire(new ReceiverImpl<List<String>>() {
                @Override
                public void onSuccess(List<String> response) {
                    if (response.isEmpty()) {
                        Message message = new Message.Builder(messageBundle.inscriptionSubmitSuccess())
                                .style(AlertType.SUCCESS)
                                .closeDelay(CloseDelay.DEFAULT)
                                .build();
                        MessageEvent.fire(this, message);
                        loadAllInscriptions();
                    } else {
                        getView().showErrors(response);
                    }
                }
            });
        }
    }

    @Override
    public void printInscription(DossierProxy dossier) {
        printDossier(dossier);
    }

    @Override
    protected void onReveal() {
        loadAllInscriptions();
        getView().clearErrors();
    }

    private void printDossier(DossierProxy dossier) {
        ReportRequestBuilder requestBuilder = new ReportRequestBuilder();
        requestBuilder.buildData(dossier.getId().toString());
        requestBuilder.sendRequest();
    }


    private void loadAllInscriptions() {
        requestFactory.inscriptionService().findAllDossiers().fire(new ReceiverImpl<List<DossierProxy>>() {
            @Override
            public void onSuccess(List<DossierProxy> result) {
                getView().setData(result);
            }
        });
    }
}
