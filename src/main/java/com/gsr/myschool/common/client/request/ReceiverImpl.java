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

package com.gsr.myschool.common.client.request;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.gsr.myschool.common.client.event.ServerDownEvent;

public abstract class ReceiverImpl<T> extends Receiver<T> implements HasHandlers {
    private static final String SERVER_DOWN = "Server Error 0";

    @Inject
    protected static EventBus eventBus;

    @Override
    public void fireEvent(GwtEvent<?> event) {
        eventBus.fireEvent(event);
    }

    public void onFailure(ServerFailure error) {
        if (SERVER_DOWN.equals(error.getMessage().trim())) {
            eventBus.fireEvent(new ServerDownEvent());
        }

        onException(error.getExceptionType());
    }

    public void onException(String type) {
    }
}
