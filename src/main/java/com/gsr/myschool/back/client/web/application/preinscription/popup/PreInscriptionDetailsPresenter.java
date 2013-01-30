package com.gsr.myschool.back.client.web.application.preinscription.popup;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.mvp.ValidatedPopupView;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;

public class PreInscriptionDetailsPresenter extends PresenterWidget<PreInscriptionDetailsPresenter.MyView>
        implements PreInscriptionDetailsUiHandlers {
    public interface MyView extends ValidatedPopupView, HasUiHandlers<PreInscriptionDetailsUiHandlers> {
	}

    @Inject
    public PreInscriptionDetailsPresenter(final EventBus eventBus, final MyView view) {
        super(eventBus, view);

        getView().setUiHandlers(this);
    }

    public void editDrivers(DossierProxy dossier) {
    }
}
