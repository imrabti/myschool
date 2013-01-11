package com.gsr.myschool.client.web.widget.message;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gsr.myschool.client.web.widget.message.event.CloseMessageEvent;
import com.gsr.myschool.client.web.widget.message.event.MessageEvent;

public class MessagePresenter extends PresenterWidget<MessagePresenter.MyView> implements MessageEvent.MessageHandler,
        CloseMessageEvent.MessageCloseHandler {
    public interface MyView extends View {
        void addMessage(Message message);

        void removeMessage(Message message);

        void clear();
    }

    @Inject
    public MessagePresenter(final EventBus eventBus, final MyView view) {
        super(eventBus, view);
    }

    @Override
    public void onMessage(MessageEvent event) {
        getView().addMessage(event.getMessage());
    }

    @Override
    public void onCloseMessage(CloseMessageEvent event) {
        getView().removeMessage(event.getMessage());
    }

    @Override
    protected void onBind() {
        addRegisteredHandler(MessageEvent.getType(), this);
        addRegisteredHandler(CloseMessageEvent.getType(), this);
    }
}
