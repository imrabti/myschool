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

package com.gsr.myschool.common.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class RequestEvent extends GwtEvent<RequestEvent.RequestEventHandler> {
    private static final Type<RequestEventHandler> TYPE = new Type<RequestEventHandler>();

    public interface RequestEventHandler extends EventHandler {
        void onRequestEvent(RequestEvent requestEvent);
    }

    public enum State {
        SENT, RECEIVED
    }

    private final State state;

    public RequestEvent(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    @Override
    public Type<RequestEventHandler> getAssociatedType() {
        return TYPE;
    }

    public static Type<RequestEventHandler> getType() {
        return TYPE;
    }

    public static void fire(HasHandlers source, State state) {
        source.fireEvent(new RequestEvent(state));
    }

    @Override
    protected void dispatch(RequestEventHandler requestEventHandler) {
        requestEventHandler.onRequestEvent(this);
    }
}
