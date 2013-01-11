package com.gsr.myschool.client.web.widget.message;

import com.arcbees.core.client.mvp.ViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import java.util.Iterator;

public class MessageView extends ViewImpl implements MessagePresenter.MyView {
    public interface Binder extends UiBinder<Widget, MessageView> {
    }

    @UiField
    HTMLPanel messages;

    private final MessageWidgetFactory messageWidgetFactory;

    @Inject
    public MessageView(final Binder binder, final MessageWidgetFactory messageWidgetFactory) {
        this.messageWidgetFactory = messageWidgetFactory;

        initWidget(binder.createAndBindUi(this));
    }

    @Override
    public void addMessage(Message message) {
        removeMessage(message);
        MessageWidget messageWidget = messageWidgetFactory.createMessage(message);
        messages.add(messageWidget);
    }

    @Override
    public void removeMessage(Message message) {
        Iterator<Widget> iterator = messages.iterator();
        while (iterator.hasNext()) {
            MessageWidget messageWidget = (MessageWidget) iterator.next();
            if (messageWidget.getMessage().equals(message)) {
                messageWidget.removeFromParent();
            }
        }
    }

    @Override
    public void clear() {
        messages.clear();
    }
}
