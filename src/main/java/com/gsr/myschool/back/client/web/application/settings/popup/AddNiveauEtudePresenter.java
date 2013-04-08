package com.gsr.myschool.back.client.web.application.settings.popup;

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.back.client.request.SettingsRequest;
import com.gsr.myschool.back.client.resource.message.MessageBundle;
import com.gsr.myschool.back.client.web.application.settings.event.SystemScolaireChangedEvent;
import com.gsr.myschool.common.client.proxy.NiveauEtudeProxy;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.util.EditorView;
import com.gsr.myschool.common.client.widget.messages.Message;
import com.gsr.myschool.common.client.widget.messages.event.MessageEvent;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;

public class AddNiveauEtudePresenter extends PresenterWidget<AddNiveauEtudePresenter.MyView>
        implements AddNiveauEtudeUiHandlers  {
    public interface MyView extends PopupView, HasUiHandlers<AddNiveauEtudeUiHandlers>, EditorView<NiveauEtudeProxy> {
    }

    private final BackRequestFactory requestFactory;
    private final MessageBundle messageBundle;

    private SettingsRequest currentContext;

    @Inject
    public AddNiveauEtudePresenter(final EventBus eventBus, final MyView view,
                                   final BackRequestFactory requestFactory,
                                   final MessageBundle messageBundle) {
        super(eventBus, view);

        this.requestFactory = requestFactory;
        this.messageBundle = messageBundle;

        getView().setUiHandlers(this);
    }

    @Override
    public void saveNiveauEtude(NiveauEtudeProxy niveauEtude) {
        if (niveauEtude.getFiliere() != null) {
            niveauEtude.setFiliere(currentContext.edit(niveauEtude.getFiliere()));
        }

        currentContext.addNiveauEtude(niveauEtude).fire(new ReceiverImpl<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                Message message = new Message.Builder(messageBundle.niveauEtudeAddedSuccess())
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
        getView().edit(currentContext.create(NiveauEtudeProxy.class));
    }
}
