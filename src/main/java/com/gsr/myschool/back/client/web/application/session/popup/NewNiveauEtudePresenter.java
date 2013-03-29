package com.gsr.myschool.back.client.web.application.session.popup;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.back.client.web.application.session.event.NiveauEtudeChangedEvent;
import com.gsr.myschool.back.client.web.application.session.popup.NewNiveauEtudePresenter.MyView;
import com.gsr.myschool.common.client.proxy.SessionExamenProxy;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;

public class NewNiveauEtudePresenter extends PresenterWidget<MyView> implements NewNiveauEtudeUiHandlers {
    public interface MyView extends PopupView, HasUiHandlers<NewNiveauEtudeUiHandlers> {
        void resetData();
    }

    private final BackRequestFactory requestFactory;

    private SessionExamenProxy currentSession;

    @Inject
    public NewNiveauEtudePresenter(final EventBus eventBus, final MyView view,
                                   final BackRequestFactory requestFactory) {
        super(eventBus, view);

        this.requestFactory = requestFactory;

        getView().setUiHandlers(this);
    }

    public void setCurrentSession(SessionExamenProxy currentSession) {
        this.currentSession = currentSession;
    }

    @Override
    public void attachNiveauEtude(Long niveauEtudeId) {
        Long sessionId = currentSession.getId();
        requestFactory.sessionService().attacheToSession(sessionId, niveauEtudeId).fire(new ReceiverImpl<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                NiveauEtudeChangedEvent.fire(this);
                getView().hide();
            }
        });
    }

    protected void onReveal() {
        getView().resetData();
    }
}
