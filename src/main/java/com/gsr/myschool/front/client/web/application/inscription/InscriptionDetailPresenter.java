package com.gsr.myschool.front.client.web.application.inscription;

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.proxy.CandidatProxy;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.client.proxy.FraterieProxy;
import com.gsr.myschool.common.client.proxy.InfoParentProxy;
import com.gsr.myschool.common.client.proxy.ScolariteActuelleProxy;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.security.LoggedInGatekeeper;
import com.gsr.myschool.common.client.widget.messages.CloseDelay;
import com.gsr.myschool.common.client.widget.messages.Message;
import com.gsr.myschool.common.client.widget.messages.event.MessageEvent;
import com.gsr.myschool.common.shared.type.ParentType;
import com.gsr.myschool.front.client.place.NameTokens;
import com.gsr.myschool.front.client.request.FrontRequestFactory;
import com.gsr.myschool.front.client.resource.message.MessageBundle;
import com.gsr.myschool.front.client.web.application.ApplicationPresenter;
import com.gsr.myschool.front.client.web.application.inscription.InscriptionDetailPresenter.MyView;
import com.gsr.myschool.front.client.web.application.inscription.InscriptionDetailPresenter.MyProxy;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InscriptionDetailPresenter extends Presenter<MyView, MyProxy> implements InscriptionDetailUiHandlers {
    public interface MyView extends View, HasUiHandlers<InscriptionDetailUiHandlers> {
        void setDossier(DossierProxy dossier);

        void setResponsable(Map<ParentType, InfoParentProxy> infoParents);

        void setCandidat(CandidatProxy candidat);

        void setFraterie(List<FraterieProxy> fraterieList);

        void showErrors(List<String> errors);

        void clearErrors();
    }

    @ProxyStandard
    @NameToken(NameTokens.inscriptiondetail)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface MyProxy extends ProxyPlace<InscriptionDetailPresenter> {
    }

    private final FrontRequestFactory requestFactory;
    private final MessageBundle messageBundle;

    private DossierProxy currentDossier;

    @Inject
    public InscriptionDetailPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
                                      final FrontRequestFactory requestFactory,
                                      final MessageBundle messageBundle) {
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);

        this.requestFactory = requestFactory;
        this.messageBundle = messageBundle;

        getView().setUiHandlers(this);
    }

    @Override
    public void prepareFromRequest(PlaceRequest placeRequest) {
        Long dossierId = Long .parseLong(placeRequest.getParameter("id", null));
        requestFactory.inscriptionService().findDossierById(dossierId).fire(new ReceiverImpl<DossierProxy>() {
            @Override
            public void onSuccess(DossierProxy result) {
                currentDossier = result;
                getView().setDossier(currentDossier);
                getView().setCandidat(currentDossier.getCandidat());
                getView().clearErrors();

                loadInfoParent();
                loadFraterie();
            }
        });
    }

    @Override
    public void submitInscription() {
        if (Window.confirm(messageBundle.inscriptionSubmitConf())) {
            Long dossierId = currentDossier.getId();
            requestFactory.inscriptionService().submitInscription(dossierId).fire(new ReceiverImpl<List<String>>() {
                @Override
                public void onSuccess(List<String> response) {
                    if (response.isEmpty()) {
                        Message message = new Message.Builder(messageBundle.inscriptionSubmitSuccess())
                                .style(AlertType.SUCCESS).closeDelay(CloseDelay.DEFAULT).build();
                        MessageEvent.fire(this, message);
                        getView().clearErrors();
                    } else {
                        getView().showErrors(response);
                    }
                }
            });
        }
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
}
