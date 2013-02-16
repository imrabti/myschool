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
import com.gsr.myschool.front.client.request.FrontRequestFactory;
import com.gsr.myschool.front.client.request.InscriptionRequest;
import com.gsr.myschool.front.client.resource.message.MessageBundle;
import com.gsr.myschool.front.client.web.application.inscription.WizardStep;
import com.gsr.myschool.front.client.web.application.inscription.event.ChangeStepEvent;
import com.gsr.myschool.front.client.web.application.inscription.event.DisplayStepEvent;
import com.gwtplatform.mvp.client.PresenterWidget;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ParentPresenter extends PresenterWidget<ParentPresenter.MyView>
        implements ChangeStepEvent.ChangeStepHandler {
    public interface MyView extends ValidatedView {
        void showEditor(ParentType parentType);

        ParentType selectedTabs();

        void editPere(InfoParentProxy infoParent);

        void editMere(InfoParentProxy infoParent);

        void editTuteur(InfoParentProxy infoParent);

        void flushPere();

        void flushMere();

        void flushTuteur();
    }

    private final FrontRequestFactory requestFactory;
    private final MessageBundle messageBundle;

    private InscriptionRequest currentPereContext;
    private InfoParentProxy currentPere;
    private Boolean pereViolation;

    private InscriptionRequest currentMereContext;
    private InfoParentProxy currentMere;
    private Boolean mereViolation;

    private InscriptionRequest currentTuteurContext;
    private InfoParentProxy currentTuteur;
    private Boolean tuteurViolation;

    private List<ParentType> usedInfoParent;
    private Map<ParentType, Boolean> recievedCorrectly;

    @Inject
    public ParentPresenter(final EventBus eventBus, final MyView view,
                           final FrontRequestFactory requestFactory,
                           final MessageBundle messageBundle) {
        super(eventBus, view);

        this.requestFactory = requestFactory;
        this.messageBundle = messageBundle;
    }

    @Override
    public void onChangeStep(final ChangeStepEvent event) {
        if (event.getCurrentStep() == WizardStep.STEP_1) {
            usedInfoParent = new ArrayList<ParentType>();
            recievedCorrectly = new HashMap<ParentType, Boolean>();

            getView().flushPere();
            getView().flushMere();
            getView().flushTuteur();

            if (!isInfoParentEmpty(currentPere)
                    || !isInfoParentEmpty(currentMere)
                    || !isInfoParentEmpty(currentTuteur)) {
                if (!isInfoParentEmpty(currentPere)) {
                    usedInfoParent.add(ParentType.PERE);
                    recievedCorrectly.put(ParentType.PERE, false);
                    processPere(event.getNextStep());
                }

                if (!isInfoParentEmpty(currentMere)) {
                    usedInfoParent.add(ParentType.MERE);
                    recievedCorrectly.put(ParentType.MERE, false);
                    processMere(event.getNextStep());
                }

                if (!isInfoParentEmpty(currentTuteur)) {
                    usedInfoParent.add(ParentType.TUTEUR);
                    recievedCorrectly.put(ParentType.TUTEUR, false);
                    processTuteur(event.getNextStep());
                }

            } else {
                Message message = new Message.Builder(messageBundle.requiredInfoParentsError())
                        .style(AlertType.ERROR).closeDelay(CloseDelay.LONG).build();
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
        pereViolation = false;

        getView().editPere(currentPere);
    }

    private void prepareMere(InfoParentProxy mere) {
        currentMereContext = requestFactory.inscriptionService();
        currentMere = currentMereContext.edit(mere);
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
        if (!pereViolation) {
            currentPereContext.updateParent(currentPere).fire(new ValidatedReceiverImpl<InfoParentProxy>() {
                @Override
                public void onValidationError(Set<ConstraintViolation<?>> violations) {
                    getView().clearErrors();
                    getView().showErrors(violations);
                    getView().showEditor(ParentType.PERE);
                    recievedCorrectly.put(ParentType.PERE, false);
                    pereViolation = true;
                }

                @Override
                public void onSuccess(InfoParentProxy result) {
                    currentPereContext = requestFactory.inscriptionService();
                    currentPere = currentPereContext.edit(result);
                    recievedCorrectly.put(ParentType.PERE, true);
                    pereViolation = false;

                    getView().editPere(currentPere);

                    if (getView().selectedTabs()  == ParentType.PERE) {
                        getView().clearErrors();
                    }

                    if (canProceed()) {
                        DisplayStepEvent.fire(this, nextStep);
                    }
                }
            });
        } else {
            currentPereContext.fire();
        }
    }

    private void processMere(final WizardStep nextStep) {
        if (!mereViolation) {
            currentMereContext.updateParent(currentMere).fire(new ValidatedReceiverImpl<InfoParentProxy>() {
                @Override
                public void onValidationError(Set<ConstraintViolation<?>> violations) {
                    getView().clearErrors();
                    getView().showErrors(violations);
                    getView().showEditor(ParentType.MERE);
                    recievedCorrectly.put(ParentType.MERE, false);
                    mereViolation = true;
                }

                @Override
                public void onSuccess(InfoParentProxy result) {
                    currentMereContext = requestFactory.inscriptionService();
                    currentMere = currentMereContext.edit(result);
                    recievedCorrectly.put(ParentType.MERE, true);
                    mereViolation = false;

                    getView().editMere(currentMere);

                    if (getView().selectedTabs()  == ParentType.MERE) {
                        getView().clearErrors();
                    }

                    if (canProceed()) {
                        DisplayStepEvent.fire(this, nextStep);
                    }
                }
            });
        } else {
            currentMereContext.fire();
        }
    }

    private void processTuteur(final WizardStep nextStep) {
        if (!tuteurViolation) {
            currentTuteurContext.updateParent(currentTuteur).fire(new ValidatedReceiverImpl<InfoParentProxy>() {
                @Override
                public void onValidationError(Set<ConstraintViolation<?>> violations) {
                    getView().clearErrors();
                    getView().showErrors(violations);
                    getView().showEditor(ParentType.TUTEUR);
                    recievedCorrectly.put(ParentType.TUTEUR, false);
                    tuteurViolation = true;
                }

                @Override
                public void onSuccess(InfoParentProxy result) {
                    currentTuteurContext = requestFactory.inscriptionService();
                    currentTuteur = currentTuteurContext.edit(result);
                    recievedCorrectly.put(ParentType.TUTEUR, true);
                    tuteurViolation = false;

                    getView().editTuteur(currentTuteur);

                    if (getView().selectedTabs()  == ParentType.TUTEUR) {
                        getView().clearErrors();
                    }

                    if (canProceed()) {
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

    private Boolean canProceed() {
        for (ParentType parentType : usedInfoParent) {
            if (!recievedCorrectly.get(parentType)) {
                return false;
            }
        }
        return true;
    }
}
