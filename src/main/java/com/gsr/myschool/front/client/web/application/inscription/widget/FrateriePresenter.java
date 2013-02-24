package com.gsr.myschool.front.client.web.application.inscription.widget;

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.mvp.ValidatedView;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.client.proxy.EtablissementScolaireProxy;
import com.gsr.myschool.common.client.proxy.FraterieDTOProxy;
import com.gsr.myschool.common.client.proxy.FraterieProxy;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.request.ValidatedReceiverImpl;
import com.gsr.myschool.common.client.widget.messages.CloseDelay;
import com.gsr.myschool.common.client.widget.messages.Message;
import com.gsr.myschool.common.client.widget.messages.event.MessageEvent;
import com.gsr.myschool.front.client.place.NameTokens;
import com.gsr.myschool.front.client.request.FrontRequestFactory;
import com.gsr.myschool.front.client.request.InscriptionRequest;
import com.gsr.myschool.front.client.resource.message.MessageBundle;
import com.gsr.myschool.front.client.web.application.inscription.WizardStep;
import com.gsr.myschool.front.client.web.application.inscription.event.ChangeStepEvent;
import com.gsr.myschool.front.client.web.application.inscription.event.EtablissementSelectedEvent;
import com.gsr.myschool.front.client.web.application.inscription.event.EtablissementSelectedEvent.EtablissementSelectedHandler;
import com.gsr.myschool.front.client.web.application.inscription.popup.EtablissementFilterPresenter;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;

public class FrateriePresenter extends PresenterWidget<FrateriePresenter.MyView>
        implements FraterieUiHandlers, ChangeStepEvent.ChangeStepHandler, EtablissementSelectedHandler {
    public interface MyView extends ValidatedView, HasUiHandlers<FraterieUiHandlers> {
        void setData(List<FraterieProxy> data);

        void editFraterie(FraterieDTOProxy fraterie);

        void setEtablissement(EtablissementScolaireProxy selectedEtablissement);
    }

    private final FrontRequestFactory requestFactory;
    private final PlaceManager placeManager;
    private final MessageBundle messageBundle;
    private final EtablissementFilterPresenter etablissementFilterPresenter;

    private InscriptionRequest currentContext;
    private DossierProxy currentDossier;
    private FraterieDTOProxy currentFraterie;
    private Boolean fraterieViolation;
    private EtablissementScolaireProxy currentEtablissement;

    @Inject
    public FrateriePresenter(final EventBus eventBus, final MyView view,
                             final FrontRequestFactory requestFactory,
                             final PlaceManager placeManager,
                             final EtablissementFilterPresenter etablissementFilterPresenter,
                             final MessageBundle messageBundle) {
        super(eventBus, view);

        this.requestFactory = requestFactory;
        this.etablissementFilterPresenter = etablissementFilterPresenter;
        this.messageBundle = messageBundle;
        this.placeManager = placeManager;

        getView().setUiHandlers(this);
    }

    @Override
    public void onChangeStep(ChangeStepEvent event) {
        if (event.getCurrentStep() == WizardStep.STEP_5) {
            Message message = new Message.Builder(messageBundle.newInscriptionSuccess())
                    .style(AlertType.SUCCESS).build();
            MessageEvent.fire(this, message);
            placeManager.revealPlace(new PlaceRequest(NameTokens.getInscription()));
        }
    }

    @Override
    public void addFraterie(FraterieDTOProxy fraterie) {
        Long dossierId = currentDossier.getId();

        if (currentEtablissement != null) {
            fraterie.setEtablissement(currentContext.edit(currentEtablissement));
        }

        if (fraterie.getNiveau() != null) {
            fraterie.setNiveau(currentContext.edit(fraterie.getNiveau()));
        }

        if (fraterie.getScolarise()) {
            if (fraterie.getEtablissement() == null || fraterie.getFiliere() == null || fraterie.getNiveau() == null) {
                Message message = new Message.Builder(messageBundle.fraterieFaillure())
                        .style(AlertType.ERROR).closeDelay(CloseDelay.DEFAULT).build();

                MessageEvent.fire(this, message);
                return;
            }
        }

        if (!fraterie.getScolarise()) {
            fraterie.setNiveau(null);
            fraterie.setFiliere(null);
            fraterie.setEtablissement(null);
        }

        if (!fraterieViolation) {
            currentContext.createNewFraterie(fraterie, dossierId).fire(new ValidatedReceiverImpl<Void>() {
                @Override
                public void onValidationError(Set<ConstraintViolation<?>> violations) {
                    getView().clearErrors();
                    getView().showErrors(violations);
                    fraterieViolation = true;
                }

                @Override
                public void onSuccess(Void response) {
                    currentContext = requestFactory.inscriptionService();
                    currentFraterie = currentContext.create(FraterieDTOProxy.class);
                    currentEtablissement = null;
                    fraterieViolation = false;

                    getView().editFraterie(currentFraterie);
                    getView().setEtablissement(currentEtablissement);
                    getView().clearErrors();
                    loadFraterie();
                }
            });
        } else {
            currentContext.fire();
        }
    }

    @Override
    public void deleteFraterie(FraterieProxy fraterie) {
        if (Window.confirm(messageBundle.deleteFraterieConf())) {
            Long fraterieId = fraterie.getId();
            requestFactory.inscriptionService().deleteFraterie(fraterieId).fire(new ReceiverImpl<Void>() {
                @Override
                public void onSuccess(Void response) {
                    Message message = new Message.Builder(messageBundle.deleteFraterieSuccess())
                            .style(AlertType.SUCCESS).closeDelay(CloseDelay.DEFAULT).build();
                    MessageEvent.fire(this, message);
                    loadFraterie();
                }
            });
        }
    }

    @Override
    public void selectEtablissement() {
        etablissementFilterPresenter.setTargetStep(WizardStep.STEP_5);
        addToPopupSlot(etablissementFilterPresenter);
    }

    @Override
    public void onEtablissementSelected(EtablissementSelectedEvent event) {
        if (event.getStep() == WizardStep.STEP_5) {
            getView().setEtablissement(event.getSelectedEtablissement());
            currentEtablissement = event.getSelectedEtablissement();
        }
    }

    public void editData(DossierProxy dossierProxy) {
        currentDossier = dossierProxy;
        currentContext = requestFactory.inscriptionService();
        currentFraterie = currentContext.create(FraterieDTOProxy.class);
        fraterieViolation = false;

        getView().editFraterie(currentFraterie);
        loadFraterie();
    }

    @Override
    protected void onBind() {
        addRegisteredHandler(ChangeStepEvent.getType(), this);
        addRegisteredHandler(EtablissementSelectedEvent.getType(), this);
    }

    private void loadFraterie() {
        Long dossierId = currentDossier.getId();
        requestFactory.inscriptionService().findFraterieByDossierId(dossierId).fire(new ReceiverImpl<List<FraterieProxy>>() {
            @Override
            public void onSuccess(List<FraterieProxy> result) {
                getView().setData(result);
            }
        });
    }
}
