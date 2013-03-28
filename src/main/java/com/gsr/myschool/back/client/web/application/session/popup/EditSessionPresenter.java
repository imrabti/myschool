package com.gsr.myschool.back.client.web.application.session.popup;

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.back.client.request.SessionRequest;
import com.gsr.myschool.back.client.resource.message.MessageBundle;
import com.gsr.myschool.back.client.web.application.session.event.SessionChangedEvent;
import com.gsr.myschool.common.client.proxy.SessionExamenProxy;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.widget.messages.Message;
import com.gsr.myschool.common.client.widget.messages.event.MessageEvent;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gsr.myschool.back.client.web.application.session.popup.EditSessionPresenter.MyView;

public class EditSessionPresenter extends PresenterWidget<MyView> implements EditSessionUiHandlers {
    public interface MyView extends PopupView, HasUiHandlers<EditSessionUiHandlers> {
        void edit(SessionExamenProxy sessionExamen);
    }

    private final BackRequestFactory requestFactory;
    private final MessageBundle messageBundle;

    private SessionRequest currentContext;
    private SessionExamenProxy currentSession;

    @Inject
    public EditSessionPresenter(final EventBus eventBus, final MyView view,
                                final BackRequestFactory requestFactory,
                                final MessageBundle messageBundle) {
        super(eventBus, view);

        this.requestFactory = requestFactory;
        this.messageBundle = messageBundle;

        getView().setUiHandlers(this);
    }

    public void setCurrentSession(SessionExamenProxy currentSession) {
        this.currentSession = currentSession;
    }

    @Override
    public void saveSession(SessionExamenProxy sessionExamen) {
        if (currentSession.getId() == null) {
            currentContext.createNewSession(currentSession).fire(new ReceiverImpl<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Message message = new Message.Builder(messageBundle.sessionSavedSuccess())
                            .style(AlertType.SUCCESS).build();
                    MessageEvent.fire(this, message);
                    SessionChangedEvent.fire(this);

                    getView().hide();
                }
            });
        } else {
            currentContext.updateSession(currentSession).fire(new ReceiverImpl<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Message message = new Message.Builder(messageBundle.sessionUpdatedSuccess())
                            .style(AlertType.SUCCESS).build();
                    MessageEvent.fire(this, message);
                    SessionChangedEvent.fire(this);

                    getView().hide();
                }
            });
        }
    }

    @Override
    protected void onReveal() {
        currentContext = requestFactory.sessionService();
        if (currentSession == null) {
            currentSession = currentContext.create(SessionExamenProxy.class);
        } else {
            currentSession = currentContext.edit(currentSession);
        }

        getView().edit(currentSession);
    }
}
