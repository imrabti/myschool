package com.gsr.myschool.client.web.widget.message;

import com.github.gwtbootstrap.client.ui.Alert;
import com.google.gwt.user.client.Timer;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class MessageWidget extends Alert {
    private final Message message;
    private Timer closeTimer = new Timer() {
        @Override
        public void run() {
            close();
        }
    };

    @Inject
    public MessageWidget(@Assisted Message message) {
        super();

        this.message = message;

        initContent();
        initTimeout();
    }

    public Message getMessage() {
        return message;
    }

    private void initContent() {
        setText(message.getMessage().asString());
        setClose(message.useDefaultCloseButton());
        setType(message.getMessageStyle());
        setAnimation(true);
    }

    private void initTimeout() {
        if (!message.getCloseDelay().equals(CloseDelay.NEVER)) {
            closeTimer.schedule(message.getCloseDelay().getDelay());
        }
    }
}

