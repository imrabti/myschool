package com.gsr.myschool.front.client.web.application.inscription.popup;

import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.front.client.web.application.inscription.event.DossierSubmitEvent;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gsr.myschool.front.client.web.application.inscription.popup.ConfirmationInscriptionPresenter.MyView;

public class ConfirmationInscriptionPresenter extends PresenterWidget<MyView>
        implements ConfirmationInscriptionUiHandlers {
    public interface MyView extends PopupView, HasUiHandlers<ConfirmationInscriptionUiHandlers> {
        void initConfirmation();
    }

    private HasHandlers source;

    @Inject
    public ConfirmationInscriptionPresenter(final EventBus eventBus, final MyView view) {
        super(eventBus, view);

        getView().setUiHandlers(this);
    }

    @Override
    public void validateInscription(Boolean agreeInformations) {
        DossierSubmitEvent.fire(source, agreeInformations);
        getView().hide();
    }

    public void setSource(HasHandlers source) {
        this.source = source;
    }

    protected void onReveal() {
        getView().initConfirmation();
    }
}
