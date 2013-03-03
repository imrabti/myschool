package com.gsr.myschool.common.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class ServerDownEvent extends GwtEvent<ServerDownEvent.ServerDownHandler> {
    public static Type<ServerDownHandler> TYPE = new Type<ServerDownHandler>();

    public interface ServerDownHandler extends EventHandler {
        void onServerDown(ServerDownEvent event);
    }

    public static Type<ServerDownHandler> getType() {
        return TYPE;
    }

    public static void fire(HasHandlers source) {
        source.fireEvent(new ServerDownEvent());
    }

    @Override
    public Type<ServerDownHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ServerDownHandler handler) {
        handler.onServerDown(this);
    }
}
