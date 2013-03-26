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

package com.gsr.myschool.back.client.web.application.session.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class SessionChangedEvent extends GwtEvent<SessionChangedEvent.SessionChangedHandler> {
    public interface SessionChangedHandler extends EventHandler {
        public void onSessionChange(SessionChangedEvent event);
    }

    private static final Type<SessionChangedHandler> TYPE = new Type<SessionChangedHandler>();

    public static Type<SessionChangedHandler> getType() {
        return TYPE;
    }

    public static void fire(HasHandlers source) {
        source.fireEvent(new SessionChangedEvent());
    }

    @Override
    public Type<SessionChangedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(SessionChangedHandler handler) {
        handler.onSessionChange(this);
    }
}
