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

package com.gsr.myschool.front.client.web.application.inbox.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.gsr.myschool.common.client.proxy.InboxProxy;
import com.gsr.myschool.front.client.web.application.inscription.WizardStep;

public class InboxStatusChangedEvent extends GwtEvent<InboxStatusChangedEvent.InboxStatusChangedHandler> {
    public interface InboxStatusChangedHandler extends EventHandler {
        void onInboxStatusChanged(InboxStatusChangedEvent event);
    }

    public static Type<InboxStatusChangedHandler> TYPE = new Type<InboxStatusChangedHandler>();
    private InboxProxy inboxMessage;

    public InboxStatusChangedEvent(){
    }

    public InboxStatusChangedEvent(InboxProxy value){
        this.inboxMessage = value;
    }

    public InboxProxy getInboxMessage(){
        return inboxMessage;
    }

    public static void fire(HasHandlers source, InboxProxy value) {
        source.fireEvent(new InboxStatusChangedEvent(value));
    }

    public static void fire(HasHandlers source) {
        source.fireEvent(new InboxStatusChangedEvent());
    }

    @Override
    public Type<InboxStatusChangedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(InboxStatusChangedHandler handler) {
        handler.onInboxStatusChanged(this);
    }

}
