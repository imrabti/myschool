package com.gsr.myschool.client.web.widget.message.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.gsr.myschool.client.web.widget.message.Message;

public class MessageEvent extends GwtEvent<MessageEvent.MessageHandler> {
    public interface MessageHandler extends EventHandler {
        public void onMessage(MessageEvent event);
    }

    private static final Type<MessageHandler> TYPE = new Type<MessageHandler>();

    private final Message message;

    public static Type<MessageHandler> getType() {
        return TYPE;
    }

    public static void fire(HasHandlers source, Message message) {
        source.fireEvent(new MessageEvent(message));
    }

    public MessageEvent(Message message) {
        this.message = message;
    }

    @Override
    public Type<MessageHandler> getAssociatedType() {
        return TYPE;
    }

    public Message getMessage() {
        return message;
    }

    @Override
    protected void dispatch(MessageHandler handler) {
        handler.onMessage(this);
    }
}
