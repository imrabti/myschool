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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.web.application.settings.renderer.MatiereExamenRenderer;
import com.gsr.myschool.back.client.web.application.settings.renderer.PieceJustifRenderer;
import com.gsr.myschool.common.client.mvp.ValidatedPopupViewImpl;
import com.gsr.myschool.common.client.widget.ValidationErrorPopup;
import com.gsr.myschool.common.client.proxy.MatiereExamenProxy;
import com.gsr.myschool.common.client.proxy.PieceJustifProxy;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;
import com.gsr.myschool.common.client.resource.style.DetailsListStyle;
import com.gsr.myschool.common.client.widget.EmptyResult;
import com.gsr.myschool.common.client.widget.ModalHeader;

import java.util.List;

public class NiveauEtudeInfosView extends ValidatedPopupViewImpl implements NiveauEtudeInfosPresenter.MyView {
    public interface Binder extends UiBinder<PopupPanel, NiveauEtudeInfosView> {
    }

    @UiField(provided = true)
    ModalHeader modalHeader;

    @UiField(provided = true)
    CellList<MatiereExamenProxy> matiereExamenList;
    @UiField(provided = true)
    CellList<PieceJustifProxy> pieceJustifList;

    private final ListDataProvider<PieceJustifProxy> dataProviderPieces;
    private final ListDataProvider<MatiereExamenProxy> dataProviderMatieres;

    @Inject
    protected NiveauEtudeInfosView(EventBus eventBus, final Binder uiBinder,
                                   final ValidationErrorPopup errorPopup,
                                   final SharedMessageBundle sharedMessageBundle,
                                   final DetailsListStyle listStyle,
                                   final ModalHeader modalHeader) {
        super(eventBus, errorPopup);

        this.modalHeader = modalHeader;
        this.dataProviderMatieres = new ListDataProvider<MatiereExamenProxy>();
        this.dataProviderPieces = new ListDataProvider<PieceJustifProxy>();
        this.matiereExamenList = new CellList(new MatiereExamenRenderer(), listStyle);
        this.pieceJustifList = new CellList(new PieceJustifRenderer(), listStyle);

        dataProviderMatieres.addDataDisplay(matiereExamenList);
        dataProviderPieces.addDataDisplay(pieceJustifList);
        pieceJustifList.setEmptyListWidget(new EmptyResult(sharedMessageBundle.noResultFound(), AlertType.WARNING));
        matiereExamenList.setEmptyListWidget(new EmptyResult(sharedMessageBundle.noResultFound(), AlertType.WARNING));

        initWidget(uiBinder.createAndBindUi(this));

        modalHeader.addCloseHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                hide();
            }
        });
    }

    public void setNiveauEtudeTitle(String niveauEtudeTitle) {
        if (niveauEtudeTitle.length() > 30) {
            modalHeader.setText(niveauEtudeTitle.substring(0, 30) + "...");
        } else {
            modalHeader.setText(niveauEtudeTitle);
        }
    }

    @Override
    public void setDataPieceJustf(List<PieceJustifProxy> response) {
        pieceJustifList.setPageSize(response.size());
        dataProviderPieces.getList().clear();
        dataProviderPieces.getList().addAll(response);
    }

    @Override
    public void setDataMatiereExamen(List<MatiereExamenProxy> response) {
        matiereExamenList.setPageSize(response.size());
        dataProviderMatieres.getList().clear();
        dataProviderMatieres.getList().addAll(response);
    }
}
