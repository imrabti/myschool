package com.gsr.myschool.back.client.web.application.affectation.popup;

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.back.client.web.application.affectation.event.DossierAffectedEvent;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.client.proxy.SessionExamenProxy;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;
import com.gsr.myschool.common.client.widget.messages.CloseDelay;
import com.gsr.myschool.common.client.widget.messages.Message;
import com.gsr.myschool.common.client.widget.messages.event.MessageEvent;
import com.gsr.myschool.common.shared.exception.AffectationClosedException;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;

import java.util.List;

public class SessionAffectationPresenter extends PresenterWidget<SessionAffectationPresenter.MyView> implements SessionAffectationUiHandlers {
    public interface MyView extends PopupView, HasUiHandlers<SessionAffectationUiHandlers> {
        void setData(List<SessionExamenProxy> data, DossierProxy currentDossier);
    }

    private final BackRequestFactory requestFactory;
    private final SharedMessageBundle messageBundle;
    private DossierProxy currentDossier;


    @Inject
    public SessionAffectationPresenter(final EventBus eventBus, final MyView view,
                                       final BackRequestFactory requestFactory,
                                       final SharedMessageBundle messageBundle) {
        super(eventBus, view);

        this.requestFactory = requestFactory;
        this.messageBundle = messageBundle;

        getView().setUiHandlers(this);
    }

    @Override
    public void valueSelected(SessionExamenProxy selectedValue) {
        requestFactory.sessionService().affecter(currentDossier, selectedValue).fire(new ReceiverImpl<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                String content = aBoolean ? messageBundle.operationSuccess() : messageBundle.operationFailure();
                AlertType alertType = aBoolean ? AlertType.SUCCESS : AlertType.ERROR;
                Message message = new Message.Builder(content)
                        .style(alertType).build();

                MessageEvent.fire(this, message);
                DossierAffectedEvent.fire(this);
                getView().hide();
            }
            @Override
            public void onException(String type) {
                if (AffectationClosedException.class.getName().equals(type)) {
                    Message message = new Message.Builder(messageBundle.affectationClosed())
                            .style(AlertType.WARNING).closeDelay(CloseDelay.NEVER).build();
                    MessageEvent.fire(this, message);
                    getView().hide();
                }
            }
        });
    }

    public void setCurrentDossier(DossierProxy currentDossier) {
        this.currentDossier = currentDossier;
    }

    @Override
    protected void onReveal() {
        requestFactory.sessionService().findSessionByNE(currentDossier.getNiveauEtude()).fire(new ReceiverImpl<List<SessionExamenProxy>>() {
            @Override
            public void onSuccess(List<SessionExamenProxy> sessionExamenProxies) {
                getView().setData(sessionExamenProxies, currentDossier);
            }
        });
    }
}
