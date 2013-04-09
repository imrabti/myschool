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

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.back.client.request.NiveauEtudeRequest;
import com.gsr.myschool.common.client.mvp.ValidatedPopupView;
import com.gsr.myschool.common.client.proxy.MatiereExamenProxy;
import com.gsr.myschool.common.client.proxy.NiveauEtudeProxy;
import com.gsr.myschool.common.client.proxy.PieceJustifProxy;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;
import com.gsr.myschool.common.client.widget.messages.Message;
import com.gsr.myschool.common.client.widget.messages.event.MessageEvent;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;

import java.util.List;

public class NiveauEtudeInfosPresenter extends PresenterWidget<NiveauEtudeInfosPresenter.MyView>
            implements NiveauEtudeSetupUiHandlers {
    public interface MyView extends ValidatedPopupView, HasUiHandlers<NiveauEtudeSetupUiHandlers> {
        void setNiveauEtudeTitle(String niveauEtudeTitle);

        void setDataPieceJustf(List<PieceJustifProxy> response);

        void setDataMatiereExamen(List<MatiereExamenProxy> response);

        void initForm();
    }

    private final BackRequestFactory requestFactory;
    private final SharedMessageBundle messageBundle;
    private NiveauEtudeRequest currentContext;

    private NiveauEtudeProxy currentNiveauEtude;

    @Inject
    public NiveauEtudeInfosPresenter(final EventBus eventBus, final MyView view,
                                     final BackRequestFactory requestFactory,
                                     final SharedMessageBundle messageBundle) {
        super(eventBus, view);

        this.requestFactory = requestFactory;
        this.messageBundle = messageBundle;

        getView().setUiHandlers(this);
    }

    public void setCurrentNiveauEtude(NiveauEtudeProxy currentNiveauEtude) {
        this.currentNiveauEtude = currentNiveauEtude;
    }

    @Override
    public void editPiece(PieceJustifProxy object, Boolean removeIt) {
        currentContext.editPieceDuNiveau(object, currentNiveauEtude, removeIt).fire(
                new ReceiverImpl<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        String messageString = aBoolean ? messageBundle.operationSuccess() : messageBundle.operationFailure();
                        AlertType alertType = aBoolean ? AlertType.SUCCESS : AlertType.ERROR;
                        Message message = new Message.Builder(messageString).style(alertType).build();
                        MessageEvent.fire(this, message);

                        if (aBoolean) {
                            fillCellList();
                            getView().initForm();
                        }
                    }
                });
    }

    @Override
    public void editMatiere(MatiereExamenProxy object, Boolean removeIt) {
        currentContext.editMatiereDuNiveau(object, currentNiveauEtude, removeIt).fire(
                new ReceiverImpl<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        String messageString = aBoolean ? messageBundle.operationSuccess() : messageBundle.operationFailure();
                        AlertType alertType = aBoolean ? AlertType.SUCCESS : AlertType.ERROR;
                        Message message = new Message.Builder(messageString).style(alertType).build();
                        MessageEvent.fire(this, message);

                        if (aBoolean) {
                            fillCellList();
                            getView().initForm();
                        }
                    }
                });
    }

    @Override
    public PieceJustifProxy loadPiece(PieceJustifProxy selectedObject) {
        currentContext = requestFactory.niveauEtudeService();
        return currentContext.edit(selectedObject);
    }

    @Override
    public PieceJustifProxy newPiece() {
        currentContext = requestFactory.niveauEtudeService();
        return currentContext.create(PieceJustifProxy.class);
    }

    @Override
    public MatiereExamenProxy loadMatiere(MatiereExamenProxy selectedObject) {
        currentContext = requestFactory.niveauEtudeService();
        return currentContext.edit(selectedObject);
    }

    @Override
    public MatiereExamenProxy newMatiere() {
        currentContext = requestFactory.niveauEtudeService();
        return currentContext.create(MatiereExamenProxy.class);
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
