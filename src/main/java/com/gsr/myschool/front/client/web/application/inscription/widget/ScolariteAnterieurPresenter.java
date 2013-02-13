package com.gsr.myschool.front.client.web.application.inscription.widget;

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.mvp.ValidatedView;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.client.proxy.ScolariteAnterieurDTOProxy;
import com.gsr.myschool.common.client.proxy.ScolariteAnterieurProxy;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.request.ValidatedReceiverImpl;
import com.gsr.myschool.common.client.widget.messages.CloseDelay;
import com.gsr.myschool.common.client.widget.messages.Message;
import com.gsr.myschool.common.client.widget.messages.event.MessageEvent;
import com.gsr.myschool.front.client.request.FrontRequestFactory;
import com.gsr.myschool.front.client.request.InscriptionRequest;
import com.gsr.myschool.front.client.resource.message.MessageBundle;
import com.gsr.myschool.front.client.web.application.inscription.WizardStep;
import com.gsr.myschool.front.client.web.application.inscription.event.ChangeStepEvent;
import com.gsr.myschool.front.client.web.application.inscription.event.DisplayStepEvent;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;

public class ScolariteAnterieurPresenter extends PresenterWidget<ScolariteAnterieurPresenter.MyView>
        implements ScolariteAnterieurUiHandlers, ChangeStepEvent.ChangeStepHandler  {
    public interface MyView extends ValidatedView, HasUiHandlers<ScolariteAnterieurUiHandlers> {
        void editScolariteAnterieur(ScolariteAnterieurDTOProxy scolariteAnterieur);

        void setData(List<ScolariteAnterieurProxy> data);
    }

    private final FrontRequestFactory requestFactory;
    private final MessageBundle messageBundle;

    private InscriptionRequest currentContext;
    private DossierProxy currentDossier;
    private ScolariteAnterieurDTOProxy currentScolarite;
    private Boolean scolariteAnterieurViolation;

    @Inject
    public ScolariteAnterieurPresenter(final EventBus eventBus, final MyView view,
                                       final FrontRequestFactory requestFactory,
                                       final MessageBundle messageBundle) {
        super(eventBus, view);

        this.requestFactory = requestFactory;
        this.messageBundle = messageBundle;

        getView().setUiHandlers(this);
    }

    @Override
    public void onChangeStep(ChangeStepEvent event) {
        if (event.getCurrentStep() == WizardStep.STEP_4) {
            DisplayStepEvent.fire(this, event.getNextStep());
        }
    }

    @Override
    public void addScolariteAnterieur(ScolariteAnterieurDTOProxy scolariteAnterieur) {
        Long dossierId = currentDossier.getId();

        if (scolariteAnterieur.getEtablissement() != null) {
            scolariteAnterieur.setEtablissement(currentContext.edit(scolariteAnterieur.getEtablissement()));
        }

        if (scolariteAnterieur.getAnneeScolaire() != null) {
            scolariteAnterieur.setAnneeScolaire(currentContext.edit(scolariteAnterieur.getAnneeScolaire()));
        }

        if (!scolariteAnterieurViolation) {
            currentContext.createNewScolariteAnterieur(scolariteAnterieur, dossierId).fire(
                    new ValidatedReceiverImpl<Void>() {
                @Override
                public void onValidationError(Set<ConstraintViolation<?>> violations) {
                    getView().clearErrors();
                    getView().showErrors(violations);
                    scolariteAnterieurViolation = true;
                }

                @Override
                public void onSuccess(Void response) {
                    currentContext = requestFactory.inscriptionService();
                    currentScolarite = currentContext.create(ScolariteAnterieurDTOProxy.class);
                    scolariteAnterieurViolation = false;

                    getView().editScolariteAnterieur(currentScolarite);
                    getView().clearErrors();
                    loadScolariteAnterieur();
                }
            });
        } else {
            currentContext.fire();
        }
    }

    @Override
    public void deleteScolariteAnterieur(ScolariteAnterieurProxy scolariteAnterieur) {
        if (Window.confirm(messageBundle.deleteScolariteAnterieurConf())) {
            Long scolariteId = scolariteAnterieur.getId();
            requestFactory.inscriptionService().deleteScolariteAnterieur(scolariteId).fire(new ReceiverImpl<Void>() {
                @Override
                public void onSuccess(Void response) {
                    Message message = new Message.Builder(messageBundle.deleteScolariteAnterieurSuccess())
                                .style(AlertType.SUCCESS).closeDelay(CloseDelay.DEFAULT).build();
                    MessageEvent.fire(this, message);
                    loadScolariteAnterieur();
                }
            });
        }
    }

    public void editData(DossierProxy dossierProxy) {
        currentDossier = dossierProxy;
        currentContext = requestFactory.inscriptionService();
        currentScolarite = currentContext.create(ScolariteAnterieurDTOProxy.class);
        scolariteAnterieurViolation = false;

        getView().editScolariteAnterieur(currentScolarite);
        loadScolariteAnterieur();
    }

    @Override
    protected void onBind() {
        addRegisteredHandler(ChangeStepEvent.getType(), this);
    }

    private void loadScolariteAnterieur() {
        Long dossierId = currentDossier.getId();
        requestFactory.inscriptionService().findScolariteAnterieursByDossierId(dossierId).fire(
                new ReceiverImpl<List<ScolariteAnterieurProxy>>() {
            @Override
            public void onSuccess(List<ScolariteAnterieurProxy> result) {
                getView().setData(result);
            }
        });
    }
}
