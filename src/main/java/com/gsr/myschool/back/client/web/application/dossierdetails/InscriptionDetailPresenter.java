/**
 * Copyright 2012 Nuvola Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.gsr.myschool.back.client.web.application.dossierdetails;

import com.google.gwt.user.client.History;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.place.NameTokens;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.back.client.web.application.ApplicationPresenter;
import com.gsr.myschool.common.client.proxy.*;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.security.HasRoleGatekeeper;
import com.gsr.myschool.common.shared.constants.GlobalParameters;
import com.gsr.myschool.common.shared.type.DossierStatus;
import com.gsr.myschool.common.shared.type.ParentType;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.GatekeeperParams;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InscriptionDetailPresenter extends Presenter<InscriptionDetailPresenter.MyView, InscriptionDetailPresenter.MyProxy>
        implements InscriptionDetailUiHandlers {
    public interface MyView extends View, HasUiHandlers<InscriptionDetailUiHandlers> {
        void setDossier(DossierProxy dossier);

        void setResponsable(Map<ParentType, InfoParentProxy> infoParents);

        void setCandidat(CandidatProxy candidat);

        void setScolariteActuelle(ScolariteActuelleProxy scolariteActuelle);

        void setFraterie(List<FraterieProxy> fraterieList);

        void setPiecesJustificatives(List<PiecejustifDTOProxy> piecesJustificatives);

        void hidePiecesJustificatives();
    }

    @ProxyStandard
    @NameToken(NameTokens.inscriptiondetail)
    @UseGatekeeper(HasRoleGatekeeper.class)
    @GatekeeperParams({GlobalParameters.ROLE_ADMIN, GlobalParameters.ROLE_OPERATOR})
    public interface MyProxy extends ProxyPlace<InscriptionDetailPresenter> {
    }

    private final BackRequestFactory requestFactory;

    private DossierProxy currentDossier;

    @Inject
    public InscriptionDetailPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
                                      final BackRequestFactory requestFactory) {
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);

        this.requestFactory = requestFactory;

        getView().setUiHandlers(this);
    }

    @Override
    public void prepareFromRequest(PlaceRequest placeRequest) {
        Long dossierId = Long.parseLong(placeRequest.getParameter("id", null));
        requestFactory.inscriptionService().findDossierById(dossierId).fire(new ReceiverImpl<DossierProxy>() {
            @Override
            public void onSuccess(DossierProxy result) {
                currentDossier = result;
                getView().setDossier(currentDossier);
                getView().setCandidat(currentDossier.getCandidat());
                getView().setScolariteActuelle(currentDossier.getScolariteActuelle());

                loadInfoParent();
                loadFraterie();
                loadPiecesJustificatives();
            }
        });
    }

    @Override
    public void retour() {
        History.back();
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

    private void loadPiecesJustificatives() {
        if (currentDossier.getStatus() == DossierStatus.STANDBY) {
            requestFactory.dossierService().getPieceJustifFromProcessStandby(currentDossier)
                    .fire(new ReceiverImpl<List<PiecejustifDTOProxy>>() {
                        @Override
                        public void onSuccess(List<PiecejustifDTOProxy> result) {
                            getView().setPiecesJustificatives(result);
                        }
                    });
        } else {
            getView().hidePiecesJustificatives();
        }
    }
}
