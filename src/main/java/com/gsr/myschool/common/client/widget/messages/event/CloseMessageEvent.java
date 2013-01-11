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
import com.gsr.myschool.common.client.widget.messages.Message;

public class CloseMessageEvent extends GwtEvent<CloseMessageEvent.MessageCloseHandler> {
    public interface MessageCloseHandler extends EventHandler {
        void onCloseMessage(CloseMessageEvent event);
    }

    public static Type<MessageCloseHandler> TYPE = new Type<MessageCloseHandler>();
    private final Message message;

    public static Type<MessageCloseHandler> getType() {
        return TYPE;
    }

    public static void fire(HasHandlers source, Message message) {
        source.fireEvent(new CloseMessageEvent(message));
    }

    public CloseMessageEvent(Message message) {
        this.message = message;
    }

    @Override
    public Type<MessageCloseHandler> getAssociatedType() {
        return TYPE;
    }

    public Message getMessage() {
        return message;
    }

    @Override
    protected void dispatch(MessageCloseHandler handler) {
        handler.onCloseMessage(this);
    }
}
