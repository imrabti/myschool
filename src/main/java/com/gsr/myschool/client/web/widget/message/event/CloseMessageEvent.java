package com.gsr.myschool.client.web.widget.message.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.gsr.myschool.client.web.widget.message.Message;

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
