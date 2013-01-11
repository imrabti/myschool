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

package com.gsr.myschool.client.web.welcome.register;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gsr.myschool.client.mvp.ValidatedView;
import com.gsr.myschool.client.place.NameTokens;
import com.gsr.myschool.client.request.MyRequestFactory;
import com.gsr.myschool.client.request.RegistrationRequest;
import com.gsr.myschool.client.request.ValidatedReceiverImpl;
import com.gsr.myschool.client.request.proxy.UserProxy;
import com.gsr.myschool.client.web.RootPresenter;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class RegisterPresenter extends Presenter<RegisterPresenter.MyView, RegisterPresenter.MyProxy>
        implements RegisterUiHandlers {
    public interface MyView extends ValidatedView, HasUiHandlers<RegisterUiHandlers> {
        void edit(UserProxy userProxy);
    }

    @ProxyStandard
    @NameToken(NameTokens.register)
    public interface MyProxy extends ProxyPlace<RegisterPresenter> {
    }

    private final MyRequestFactory requestFactory;
    private final PlaceManager placeManager;

    private RegistrationRequest currentContext;

    @Inject
    public RegisterPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
                             final MyRequestFactory requestFactory, final PlaceManager placeManager) {
        super(eventBus, view, proxy, RootPresenter.TYPE_SetMainContent);

        this.requestFactory = requestFactory;
        this.placeManager = placeManager;

        getView().setUiHandlers(this);
    }

    @Override
    public void register(UserProxy user) {
        currentContext.register(user).fire(new ValidatedReceiverImpl<Void>() {
            @Override
            public void onValidationError(Set<ConstraintViolation<?>> violations) {
                getView().clearErrors();
                getView().showErrors(violations);
            }

            @Override
            public void onSuccess(Void aVoid) {
                getView().clearErrors();
                placeManager.revealPlace(new PlaceRequest(NameTokens.getLogin()));
            }
        });
    }

    protected void onReveal() {
        currentContext = requestFactory.registrationService();
        getView().edit(currentContext.create(UserProxy.class));
    }
}
