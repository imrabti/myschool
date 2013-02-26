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

package com.gsr.myschool.back.client.web.application.settings.popup;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.common.client.mvp.ValidatedPopupView;
import com.gsr.myschool.common.client.proxy.MatiereExamenProxy;
import com.gsr.myschool.common.client.proxy.NiveauEtudeProxy;
import com.gsr.myschool.common.client.proxy.PieceJustifProxy;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gwtplatform.mvp.client.PresenterWidget;

import java.util.List;

public class NiveauEtudeInfosPresenter extends PresenterWidget<NiveauEtudeInfosPresenter.MyView> {
    public interface MyView extends ValidatedPopupView {
        void setNiveauEtudeTitle(String niveauEtudeTitle);

        void setDataPieceJustf(List<PieceJustifProxy> response);

        void setDataMatiereExamen(List<MatiereExamenProxy> response);
    }

    private final BackRequestFactory requestFactory;

    private NiveauEtudeProxy currentNiveauEtude;

    @Inject
    public NiveauEtudeInfosPresenter(final EventBus eventBus, final MyView view,
                                     final BackRequestFactory requestFactory) {
        super(eventBus, view);

        this.requestFactory = requestFactory;
    }

    public void setCurrentNiveauEtude(NiveauEtudeProxy currentNiveauEtude) {
        this.currentNiveauEtude = currentNiveauEtude;
    }

    @Override
    protected void onReveal() {
        getView().setNiveauEtudeTitle(currentNiveauEtude.getNom());
        fillCellList();
    }

    private void fillCellList() {
        requestFactory.niveauEtudeService().getMatiereExamenByNiveau(currentNiveauEtude.getId())
                .fire(new ReceiverImpl<List<MatiereExamenProxy>>() {
                    @Override
                    public void onSuccess(List<MatiereExamenProxy> response) {
                        getView().setDataMatiereExamen(response);
                    }
                });
        requestFactory.niveauEtudeService().getPieceJustfByNiveau(currentNiveauEtude.getId())
                .fire(new ReceiverImpl<List<PieceJustifProxy>>() {
                    @Override
                    public void onSuccess(List<PieceJustifProxy> response) {
                        getView().setDataPieceJustf(response);
                    }
                });
    }

}
