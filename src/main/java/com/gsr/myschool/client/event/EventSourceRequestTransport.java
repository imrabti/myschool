package com.gsr.myschool.client.event;

import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.RequestTransport;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.gsr.myschool.client.security.SecureRequestTransport;
import com.gsr.myschool.client.security.SecurityUtils;

public class EventSourceRequestTransport implements RequestTransport {
    private final EventBus eventBus;
    private final RequestTransport wrapped;

    public EventSourceRequestTransport(EventBus eventBus, SecurityUtils securityUtils) {
        this(eventBus, new SecureRequestTransport(securityUtils));
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


