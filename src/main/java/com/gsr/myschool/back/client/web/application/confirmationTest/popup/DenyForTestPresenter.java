package com.gsr.myschool.back.client.web.application.confirmationTest.popup;

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.back.client.web.application.validation.event.RejectCompletedEvent;
import com.gsr.myschool.common.client.mvp.ValidatedPopupView;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;
import com.gsr.myschool.common.client.widget.messages.Message;
import com.gsr.myschool.common.client.widget.messages.event.MessageEvent;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;

public class DenyForTestPresenter extends PresenterWidget<DenyForTestPresenter.MyView>
        implements DenyForTestUiHandlers {
    public interface MyView extends ValidatedPopupView, HasUiHandlers<DenyForTestUiHandlers> {
        void resetReason();
    }

    private final BackRequestFactory requestFactory;
    private final SharedMessageBundle messageBundle;

    private DossierProxy currentDossier;

    @Inject
    public DenyForTestPresenter(final EventBus eventBus, final MyView view,
                                final BackRequestFactory requestFactory,
                                final SharedMessageBundle messageBundle) {
        super(eventBus, view);

        this.requestFactory = requestFactory;
        this.messageBundle = messageBundle;

        getView().setUiHandlers(this);
    }

    @Override
    public void loadDossier(DossierProxy dossierProxy) {
        currentDossier = dossierProxy;
    }

    @Override
    protected void onReveal() {
        getView().resetReason();
    }

    @Override
    public void setDenyReason(String reason) {
        currentDossier.setMotifRefus(reason);
        requestFactory.dossierService().rejectDossier(currentDossier).fire(new ReceiverImpl<Boolean>() {
            @Override
            public void onSuccess(Boolean response) {
                String messageString = response ? messageBundle.operationSuccess() : messageBundle.operationFailure();
                AlertType alertType = response ? AlertType.SUCCESS : AlertType.ERROR;
                Message message = new Message.Builder(messageString).style(alertType).build();
                MessageEvent.fire(this, message);

                if (response) {
                    getView().hide();
                    RejectCompletedEvent.fire(this);
                }
            }
        });
    }
}
