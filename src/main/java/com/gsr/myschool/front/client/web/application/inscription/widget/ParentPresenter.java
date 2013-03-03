package com.gsr.myschool.front.client.web.application.inscription.widget;

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.mvp.ValidatedView;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.client.proxy.InfoParentProxy;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.request.ValidatedReceiverImpl;
import com.gsr.myschool.common.client.widget.messages.CloseDelay;
import com.gsr.myschool.common.client.widget.messages.Message;
import com.gsr.myschool.common.client.widget.messages.event.MessageEvent;
import com.gsr.myschool.common.shared.type.ParentType;
import com.gsr.myschool.front.client.place.NameTokens;
import com.gsr.myschool.front.client.request.FrontRequestFactory;
import com.gsr.myschool.front.client.request.InscriptionRequest;
import com.gsr.myschool.front.client.resource.message.MessageBundle;
import com.gsr.myschool.front.client.web.application.inscription.WizardStep;
import com.gsr.myschool.front.client.web.application.inscription.event.ChangeStepEvent;
import com.gsr.myschool.front.client.web.application.inscription.event.DisplayStepEvent;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;

public class ParentPresenter extends PresenterWidget<ParentPresenter.MyView>
        implements ChangeStepEvent.ChangeStepHandler {
    public interface MyView extends ValidatedView {
        void showEditor(ParentType parentType);

        void editPere(InfoParentProxy infoParent);

        void editMere(InfoParentProxy infoParent);

        void editTuteur(InfoParentProxy infoParent);

        void flushPere();

        void flushMere();

        void flushTuteur();

        ParentType selectedTab();
    }

    private final FrontRequestFactory requestFactory;
    private final MessageBundle messageBundle;
    private final PlaceManager placeManager;

    private InscriptionRequest currentPereContext;
    private InfoParentProxy currentPere;
    private Boolean pereViolation;

    private InscriptionRequest currentMereContext;
    private InfoParentProxy currentMere;
    private Boolean mereViolation;

    private InscriptionRequest currentTuteurContext;
    private InfoParentProxy currentTuteur;
    private Boolean tuteurViolation;

    private Boolean pereValid;
    private Boolean mereValid;

    @Inject
    public ParentPresenter(final EventBus eventBus, final MyView view,
                           final FrontRequestFactory requestFactory,
                           final MessageBundle messageBundle,
                           final PlaceManager placeManager) {
        super(eventBus, view);

        this.requestFactory = requestFactory;
        this.messageBundle = messageBundle;
        this.placeManager = placeManager;
    }

    @Override
    public void onChangeStep(final ChangeStepEvent event) {
        if (event.getCurrentStep() == WizardStep.STEP_1) {
            getView().flushPere();
            getView().flushMere();
            getView().flushTuteur();

            if (!isInfoParentEmpty(currentTuteur)) {
                processTuteur(event.getNextStep());
            } else if (!isInfoParentEmpty(currentPere) && !isInfoParentEmpty(currentMere)) {
                processPere(event.getNextStep());
                processMere(event.getNextStep());
            } else {
                Message message = new Message.Builder(messageBundle.requiredInfoParentsError())
                        .style(AlertType.ERROR).build();
                MessageEvent.fire(this, message);
            }
        }
    }

    public void editData(DossierProxy dossierProxy) {
        Long dossierId = dossierProxy.getId();
        requestFactory.inscriptionService().findInfoParentByDossierId(dossierId).fire(
                new ReceiverImpl<List<InfoParentProxy>>() {
                    @Override
                    public void onSuccess(List<InfoParentProxy> result) {
                        for (InfoParentProxy infoParent : result) {
                            switch (infoParent.getParentType()) {
                                case PERE:
                                    preparePere(infoParent);
                                    break;
                                case MERE:
                                    prepareMere(infoParent);
                                    break;
                                case TUTEUR:
                                    prepareTuteur(infoParent);
                                    break;
                            }
                        }
                    }
                });

        getView().showEditor(ParentType.PERE);
    }

    @Override
    protected void onBind() {
        addRegisteredHandler(ChangeStepEvent.getType(), this);
    }

    private void preparePere(InfoParentProxy pere) {
        currentPereContext = requestFactory.inscriptionService();
        currentPere = currentPereContext.edit(pere);
        pereValid = false;
        pereViolation = false;

        getView().editPere(currentPere);
    }

    private void prepareMere(InfoParentProxy mere) {
        currentMereContext = requestFactory.inscriptionService();
        currentMere = currentMereContext.edit(mere);
        mereValid = false;
        mereViolation = false;

        getView().editMere(currentMere);
    }

    private void prepareTuteur(InfoParentProxy tuteur) {
        currentTuteurContext = requestFactory.inscriptionService();
        currentTuteur = currentTuteurContext.edit(tuteur);
        tuteurViolation = false;

        getView().editTuteur(currentTuteur);
    }

    private void processPere(final WizardStep nextStep) {
        if (currentPere.getParentGsr() &&
                (Strings.isNullOrEmpty(currentPere.getFormationGsr())
                        || Strings.isNullOrEmpty(currentPere.getPromotionGsr()))) {

            Message message = new Message.Builder(messageBundle.parentGsrFaillure())
                    .style(AlertType.ERROR)
                    .closeDelay(CloseDelay.DEFAULT)
                    .build();
            MessageEvent.fire(this, message);
            pereValid = false;
            return;
        }

        if (!currentPere.getParentGsr()) {
            currentPere.setPromotionGsr(null);
            currentPere.setFormationGsr(null);
        }

        if (!pereViolation) {
            if (currentPere.getNationality() != null) {
                currentPere.setNationality(currentPereContext.edit(currentPere.getNationality()));
            }

            currentPereContext.updateParent(currentPere).fire(new ValidatedReceiverImpl<InfoParentProxy>() {
                @Override
                public void onValidationError(Set<ConstraintViolation<?>> violations) {
                    getView().clearErrors();
                    getView().showEditor(ParentType.PERE);
                    getView().showErrors(violations);
                    pereValid = false;
                    pereViolation = true;
                }

                @Override
                public void onSuccess(InfoParentProxy result) {
                    currentPereContext = requestFactory.inscriptionService();
                    currentPere = currentPereContext.edit(result);
                    pereValid = true;
                    pereViolation = false;

                    getView().editPere(currentPere);

                    if (getView().selectedTab() == ParentType.PERE) {
                        getView().clearErrors();
                    }

                    if (pereValid && mereValid) {
                        if (nextStep == null) {
                            placeManager.revealPlace(new PlaceRequest(NameTokens.getInscription()));
                        } else {
                            DisplayStepEvent.fire(this, nextStep);
                        }
                    }
                }
            });
        } else {
            currentPereContext.fire();
        }
    }

    private void processMere(final WizardStep nextStep) {
        if (currentMere.getParentGsr() &&
                (Strings.isNullOrEmpty(currentMere.getFormationGsr())
                        || Strings.isNullOrEmpty(currentMere.getPromotionGsr()))) {

            Message message = new Message.Builder(messageBundle.parentGsrFaillure())
                    .style(AlertType.ERROR)
                    .closeDelay(CloseDelay.DEFAULT)
                    .build();
            MessageEvent.fire(this, message);
            mereValid = false;
            return;
        }

        if (!currentMere.getParentGsr()) {
            currentMere.setPromotionGsr(null);
            currentMere.setFormationGsr(null);
        }

        if (!mereViolation) {
            if (currentMere.getNationality() != null) {
                currentMere.setNationality(currentMereContext.edit(currentMere.getNationality()));
            }

            currentMereContext.updateParent(currentMere).fire(new ValidatedReceiverImpl<InfoParentProxy>() {
                @Override
                public void onValidationError(Set<ConstraintViolation<?>> violations) {
                    getView().clearErrors();
                    getView().showEditor(ParentType.MERE);
                    getView().showErrors(violations);
                    mereViolation = true;
                }

                @Override
                public void onSuccess(InfoParentProxy result) {
                    currentMereContext = requestFactory.inscriptionService();
                    currentMere = currentMereContext.edit(result);
                    mereValid = true;
                    mereViolation = false;

                    getView().editMere(currentMere);

                    if (getView().selectedTab() == ParentType.MERE) {
                        getView().clearErrors();
                    }

                    if (pereValid && mereValid) {
                        if (nextStep == null) {
                            placeManager.revealPlace(new PlaceRequest(NameTokens.getInscription()));
                        } else {
                            DisplayStepEvent.fire(this, nextStep);
                        }
                    }
                }
            });
        } else {
            currentMereContext.fire();
        }
    }

    private void processTuteur(final WizardStep nextStep) {
        if (currentTuteur.getParentGsr() &&
                (Strings.isNullOrEmpty(currentTuteur.getFormationGsr())
                        || Strings.isNullOrEmpty(currentTuteur.getPromotionGsr()))) {

            Message message = new Message.Builder(messageBundle.parentGsrFaillure())
                    .style(AlertType.ERROR)
                    .closeDelay(CloseDelay.DEFAULT)
                    .build();
            MessageEvent.fire(this, message);
            return;
        }

        if (!currentTuteur.getParentGsr()) {
            currentTuteur.setPromotionGsr(null);
            currentTuteur.setFormationGsr(null);
        }

        if (!tuteurViolation) {
            if (currentTuteur.getNationality() != null) {
                currentTuteur.setNationality(currentTuteurContext.edit(currentTuteur.getNationality()));
            }

            currentTuteurContext.updateParent(currentTuteur).fire(new ValidatedReceiverImpl<InfoParentProxy>() {
                @Override
                public void onValidationError(Set<ConstraintViolation<?>> violations) {
                    getView().showEditor(ParentType.TUTEUR);
                    getView().clearErrors();
                    getView().showErrors(violations);
                    tuteurViolation = true;
                }

                @Override
                public void onSuccess(InfoParentProxy result) {
                    currentTuteurContext = requestFactory.inscriptionService();
                    currentTuteur = currentTuteurContext.edit(result);
                    tuteurViolation = false;

                    getView().clearErrors();
                    getView().editTuteur(currentTuteur);

                    if (nextStep == null) {
                        placeManager.revealPlace(new PlaceRequest(NameTokens.getInscription()));
                    } else {
                        DisplayStepEvent.fire(this, nextStep);
                    }
                }
            });
        } else {
            currentTuteurContext.fire();
        }
    }

    private Boolean isInfoParentEmpty(InfoParentProxy infoParent) {
        return Strings.isNullOrEmpty(infoParent.getNom())
                && Strings.isNullOrEmpty(infoParent.getPrenom())
                && Strings.isNullOrEmpty(infoParent.getTelDom())
                && Strings.isNullOrEmpty(infoParent.getEmail());
    }
}
