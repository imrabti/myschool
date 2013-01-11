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

package com.gsr.myschool.front.client.web.application.home;

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.gsr.myschool.front.client.resource.message.MessageBundle;
import com.gsr.myschool.common.client.security.LoggedInGatekeeper;
import com.gsr.myschool.front.client.web.application.ApplicationPresenter;
import com.gsr.myschool.common.client.mvp.ValidatedView;
import com.gsr.myschool.front.client.place.NameTokens;
import com.gsr.myschool.front.client.request.MyRequestFactory;
import com.gsr.myschool.front.client.request.MyServiceRequest;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.request.ValidatedReceiverImpl;
import com.gsr.myschool.front.client.request.proxy.MyEntityProxy;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gsr.myschool.common.client.widget.messages.CloseDelay;
import com.gsr.myschool.common.client.widget.messages.Message;
import com.gsr.myschool.common.client.widget.messages.event.MessageEvent;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;

public class HomePagePresenter extends Presenter<HomePagePresenter.MyView, HomePagePresenter.MyProxy>
        implements HomeUiHandlers {
    public interface MyView extends ValidatedView, HasUiHandlers<HomeUiHandlers> {
        void editUser(MyEntityProxy myEntity);

        void setData(List<MyEntityProxy> data);
    }

    @ProxyStandard
    @NameToken(NameTokens.home)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface MyProxy extends ProxyPlace<HomePagePresenter> {
    }

    private final MyRequestFactory requestFactory;
    private final MessageBundle messageBundle;

    private MyServiceRequest currentContext;
    private String searchToken;

    @Inject
    public HomePagePresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
                             final MyRequestFactory requestFactory, final MessageBundle messageBundle) {
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);

        this.requestFactory = requestFactory;
        this.messageBundle = messageBundle;

        getView().setUiHandlers(this);
    }

    @Override
    public void saveEntity(MyEntityProxy myEntity) {
        currentContext.create(myEntity).fire(new ValidatedReceiverImpl<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                getView().clearErrors();
                loadEntities();
                initializeContext();

                Message message = new Message.Builder(messageBundle.myEntitySaveSucess())
                        .style(AlertType.SUCCESS)
                        .closeDelay(CloseDelay.DEFAULT)
                        .build();
                MessageEvent.fire(this, message);
            }

            @Override
            public void onValidationError(Set<ConstraintViolation<?>> violations) {
                getView().clearErrors();
                getView().showErrors(violations);
            }
        });
    }

    @Override
    protected void onReveal() {
        searchToken = "";
        initializeContext();
        loadEntities();
    }

    private void initializeContext() {
        currentContext = requestFactory.myService();
        MyEntityProxy newEntity = currentContext.create(MyEntityProxy.class);
        getView().editUser(newEntity);
    }

    private void loadEntities() {
        requestFactory.myService().loadAll(searchToken).fire(new ReceiverImpl<List<MyEntityProxy>>() {
            @Override
            public void onSuccess(List<MyEntityProxy> data) {
                getView().setData(data);
            }
        });
    }
}
