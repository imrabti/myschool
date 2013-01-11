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

package com.gsr.myschool.client.web.widget.message;

import com.arcbees.core.client.mvp.ViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import java.util.Iterator;

public class MessageView extends ViewImpl implements MessagePresenter.MyView {
    public interface Binder extends UiBinder<Widget, MessageView> {
    }

    @UiField
    HTMLPanel messages;

    private final MessageWidgetFactory messageWidgetFactory;

    @Inject
    public MessageView(final Binder binder, final MessageWidgetFactory messageWidgetFactory) {
        this.messageWidgetFactory = messageWidgetFactory;

        initWidget(binder.createAndBindUi(this));
    }

    @Override
    public void addMessage(Message message) {
        removeMessage(message);
        MessageWidget messageWidget = messageWidgetFactory.createMessage(message);
        messages.add(messageWidget);
    }

    @Override
    public void removeMessage(Message message) {
        Iterator<Widget> iterator = messages.iterator();
        while (iterator.hasNext()) {
            MessageWidget messageWidget = (MessageWidget) iterator.next();
            if (messageWidget.getMessage().equals(message)) {
                messageWidget.removeFromParent();
            }
        }
    }

    @Override
    public void clear() {
        messages.clear();
    }
}
