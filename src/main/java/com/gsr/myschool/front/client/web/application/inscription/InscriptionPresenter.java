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

import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.security.CurrentUserProvider;
import com.gsr.myschool.common.client.security.LoggedInGatekeeper;
import com.gsr.myschool.front.client.request.MyRequestFactory;
import com.gsr.myschool.front.client.request.proxy.InscriptionProxy;
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
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import java.util.List;

public class InscriptionPresenter extends Presenter<InscriptionPresenter.MyView, InscriptionPresenter.MyProxy>
        implements InscriptionUiHandlers {
    public interface MyView extends View, HasUiHandlers<InscriptionUiHandlers> {
        void setData(List<InscriptionProxy> data);
    }

    @ProxyStandard
    @NameToken(NameTokens.inscription)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface MyProxy extends ProxyPlace<InscriptionPresenter> {
    }

    private final CurrentUserProvider currentUserProvider;
    private final MyRequestFactory requestFactory;

    @Inject
    public InscriptionPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
                                final MyRequestFactory requestFactory,
                                final CurrentUserProvider currentUserProvider) {
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);

        this.requestFactory = requestFactory;
        this.currentUserProvider = currentUserProvider;

        getView().setUiHandlers(this);
    }

    @Override
    public void previewInscription(InscriptionProxy inscription) {
    }

    @Override
    public void editInscription(InscriptionProxy inscription) {
    }

    @Override
    public void deleteInscription(InscriptionProxy inscription) {
    }

    @Override
    public void submitInscription(InscriptionProxy inscription) {
    }

    @Override
    public void printInscription(InscriptionProxy inscription) {
    }

    @Override
    protected void onReveal() {
        Long userId = currentUserProvider.get().getId();
        requestFactory.inscriptionService().findAllInscriptionsByUser(userId)
                .fire(new ReceiverImpl<List<InscriptionProxy>>() {
            @Override
            public void onSuccess(List<InscriptionProxy> result) {
                getView().setData(result);
            }
        });
    }
}
