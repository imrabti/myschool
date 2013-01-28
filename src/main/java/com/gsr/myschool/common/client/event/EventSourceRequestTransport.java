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

import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.RequestTransport;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.gsr.myschool.common.client.security.SecureRequestTransport;
import com.gsr.myschool.common.client.security.SecurityUtils;

public class EventSourceRequestTransport implements RequestTransport {
    private final EventBus eventBus;
    private final RequestTransport wrapped;

    public EventSourceRequestTransport(EventBus eventBus, SecurityUtils securityUtils, String requestUrl) {
        this(eventBus, new SecureRequestTransport(securityUtils, requestUrl));
    }

    public EventSourceRequestTransport(EventBus eventBus, RequestTransport wrapped) {
        this.eventBus = eventBus;
        this.wrapped = wrapped;
    }

    @Override
    public void send(String payload, final TransportReceiver receiver) {
        TransportReceiver myReceiver = new TransportReceiver() {
            @Override
            public void onTransportSuccess(String payload) {
                try {
                    receiver.onTransportSuccess(payload);
                } finally {
                    eventBus.fireEvent(new RequestEvent(RequestEvent.State.RECEIVED));
                }
            }

            @Override
            public void onTransportFailure(ServerFailure failure) {
                try {
                    receiver.onTransportFailure(failure);
                } finally {
                    eventBus.fireEvent(new RequestEvent(RequestEvent.State.RECEIVED));
                }
            }
        };

        try {
            wrapped.send(payload, myReceiver);
        } finally {
            eventBus.fireEvent(new RequestEvent(RequestEvent.State.SENT));
        }
    }
}


