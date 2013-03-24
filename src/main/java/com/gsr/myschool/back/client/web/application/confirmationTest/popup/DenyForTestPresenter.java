package com.gsr.myschool.back.client.web.application.confirmationTest.popup;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.request.DossierServiceRequest;
import com.gsr.myschool.common.client.mvp.ValidatedPopupView;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;

public class DenyForTestPresenter extends PresenterWidget<DenyForTestPresenter.MyView>
        implements DenyForTestUiHandlers {
    public interface MyView extends ValidatedPopupView, HasUiHandlers<DenyForTestUiHandlers> {
    }

    private DossierServiceRequest currentContext;
    private DossierProxy currentDossier;

    @Inject
    public DenyForTestPresenter(final EventBus eventBus, final MyView view) {
        super(eventBus, view);

        getView().setUiHandlers(this);
    }

    @Override
    public void loadDossier(DossierProxy dossierProxy) {
        currentDossier = dossierProxy;
    }

    @Override
    public void saveReason(String reason) {
    }
}
