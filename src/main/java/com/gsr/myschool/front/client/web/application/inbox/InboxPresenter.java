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

package com.gsr.myschool.front.client.web.application.inbox;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.proxy.InboxProxy;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.request.ValidatedReceiverImpl;
import com.gsr.myschool.common.client.security.LoggedInGatekeeper;
import com.gsr.myschool.common.shared.type.InboxMessageStatus;
import com.gsr.myschool.front.client.place.NameTokens;
import com.gsr.myschool.front.client.request.FrontRequestFactory;
import com.gsr.myschool.front.client.security.CurrentUserProvider;
import com.gsr.myschool.front.client.web.application.ApplicationPresenter;
import com.gsr.myschool.front.client.web.application.inbox.event.InboxStatusChangedEvent;
import com.gsr.myschool.front.client.web.application.inbox.popup.InboxDetailsPresenter;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;

public class InboxPresenter extends Presenter<InboxPresenter.MyView, InboxPresenter.MyProxy>
        implements InboxUiHandlers {
    public interface MyView extends View, HasUiHandlers<InboxUiHandlers> {
        public void setData(List<InboxProxy> response);
    }

    private final FrontRequestFactory requestFactory;
    private final CurrentUserProvider currentUserProvider;
    private final InboxDetailsPresenter inboxDetailsPresenter;

    @ProxyStandard
    @NameToken(NameTokens.inbox)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface MyProxy extends ProxyPlace<InboxPresenter> {
    }

    @Inject
    public InboxPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
                          final FrontRequestFactory requestFactory,
                          final CurrentUserProvider currentUserProvider,
                          final InboxDetailsPresenter inboxDetailsPresenter) {
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);

        this.requestFactory = requestFactory;
        this.currentUserProvider = currentUserProvider;
        this.inboxDetailsPresenter = inboxDetailsPresenter;

        getView().setUiHandlers(this);
    }

    @Override
    public void delete(List<InboxProxy> toDelete) {
        requestFactory.inboxService().deleteInboxMessages(toDelete).fire(new ValidatedReceiverImpl<Void>() {
            @Override
            public void onValidationError(Set<ConstraintViolation<?>> violations) {
            }

            @Override
            public void onSuccess(Void aVoid) {
                fillCellList();
                fireEvent(new InboxStatusChangedEvent());
            }
        });
    }

    @Override
    public void update(List<InboxProxy> toUpdate, InboxMessageStatus status) {
        requestFactory.inboxService().updateInboxMessages(toUpdate, status).fire(new ValidatedReceiverImpl<Void>() {
            @Override
            public void onValidationError(Set<ConstraintViolation<?>> violations) {
            }
            @Override
            public void onSuccess(Void aVoid) {
                fillCellList();
                fireEvent(new InboxStatusChangedEvent());
            }
        });
    }

    public void showDetails(InboxProxy value) {
        inboxDetailsPresenter.setCurrentMessage(value);
        addToPopupSlot(inboxDetailsPresenter);
    }

    @Override
    protected void onReveal(){
        fillCellList();
    }

    private void fillCellList(){
        requestFactory.inboxService().findAllInboxMessage(currentUserProvider.get().getId()).fire(new ReceiverImpl<List<InboxProxy>>() {
            @Override
            public void onSuccess(List<InboxProxy> inboxProxies) {
                getView().setData(inboxProxies);
            }
        });
    }
}
