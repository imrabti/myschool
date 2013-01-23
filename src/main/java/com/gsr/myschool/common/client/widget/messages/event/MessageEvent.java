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

package com.gsr.myschool.common.client.widget.messages.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.gsr.myschool.common.client.widget.messages.Message;

public class MessageEvent extends GwtEvent<MessageEvent.MessageHandler> {
    public interface MessageHandler extends EventHandler {
        public void onMessage(MessageEvent event);
    }

    private static final Type<MessageHandler> TYPE = new Type<MessageHandler>();

    private final Message message;

    public static Type<MessageHandler> getType() {
        return TYPE;
    }

    public static void fire(Receiver<Void> source, Message message) {
        source.fireEvent(new MessageEvent(message));
    }

    public MessageEvent(Message message) {
        this.message = message;
    }

    @Override
    public Type<MessageHandler> getAssociatedType() {
        return TYPE;
    }

    public Message getMessage() {
        return message;
    }

    @Override
    protected void dispatch(MessageHandler handler) {
        handler.onMessage(this);
    }
}
