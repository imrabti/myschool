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

import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.front.client.request.FrontRequestFactory;
import com.gsr.myschool.common.client.security.LoggedInGatekeeper;
import com.gsr.myschool.front.client.web.application.ApplicationPresenter;
import com.gsr.myschool.front.client.place.NameTokens;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
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
    }

    @ProxyStandard
    @NameToken(NameTokens.inscription)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface MyProxy extends ProxyPlace<InscriptionPresenter> {
    }

    private final FrontRequestFactory requestFactory;
    private final PlaceManager placeManager;

    @Inject
    public InscriptionPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
                                final FrontRequestFactory requestFactory,
                                final PlaceManager placeManager) {
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);

        this.requestFactory = requestFactory;
        this.placeManager = placeManager;

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
    }

    @Override
    public void editInscription(DossierProxy dossier) {
        PlaceRequest placeRequest = new PlaceRequest(NameTokens.getEditInscription());
        placeRequest = placeRequest.with("id", dossier.getId().toString());
        placeManager.revealPlace(placeRequest);
    }

    @Override
    public void deleteInscription(DossierProxy dossier) {
    }

    @Override
    public void submitInscription(DossierProxy dossier) {
    }

    @Override
    public void printInscription(DossierProxy dossier) {
    }

    @Override
    protected void onReveal() {
        requestFactory.inscriptionService().findAllDossiers().fire(new ReceiverImpl<List<DossierProxy>>() {
            @Override
            public void onSuccess(List<DossierProxy> result) {
                getView().setData(result);
            }
        });
    }
}
