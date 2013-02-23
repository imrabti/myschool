package com.gsr.myschool.front.client.web.application.inscription.popup;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.proxy.EtablissementFilterDTOProxy;
import com.gsr.myschool.common.client.proxy.EtablissementScolaireProxy;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.front.client.request.FrontRequestFactory;
import com.gsr.myschool.front.client.request.InscriptionRequest;
import com.gsr.myschool.front.client.web.application.inscription.WizardStep;
import com.gsr.myschool.front.client.web.application.inscription.event.EtablissementSelectedEvent;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gsr.myschool.front.client.web.application.inscription.popup.EtablissementFilterPresenter.MyView;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

import java.util.List;

public class EtablissementFilterPresenter extends PresenterWidget<MyView> implements EtablissementFilterUiHandlers {
    public interface MyView extends PopupView, HasUiHandlers<EtablissementFilterUiHandlers> {
        void setData(List<EtablissementScolaireProxy> data);

        void edit(EtablissementFilterDTOProxy object);
    }

    private final FrontRequestFactory requestFactory;
    private final PlaceManager placeManager;

    private WizardStep targetStep;
    private InscriptionRequest currentContext;

    @Inject
    public EtablissementFilterPresenter(final EventBus eventBus, final MyView view,
                                        final FrontRequestFactory requestFactory,
                                        final PlaceManager placeManager) {
        super(eventBus, view);

        this.requestFactory = requestFactory;
        this.placeManager = placeManager;

        getView().setUiHandlers(this);
    }

    @Override
    public void search(final EtablissementFilterDTOProxy etablissementFilter) {
        currentContext.findEtablissementByFilter(etablissementFilter).fire(
                new ReceiverImpl<List<EtablissementScolaireProxy>>() {
            @Override
            public void onSuccess(List<EtablissementScolaireProxy> response) {
                currentContext = requestFactory.inscriptionService();

                getView().edit(currentContext.edit(etablissementFilter));
                getView().setData(response);
            }
        });
    }

    @Override
    public void valueSelected(EtablissementScolaireProxy selectedValue) {
        EtablissementSelectedEvent.fire(this, selectedValue, targetStep);
        getView().hide();
    }

    public void setTargetStep(WizardStep targetStep) {
        this.targetStep = targetStep;
    }

    @Override
    protected void onReveal() {
        currentContext = requestFactory.inscriptionService();
        getView().edit(currentContext.create(EtablissementFilterDTOProxy.class));
    }
}
