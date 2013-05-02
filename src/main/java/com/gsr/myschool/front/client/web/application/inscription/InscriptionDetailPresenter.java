package com.gsr.myschool.front.client.web.application.inscription;

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.proxy.*;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.request.ReportRequestBuilder;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;
import com.gsr.myschool.common.client.security.LoggedInGatekeeper;
import com.gsr.myschool.common.client.widget.messages.CloseDelay;
import com.gsr.myschool.common.client.widget.messages.Message;
import com.gsr.myschool.common.client.widget.messages.event.MessageEvent;
import com.gsr.myschool.common.shared.constants.GlobalParameters;
import com.gsr.myschool.common.shared.exception.InscriptionClosedException;
import com.gsr.myschool.common.shared.exception.UnAuthorizedException;
import com.gsr.myschool.common.shared.type.ParentType;
import com.gsr.myschool.front.client.place.NameTokens;
import com.gsr.myschool.front.client.request.FrontRequestFactory;
import com.gsr.myschool.front.client.resource.message.MessageBundle;
import com.gsr.myschool.front.client.web.application.ApplicationPresenter;
import com.gsr.myschool.front.client.web.application.inscription.InscriptionDetailPresenter.MyProxy;
import com.gsr.myschool.front.client.web.application.inscription.InscriptionDetailPresenter.MyView;
import com.gsr.myschool.front.client.web.application.inscription.event.DossierSubmitEvent;
import com.gsr.myschool.front.client.web.application.inscription.popup.ConfirmationInscriptionPresenter;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InscriptionDetailPresenter extends Presenter<MyView, MyProxy>
        implements InscriptionDetailUiHandlers, DossierSubmitEvent.DossierSubmitHandler {
    public interface MyView extends View, HasUiHandlers<InscriptionDetailUiHandlers> {
        void setDossier(DossierProxy dossier);

        void setResponsable(Map<ParentType, InfoParentProxy> infoParents);

        void setCandidat(CandidatProxy candidat);

        void setScolariteActuelle(ScolariteActuelleProxy scolariteActuelle);

        void setFraterie(List<FraterieProxy> fraterieList);

        void setStatusInscription(Boolean opened);
    }

    @ProxyStandard
    @NameToken(NameTokens.inscriptiondetail)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface MyProxy extends ProxyPlace<InscriptionDetailPresenter> {
    }

    private final FrontRequestFactory requestFactory;
    private final PlaceManager placeManager;
    private final MessageBundle messageBundle;
    private final SharedMessageBundle sharedMessageBundle;
    private final ConfirmationInscriptionPresenter confirmationInscriptionPresenter;

    private DossierProxy currentDossier;

    @Inject
    public InscriptionDetailPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
                                      final FrontRequestFactory requestFactory,
                                      final PlaceManager placeManager,
                                      final MessageBundle messageBundle,
                                      final SharedMessageBundle sharedMessageBundle,
                                      final ConfirmationInscriptionPresenter confirmationInscriptionPresenter) {
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);

        this.requestFactory = requestFactory;
        this.placeManager = placeManager;
        this.messageBundle = messageBundle;
        this.sharedMessageBundle = sharedMessageBundle;
        this.confirmationInscriptionPresenter = confirmationInscriptionPresenter;

        getView().setUiHandlers(this);
    }

    @Override
    public void onBind() {
        addRegisteredHandler(DossierSubmitEvent.getSecondType(), this);
    }

    @Override
    public void prepareFromRequest(PlaceRequest placeRequest) {
        Long dossierId = Long.parseLong(placeRequest.getParameter("id", null));
        requestFactory.inscriptionService().findDossierById(dossierId).fire(new ReceiverImpl<DossierProxy>() {
            @Override
            public void onSuccess(DossierProxy result) {
                getStatusInscription();

                currentDossier = result;
                getView().setDossier(currentDossier);
                getView().setCandidat(currentDossier.getCandidat());
                getView().setScolariteActuelle(currentDossier.getScolariteActuelle());

                loadInfoParent();
                loadFraterie();
            }

            @Override
            public void onException(String type) {
                if (type.equals(UnAuthorizedException.class.getName())) {
                    Message message = new Message.Builder(sharedMessageBundle.unAuthorized())
                            .style(AlertType.ERROR).build();
                    MessageEvent.fire(this, message);
                    placeManager.revealPlace(new PlaceRequest(NameTokens.getInscription()));
                }
            }
        });
    }

    @Override
    public void onDossierSubmit(DossierSubmitEvent event) {
        if (event.getAgreement()) {
            Long dossierId = currentDossier.getId();
            requestFactory.inscriptionService().submitInscription(dossierId, false).fire(new ReceiverImpl<List<String>>() {
                @Override
                public void onSuccess(List<String> response) {
                    if (response.isEmpty()) {
                        Message message = new Message.Builder(messageBundle.inscriptionSubmitSuccess())
                                .style(AlertType.SUCCESS).closeDelay(CloseDelay.NEVER).build();
                        MessageEvent.fire(this, message);
                    } else {
                        for (String error : response) {
                            Message message = new Message.Builder(error).style(AlertType.ERROR)
                                    .closeDelay(CloseDelay.NEVER).build();
                            MessageEvent.fire(this, message);
                        }
                    }
                }

                @Override
                public void onException(String type) {
                    if (InscriptionClosedException.class.getName().equals(type)) {
                        Message message = new Message.Builder(messageBundle.inscriptionClosed())
                                .style(AlertType.ERROR).closeDelay(CloseDelay.NEVER).build();
                        MessageEvent.fire(this, message);

                        getStatusInscription();
                    }
                }
            });
        }
    }

    @Override
    public void submitInscription() {
        confirmationInscriptionPresenter.setSource(this);
        addToPopupSlot(confirmationInscriptionPresenter);
    }

    @Override
    public void printInscription() {
        ReportRequestBuilder requestBuilder = new ReportRequestBuilder();
        requestBuilder.buildData(currentDossier.getId().toString());
        requestBuilder.sendRequest();
    }

    @Override
    public void editInscription() {
        PlaceRequest placeRequest = new PlaceRequest(NameTokens.getEditInscription());
        placeRequest = placeRequest.with("id", currentDossier.getId().toString());
        placeManager.revealPlace(placeRequest);
    }

    private void loadInfoParent() {
        Long dossierId = currentDossier.getId();
        requestFactory.inscriptionService().findInfoParentByDossierId(dossierId).fire(
                new ReceiverImpl<List<InfoParentProxy>>() {
                    @Override
                    public void onSuccess(List<InfoParentProxy> result) {
                        Map<ParentType, InfoParentProxy> infoParents = new HashMap<ParentType, InfoParentProxy>();
                        for (InfoParentProxy infoParent : result) {
                            infoParents.put(infoParent.getParentType(), infoParent);
                        }

                        getView().setResponsable(infoParents);
                    }
                });
    }

    private void loadFraterie() {
        Long dossierId = currentDossier.getId();
        requestFactory.inscriptionService().findFraterieByDossierId(dossierId).fire(
                new ReceiverImpl<List<FraterieProxy>>() {
                    @Override
                    public void onSuccess(List<FraterieProxy> result) {
                        getView().setFraterie(result);
                    }
                });
    }

    private void getStatusInscription() {
        requestFactory.inscriptionService().statusInscriptionOpened().fire(new ReceiverImpl<Boolean>() {
            @Override
            public void onSuccess(final Boolean response) {
                requestFactory.inscriptionService().statusFilieresGeneralesOpened().fire(new ReceiverImpl<Boolean>() {
                    /* this is shit >_> */
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        getView().setStatusInscription(aBoolean && response ||
                                !aBoolean
                                        && response
                                        && currentDossier.getNiveauEtude() != null
                                        && currentDossier.getNiveauEtude().getFiliere() != null
                                        && currentDossier.getNiveauEtude().getFiliere().getId() >= GlobalParameters.PREPA_FILIERE_FROM
                                || !aBoolean
                                && response
                                && currentDossier.getNiveauEtude() == null);
                    }
                });
            }
        });
    }
}
