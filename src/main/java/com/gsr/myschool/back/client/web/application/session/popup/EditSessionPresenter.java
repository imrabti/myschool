package com.gsr.myschool.back.client.web.application.session.popup;

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.back.client.request.SessionRequest;
import com.gsr.myschool.back.client.resource.message.MessageBundle;
import com.gsr.myschool.back.client.web.application.session.event.SessionChangedEvent;
import com.gsr.myschool.back.client.web.application.session.popup.EditSessionPresenter.MyView;
import com.gsr.myschool.common.client.mvp.ValidatedPopupView;
import com.gsr.myschool.common.client.proxy.SessionExamenProxy;
import com.gsr.myschool.common.client.request.ValidatedReceiverImpl;
import com.gsr.myschool.common.client.widget.messages.Message;
import com.gsr.myschool.common.client.widget.messages.event.MessageEvent;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class EditSessionPresenter extends PresenterWidget<MyView> implements EditSessionUiHandlers {
    public interface MyView extends ValidatedPopupView, HasUiHandlers<EditSessionUiHandlers> {
        void edit(SessionExamenProxy sessionExamen);
    }

    private final BackRequestFactory requestFactory;
    private final MessageBundle messageBundle;

    private SessionRequest currentContext;
    private SessionExamenProxy currentSession;
    private Boolean sessionViolation;

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
        sessionViolation = false;
    }

    @Override
    public void saveSession(SessionExamenProxy sessionExamen) {
        if (!sessionViolation) {
            if (currentSession.getId() == null) {
                currentContext.createNewSession(currentSession).fire(new ValidatedReceiverImpl<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        sessionViolation = true;

                        Message message = new Message.Builder(messageBundle.sessionSavedSuccess())
                                .style(AlertType.SUCCESS).build();
                        MessageEvent.fire(this, message);
                        SessionChangedEvent.fire(this);

                        getView().clearErrors();
                        getView().hide();
                    }

                    @Override
                    public void onValidationError(Set<ConstraintViolation<?>> violations) {
                        sessionViolation = false;

                        getView().clearErrors();
                        getView().showErrors(violations);
                    }
                });
            } else {
                currentContext.updateSession(currentSession).fire(new ValidatedReceiverImpl<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        sessionViolation = true;

                        Message message = new Message.Builder(messageBundle.sessionUpdatedSuccess())
                                .style(AlertType.SUCCESS).build();
                        MessageEvent.fire(this, message);
                        SessionChangedEvent.fire(this);

                        getView().clearErrors();
                        getView().hide();
                    }

                    @Override
                    public void onValidationError(Set<ConstraintViolation<?>> violations) {
                        sessionViolation = false;

                        getView().clearErrors();
                        getView().showErrors(violations);
                    }
                });
            }
        } else {
          currentContext.fire();
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
        getView().clearErrors();
    }
}
