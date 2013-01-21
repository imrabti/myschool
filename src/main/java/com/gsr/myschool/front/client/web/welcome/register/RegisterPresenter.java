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

package com.gsr.myschool.front.client.web.welcome.register;

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.mvp.ValidatedView;
import com.gsr.myschool.common.client.proxy.UserProxy;
import com.gsr.myschool.common.client.request.ValidatedReceiverImpl;
import com.gsr.myschool.common.client.widget.messages.CloseDelay;
import com.gsr.myschool.common.client.widget.messages.Message;
import com.gsr.myschool.common.client.widget.messages.event.MessageEvent;
import com.gsr.myschool.front.client.place.NameTokens;
import com.gsr.myschool.front.client.request.FrontRequestFactory;
import com.gsr.myschool.front.client.request.RegistrationRequest;
import com.gsr.myschool.front.client.resource.message.MessageBundle;
import com.gsr.myschool.front.client.web.RootPresenter;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

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

    private final FrontRequestFactory requestFactory;
    private final PlaceManager placeManager;
    private final MessageBundle messageBundle;

    private RegistrationRequest currentContext;

    @Inject
    public RegisterPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
                             final FrontRequestFactory requestFactory, final MessageBundle messageBundle, final PlaceManager placeManager) {
        super(eventBus, view, proxy, RootPresenter.TYPE_SetMainContent);

        this.messageBundle = messageBundle;
        this.requestFactory = requestFactory;
        this.placeManager = placeManager;

        getView().setUiHandlers(this);
    }

    @Override
    public void register(UserProxy user) {
        currentContext.register(user, Window.Location.getHost()).fire(new ValidatedReceiverImpl<Boolean>() {
            @Override
            public void onValidationError(Set<ConstraintViolation<?>> violations) {
                getView().clearErrors();
                getView().showErrors(violations);
            }

            @Override
            public void onSuccess(Boolean aBoolean) {
                getView().clearErrors();
                Message message = new Message.Builder(messageBundle.registerSucces()).style(AlertType.SUCCESS).closeDelay(CloseDelay.NEVER).build();
                MessageEvent.fire(this, message);
                placeManager.revealPlace(new PlaceRequest(NameTokens.getLogin()));
            }
        });
    }

    protected void onReveal() {
        currentContext = requestFactory.registrationService();
        getView().edit(currentContext.create(UserProxy.class));
    }
}
