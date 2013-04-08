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

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.web.application.settings.popup.ui.MatiereExamenEditor;
import com.gsr.myschool.back.client.web.application.settings.popup.ui.PieceJustificatifEditor;
import com.gsr.myschool.back.client.web.application.settings.renderer.MatiereExamenRenderer;
import com.gsr.myschool.back.client.web.application.settings.renderer.PieceJustifRenderer;
import com.gsr.myschool.common.client.mvp.ValidatedPopupViewImplWithUiHandlers;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gsr.myschool.common.client.proxy.MatiereExamenProxy;
import com.gsr.myschool.common.client.proxy.PieceJustifProxy;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;
import com.gsr.myschool.common.client.resource.style.DetailsListStyle;
import com.gsr.myschool.common.client.widget.EmptyResult;
import com.gsr.myschool.common.client.widget.ModalHeader;
import com.gsr.myschool.common.client.widget.ValidationErrorPopup;

import java.util.List;

public class NiveauEtudeInfosView extends ValidatedPopupViewImplWithUiHandlers<NiveauEtudeSetupUiHandlers>
        implements NiveauEtudeInfosPresenter.MyView {
    public interface Binder extends UiBinder<PopupPanel, NiveauEtudeInfosView> {
    }

    @UiField(provided = true)
    ModalHeader modalHeader;

    @UiField(provided = true)
    CellList<MatiereExamenProxy> matiereExamenList;
    @UiField(provided = true)
    CellList<PieceJustifProxy> pieceJustifList;

    @UiField(provided = true)
    PieceJustificatifEditor pieceEditor;
    @UiField
    Button savePiece;
    @UiField
    Button addPiece;
    @UiField
    Button removePiece;

    @UiField(provided = true)
    MatiereExamenEditor matiereEditor;
    @UiField
    Button saveMatiere;
    @UiField
    Button addMatiere;
    @UiField
    Button removeMatiere;


    private final ListDataProvider<PieceJustifProxy> dataProviderPieces;
    private final ListDataProvider<MatiereExamenProxy> dataProviderMatieres;

    @Inject
    protected NiveauEtudeInfosView(EventBus eventBus, final Binder uiBinder,
                                   final ValidationErrorPopup errorPopup,
                                   final UiHandlersStrategy<NiveauEtudeSetupUiHandlers> uiHandlers,
                                   final SharedMessageBundle sharedMessageBundle,
                                   final PieceJustificatifEditor pieceEditor,
                                   final MatiereExamenEditor matiereEditor,
                                   final DetailsListStyle listStyle,
                                   final ModalHeader modalHeader) {
        super(eventBus, errorPopup, uiHandlers);

        this.modalHeader = modalHeader;
        this.pieceEditor = pieceEditor;
        this.matiereEditor = matiereEditor;
        this.dataProviderMatieres = new ListDataProvider<MatiereExamenProxy>();
        this.dataProviderPieces = new ListDataProvider<PieceJustifProxy>();
        this.matiereExamenList = new CellList(new MatiereExamenRenderer(), listStyle);
        this.pieceJustifList = new CellList(new PieceJustifRenderer(), listStyle);

        dataProviderMatieres.addDataDisplay(matiereExamenList);
        dataProviderPieces.addDataDisplay(pieceJustifList);
        pieceJustifList.setEmptyListWidget(new EmptyResult(sharedMessageBundle.noResultFound(), AlertType.WARNING));
        matiereExamenList.setEmptyListWidget(new EmptyResult(sharedMessageBundle.noResultFound(), AlertType.WARNING));

        initWidget(uiBinder.createAndBindUi(this));

        initializeEvents();

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
        initializeEvents();
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

    @UiHandler("savePiece")
    void onSavePieceClicked(ClickEvent event) {
        getUiHandlers().editPiece(pieceEditor.get(), false);
    }

    @UiHandler("addPiece")
    void onAddPieceClicked(ClickEvent event) {
        pieceEditor.newPiece(getUiHandlers().newPiece(), pieceEditor.getNewPieceNom());
        getUiHandlers().editPiece(pieceEditor.get(), false);
    }

    @UiHandler("removePiece")
    void onRemovePieceClicked(ClickEvent event) {
        getUiHandlers().editPiece(pieceEditor.get(), true);
    }

    @UiHandler("saveMatiere")
    void onSaveMatiereClicked(ClickEvent event) {
        getUiHandlers().editMatiere(matiereEditor.get(), false);
    }

    @UiHandler("addMatiere")
    void onAddMatiereClicked(ClickEvent event) {
        matiereEditor.newMatiere(getUiHandlers().newMatiere(), matiereEditor.getNewMatiereNom());
        getUiHandlers().editMatiere(matiereEditor.get(), false);
    }

    @UiHandler("removeMatiere")
    void onRemoveMatiereClicked(ClickEvent event) {
        getUiHandlers().editMatiere(matiereEditor.get(), true);
    }

    private void initializeEvents() {
        final SingleSelectionModel<PieceJustifProxy> pieceModel = new SingleSelectionModel<PieceJustifProxy>();
        pieceJustifList.setSelectionModel(pieceModel);
        pieceModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            public void onSelectionChange(SelectionChangeEvent event) {
                pieceEditor.edit(getUiHandlers().loadPiece(pieceModel.getSelectedObject()));
            }
        });

        final SingleSelectionModel<MatiereExamenProxy> matiereModel = new SingleSelectionModel<MatiereExamenProxy>();
        matiereExamenList.setSelectionModel(matiereModel);
        matiereModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            public void onSelectionChange(SelectionChangeEvent event) {
                matiereEditor.edit(getUiHandlers().loadMatiere(matiereModel.getSelectedObject()));
            }
        });

        pieceEditor.initSuggestions();
        matiereEditor.initSuggestions();
    }
}
