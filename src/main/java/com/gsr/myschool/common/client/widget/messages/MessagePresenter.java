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

package com.gsr.myschool.common.client.widget.messages;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.widget.messages.event.CloseMessageEvent;
import com.gsr.myschool.common.client.widget.messages.event.MessageEvent;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class MessagePresenter extends PresenterWidget<MessagePresenter.MyView> implements MessageEvent.MessageHandler,
        CloseMessageEvent.MessageCloseHandler {
    public interface MyView extends View {
        void addMessage(Message message);

        void removeMessage(Message message);

        void clear();
    }

    @Inject
    public MessagePresenter(final EventBus eventBus, final MyView view) {
        super(eventBus, view);
    }

    @Override
    public void onMessage(MessageEvent event) {
        getView().addMessage(event.getMessage());
    }

    @Override
    public void onCloseMessage(CloseMessageEvent event) {
        getView().removeMessage(event.getMessage());
    }

    @Override
    protected void onBind() {
        addRegisteredHandler(MessageEvent.getType(), this);
        addRegisteredHandler(CloseMessageEvent.getType(), this);
    }
}
