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

package com.gsr.myschool.front.client.web.application.inbox.popup;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.mvp.ValidatedPopupView;
import com.gsr.myschool.common.client.proxy.InboxProxy;
import com.gsr.myschool.common.shared.type.InboxMessageStatus;
import com.gsr.myschool.front.client.request.FrontRequestFactory;
import com.gsr.myschool.front.client.resource.message.MessageBundle;
import com.gsr.myschool.front.client.web.application.inbox.event.InboxStatusChangedEvent;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;

public class InboxDetailsPresenter extends PresenterWidget<InboxDetailsPresenter.MyView>
        implements InboxDetailsUiHandlers {
    public interface MyView extends ValidatedPopupView, HasUiHandlers<InboxDetailsUiHandlers> {
        void setDatas(InboxProxy value);
    }

    private InboxProxy currentMessage;

    @Inject
    public InboxDetailsPresenter(final EventBus eventBus, final MyView view,
                                 final FrontRequestFactory requestFactory,
                                 final MessageBundle messageBundle) {
        super(eventBus, view);

        getView().setUiHandlers(this);
    }

    public void setCurrentMessage(InboxProxy value){
        this.currentMessage = value;
        getView().setDatas(value);
    }

    @Override
    public void readComplete(){
        InboxStatusChangedEvent.fire(this, currentMessage);
    }
}
