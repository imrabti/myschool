package com.gsr.myschool.back.client.web.application.session;

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.common.base.Strings;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.place.NameTokens;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.back.client.resource.message.MessageBundle;
import com.gsr.myschool.back.client.web.application.ApplicationPresenter;
import com.gsr.myschool.back.client.web.application.session.SessionPresenter.MyProxy;
import com.gsr.myschool.back.client.web.application.session.SessionPresenter.MyView;
import com.gsr.myschool.back.client.web.application.session.event.NiveauEtudeChangedEvent;
import com.gsr.myschool.back.client.web.application.session.event.SessionChangedEvent;
import com.gsr.myschool.back.client.web.application.session.popup.EditNiveauEtudeTimePresenter;
import com.gsr.myschool.back.client.web.application.session.popup.EditSessionPresenter;
import com.gsr.myschool.back.client.web.application.session.popup.NewNiveauEtudePresenter;
import com.gsr.myschool.common.client.proxy.FiliereProxy;
import com.gsr.myschool.common.client.proxy.NiveauEtudeProxy;
import com.gsr.myschool.common.client.proxy.SessionExamenProxy;
import com.gsr.myschool.common.client.proxy.SessionNiveauEtudeProxy;
import com.gsr.myschool.common.client.request.ConvocationRequestBuilder;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.security.HasRoleGatekeeper;
import com.gsr.myschool.common.client.widget.messages.Message;
import com.gsr.myschool.common.client.widget.messages.event.MessageEvent;
import com.gsr.myschool.common.shared.constants.GlobalParameters;
import com.gsr.myschool.common.shared.dto.NiveauEtudeNode;
import com.gsr.myschool.common.shared.dto.SessionTree;
import com.gsr.myschool.common.shared.exception.SessionEmptyException;
import com.gsr.myschool.common.shared.type.SessionStatus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.GatekeeperParams;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionPresenter extends Presenter<MyView, MyProxy> implements SessionUiHandlers,
        SessionChangedEvent.SessionChangedHandler, NiveauEtudeChangedEvent.NiveauEtudeChangedHandler {
    public interface MyView extends View, HasUiHandlers<SessionUiHandlers> {
        void setData(List<SessionExamenProxy> sessions);

        void setAttachedNiveau(List<SessionTree> treeModel, SessionStatus status);
    }

    @ProxyStandard
    @NameToken(NameTokens.session)
    @UseGatekeeper(HasRoleGatekeeper.class)
    @GatekeeperParams({GlobalParameters.ROLE_ADMIN})
    public interface MyProxy extends ProxyPlace<SessionPresenter> {
    }

    private final BackRequestFactory requestFactory;
    private final MessageBundle messageBundle;
    private final EditSessionPresenter editSessionPresenter;
    private final NewNiveauEtudePresenter newNiveauEtudePresenter;
    private final EditNiveauEtudeTimePresenter editNiveauEtudeTimePresenter;

    private SessionExamenProxy selectedSession;

    @Inject
    public SessionPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
                            final BackRequestFactory requestFactory,
                            final MessageBundle messageBundle,
                            final EditSessionPresenter editSessionPresenter,
                            final NewNiveauEtudePresenter newNiveauEtudePresenter,
                            final EditNiveauEtudeTimePresenter editNiveauEtudeTimePresenter) {
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);

        this.requestFactory = requestFactory;
        this.messageBundle = messageBundle;
        this.editSessionPresenter = editSessionPresenter;
        this.newNiveauEtudePresenter = newNiveauEtudePresenter;
        this.editNiveauEtudeTimePresenter = editNiveauEtudeTimePresenter;

        getView().setUiHandlers(this);
    }

    @Override
    public void onSessionChange(SessionChangedEvent event) {
        selectedSession = null;
        loadSession();
    }

    @Override
    public void onNiveauEtudeChange(NiveauEtudeChangedEvent event) {
        loadAttachedNiveauEtude();
    }

    @Override
    public void sessionSelected(SessionExamenProxy session) {
        selectedSession = session;
        loadAttachedNiveauEtude();
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
        Long sessionId = session.getId();
        requestFactory.sessionService().openSession(sessionId).fire(new ReceiverImpl<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                if (aBoolean) {
                    Message message = new Message.Builder(messageBundle.openSessionSuccess())
                            .style(AlertType.SUCCESS).build();
                    MessageEvent.fire(this, message);

                    getView().setAttachedNiveau(new ArrayList<SessionTree>(), selectedSession.getStatus());
                    loadSession();
                } else {
                    Message message = new Message.Builder(messageBundle.openSessionError()).build();
                    MessageEvent.fire(this, message);
                }
            }

            @Override
            public void onException(String type) {
                if (SessionEmptyException.class.getName().equals(type)) {
                    Message message = new Message.Builder(messageBundle.sessionEmptyError()).build();
                    MessageEvent.fire(this, message);
                }
            }
        });
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
    public void showNiveauEtudeDetail(NiveauEtudeNode niveauEtudeNode) {
        editNiveauEtudeTimePresenter.setCurrentSession(selectedSession);
        editNiveauEtudeTimePresenter.setCurrentNiveauEtudeId(niveauEtudeNode.getId());
        addToPopupSlot(editNiveauEtudeTimePresenter);
    }

    @Override
    public void deleteNiveauEtude(NiveauEtudeNode niveauEtudeNode) {
        if (Window.confirm(messageBundle.deleteConfirmation())) {
            requestFactory.sessionService().deleteNiveauEtude(niveauEtudeNode.getId()).fire(new ReceiverImpl<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Message message = new Message.Builder(messageBundle.niveauEtudeDeleteSucess())
                            .style(AlertType.SUCCESS).build();
                    MessageEvent.fire(this, message);

                    loadAttachedNiveauEtude();
                }
            });
        }
    }

    @Override
    public void printConvocation(NiveauEtudeNode niveauEtudeNode) {
        ConvocationRequestBuilder requestBuilder = new ConvocationRequestBuilder();
        requestBuilder.buildData(niveauEtudeNode.getId().toString(), selectedSession.getId().toString());
        requestBuilder.sendTestRequest();
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

    private void loadAttachedNiveauEtude() {
        Long sessionId = selectedSession.getId();
        requestFactory.sessionService().findAllNiveauEtudeBySession(sessionId).fire(
                new ReceiverImpl<List<SessionNiveauEtudeProxy>>() {
                    @Override
                    public void onSuccess(List<SessionNiveauEtudeProxy> response) {
                        List<SessionTree> tree = buildTree(response);
                        getView().setAttachedNiveau(tree, selectedSession.getStatus());
                    }
                });
    }

    private List<SessionTree> buildTree(List<SessionNiveauEtudeProxy> response) {
        Map<Long, SessionTree> treeRoot = new HashMap<Long, SessionTree>();

        for (SessionNiveauEtudeProxy item : response) {
            FiliereProxy filiere = item.getNiveauEtude().getFiliere();
            NiveauEtudeProxy niveauEtude = item.getNiveauEtude();

            if (!treeRoot.containsKey(filiere.getId())) {
                SessionTree rootNode = new SessionTree();
                rootNode.setId(filiere.getId());
                rootNode.setName(filiere.getNom());
                treeRoot.put(filiere.getId(), rootNode);
            }

            SessionTree rootNode = treeRoot.get(filiere.getId());
            if (!rootNode.getNiveauEtudeNodes().containsKey(niveauEtude.getId())) {
                NiveauEtudeNode niveauNode = new NiveauEtudeNode();
                niveauNode.setId(niveauEtude.getId());
                niveauNode.setName(niveauEtude.getNom());
                niveauNode.setComplete(niveauEtudeCompleted(niveauEtude.getId(), response));
                rootNode.getNiveauEtudeNodes().put(niveauEtude.getId(), niveauNode);
            }
        }

        return new ArrayList<SessionTree>(treeRoot.values());
    }

    private Boolean niveauEtudeCompleted(Long id, List<SessionNiveauEtudeProxy> response) {
        for (SessionNiveauEtudeProxy item : response) {
            if (item.getNiveauEtude().getId().equals(id)) {
                if (Strings.isNullOrEmpty(item.getHoraireA())
                        || Strings.isNullOrEmpty(item.getHoraireDe())) {
                    return false;
                }
            }
        }

        return true;
    }
}
