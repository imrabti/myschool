package com.gsr.myschool.back.client.web.application.session;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.place.NameTokens;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.back.client.web.application.ApplicationPresenter;
import com.gsr.myschool.back.client.web.application.session.SessionPresenter.MyView;
import com.gsr.myschool.back.client.web.application.session.SessionPresenter.MyProxy;
import com.gsr.myschool.back.client.web.application.session.event.NiveauEtudeChangedEvent;
import com.gsr.myschool.back.client.web.application.session.event.SessionChangedEvent;
import com.gsr.myschool.back.client.web.application.session.popup.EditSessionPresenter;
import com.gsr.myschool.back.client.web.application.session.popup.NewNiveauEtudePresenter;
import com.gsr.myschool.common.client.proxy.SessionExamenProxy;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.security.HasRoleGatekeeper;
import com.gsr.myschool.common.shared.constants.GlobalParameters;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.GatekeeperParams;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import java.util.List;

public class SessionPresenter extends Presenter<MyView, MyProxy> implements SessionUiHandlers,
        SessionChangedEvent.SessionChangedHandler, NiveauEtudeChangedEvent.NiveauEtudeChangedHandler {
    public interface MyView extends View, HasUiHandlers<SessionUiHandlers> {
        void setData(List<SessionExamenProxy> sessions);
    }

    @ProxyStandard
    @NameToken(NameTokens.session)
    @UseGatekeeper(HasRoleGatekeeper.class)
    @GatekeeperParams({GlobalParameters.ROLE_ADMIN})
    public interface MyProxy extends ProxyPlace<SessionPresenter> {
    }

    private final BackRequestFactory requestFactory;
    private final EditSessionPresenter editSessionPresenter;
    private final NewNiveauEtudePresenter newNiveauEtudePresenter;

    private SessionExamenProxy selectedSession;

    @Inject
    public SessionPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
                            final BackRequestFactory requestFactory,
                            final EditSessionPresenter editSessionPresenter,
                            final NewNiveauEtudePresenter newNiveauEtudePresenter) {
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);

        this.requestFactory = requestFactory;
        this.editSessionPresenter = editSessionPresenter;
        this.newNiveauEtudePresenter = newNiveauEtudePresenter;

        getView().setUiHandlers(this);
    }

    @Override
    public void onSessionChange(SessionChangedEvent event) {
        selectedSession = null;
        loadSession();
    }

    @Override
    public void onNiveauEtudeChange(NiveauEtudeChangedEvent event) {
        // TODO Reload currently displated niveau etude.
    }

    @Override
    public void sessionSelected(SessionExamenProxy session) {
        selectedSession = session;

        // TODO load attached NiveaEtudes
        // TODO update the UI to show or hide the Edit button
    }

    @Override
    public void newSession() {
        editSessionPresenter.setCurrentSession(null);
        addToPopupSlot(editSessionPresenter);
    }

    @Override
    public void attachNiveauEtude() {
        if (selectedSession != null) {
            newNiveauEtudePresenter.setCurrentSession(selectedSession);
            addToPopupSlot(newNiveauEtudePresenter);
        }
    }

    @Override
    public void openSession(SessionExamenProxy session) {
        // TODO : Remove a session
    }

    @Override
    public void updateSession(SessionExamenProxy session) {
        editSessionPresenter.setCurrentSession(session);
        addToPopupSlot(editSessionPresenter);
    }

    @Override
    public void closeSession(SessionExamenProxy session) {
        // TODO : Close a session
    }

    @Override
    public void cancelSession(SessionExamenProxy session) {
        // TODO : Cancel a session
    }

    @Override
    protected void onReveal() {
        selectedSession = null;
        loadSession();
    }

    @Override
    protected void onBind() {
        addRegisteredHandler(SessionChangedEvent.getType(), this);
        addRegisteredHandler(NiveauEtudeChangedEvent.getType(), this);
    }

    private void loadSession() {
        requestFactory.sessionService().findAllSessions().fire(new ReceiverImpl<List<SessionExamenProxy>>() {
            @Override
            public void onSuccess(List<SessionExamenProxy> result) {
                getView().setData(result);
            }
        });
    }
}
