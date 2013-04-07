package com.gsr.myschool.back.client.web.application.settings.popup;

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.back.client.request.SettingsRequest;
import com.gsr.myschool.back.client.resource.message.MessageBundle;
import com.gsr.myschool.back.client.web.application.settings.event.SystemScolaireChangedEvent;
import com.gsr.myschool.common.client.proxy.FiliereProxy;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.util.EditorView;
import com.gsr.myschool.common.client.widget.messages.Message;
import com.gsr.myschool.common.client.widget.messages.event.MessageEvent;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;

public class AddFilierePresenter extends PresenterWidget<AddFilierePresenter.MyView> implements AddFiliereUiHandlers {
    public interface MyView extends PopupView, EditorView<FiliereProxy>, HasUiHandlers<AddFiliereUiHandlers> {
    }

    private final BackRequestFactory requestFactory;
    private final MessageBundle messageBundle;

    private SettingsRequest currentContext;

    @Inject
    public AddFilierePresenter(final EventBus eventBus, final MyView view,
                               final BackRequestFactory requestFactory,
                               final MessageBundle messageBundle) {
        super(eventBus, view);

        this.requestFactory = requestFactory;
        this.messageBundle = messageBundle;

        getView().setUiHandlers(this);
    }

    @Override
    public void saveFiliere(FiliereProxy filiere) {
        currentContext.addFiliere(filiere).fire(new ReceiverImpl<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                Message message = new Message.Builder(messageBundle.filiereAddedSuccess())
                        .style(AlertType.SUCCESS).build();
                MessageEvent.fire(this, message);
                SystemScolaireChangedEvent.fire(this);

                getView().hide();
            }
        });
    }

    @Override
    protected void onReveal() {
        currentContext = requestFactory.settingsService();
        getView().edit(currentContext.create(FiliereProxy.class));
    }
}
