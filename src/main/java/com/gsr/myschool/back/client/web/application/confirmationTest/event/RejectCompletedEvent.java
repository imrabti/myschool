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

package com.gsr.myschool.back.client.web.application.confirmationTest.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class RejectCompletedEvent extends GwtEvent<RejectCompletedEvent.RejectCompletedHandler> {
    public interface RejectCompletedHandler extends EventHandler {
        void onRejectCompleted(RejectCompletedEvent event);
    }

    public static Type<RejectCompletedHandler> TYPE = new Type<RejectCompletedHandler>();

    public static void fire(HasHandlers source) {
        source.fireEvent(new RejectCompletedEvent());
    }

    public static Type<RejectCompletedHandler> getType() {
        return TYPE;
    }

    @Override
    public Type<RejectCompletedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(RejectCompletedHandler handler) {
        handler.onRejectCompleted(this);
    }
}
