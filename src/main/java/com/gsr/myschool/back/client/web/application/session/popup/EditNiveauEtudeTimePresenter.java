package com.gsr.myschool.back.client.web.application.session.popup;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.back.client.request.SessionRequest;
import com.gsr.myschool.back.client.web.application.session.event.NiveauEtudeChangedEvent;
import com.gsr.myschool.common.client.proxy.SessionExamenProxy;
import com.gsr.myschool.common.client.proxy.SessionNiveauEtudeProxy;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.shared.type.SessionStatus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gsr.myschool.back.client.web.application.session.popup.EditNiveauEtudeTimePresenter.MyView;
import com.gwtplatform.mvp.client.PresenterWidget;

import java.util.ArrayList;
import java.util.List;

public class EditNiveauEtudeTimePresenter extends PresenterWidget<MyView> implements EditNiveauEtudeTimeUiHandlers {
    public interface MyView extends PopupView, HasUiHandlers<EditNiveauEtudeTimeUiHandlers> {
        void editHoraires(List<SessionNiveauEtudeProxy> data, Boolean enabled);
    }

    private final BackRequestFactory requestFactory;

    private SessionExamenProxy currentSession;
    private Long currentNiveauEtudeId;
    private List<SessionNiveauEtudeProxy> horaires;

    @Inject
    public EditNiveauEtudeTimePresenter(final EventBus eventBus, final MyView view,
                                        final BackRequestFactory requestFactory) {
        super(eventBus, view);

        this.requestFactory = requestFactory;

        getView().setUiHandlers(this);
    }

    public void setCurrentSession(SessionExamenProxy currentSession) {
        this.currentSession = currentSession;
    }

    public void setCurrentNiveauEtudeId(Long currentNiveauEtudeId) {
        this.currentNiveauEtudeId = currentNiveauEtudeId;
    }

    @Override
    public void saveHoraires(List<SessionNiveauEtudeProxy> matieres) {
        List<String> horaires = new ArrayList<String>();
        for (SessionNiveauEtudeProxy item : matieres) {
            horaires.add(item.getId() + "#" + item.getHoraireDe() + "#" + item.getHoraireA());
        }

        requestFactory.sessionService().updateHoraire(horaires).fire(new ReceiverImpl<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                NiveauEtudeChangedEvent.fire(this);
                getView().hide();
            }
        });
    }

    @Override
    protected void onReveal() {
        Long sessionId = currentSession.getId();
        requestFactory.sessionService().findAllMatieresByNiveauEtude(sessionId, currentNiveauEtudeId)
                .fire(new ReceiverImpl<List<SessionNiveauEtudeProxy>>() {
                    @Override
                    public void onSuccess(List<SessionNiveauEtudeProxy> result) {
                        horaires = new ArrayList<SessionNiveauEtudeProxy>();
                        for (SessionNiveauEtudeProxy piece : result) {
                            SessionRequest context = requestFactory.sessionService();
                            horaires.add(context.edit(piece));
                        }

                        getView().editHoraires(horaires, currentSession.getStatus() == SessionStatus.CREATED);
                    }
                });
    }
}
