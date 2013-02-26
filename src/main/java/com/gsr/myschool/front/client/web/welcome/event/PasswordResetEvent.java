package com.gsr.myschool.front.client.web.welcome.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class PasswordResetEvent extends GwtEvent<PasswordResetEvent.PasswordResetHandler> {
    public static Type<PasswordResetHandler> TYPE = new Type<PasswordResetHandler>();

    public interface PasswordResetHandler extends EventHandler {
        void onPasswordReset(PasswordResetEvent event);
    }

    public static Type<PasswordResetHandler> getType() {
        return TYPE;
    }

    public static void fire(HasHandlers source) {
        source.fireEvent(new PasswordResetEvent());
    }

    @Override
    public Type<PasswordResetHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(PasswordResetHandler handler) {
        handler.onPasswordReset(this);
    }
}
