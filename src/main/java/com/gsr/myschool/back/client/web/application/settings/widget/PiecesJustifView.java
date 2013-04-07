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

package com.gsr.myschool.back.client.web.application.settings.widget;

import com.github.gwtbootstrap.client.ui.CellTable;
import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.mvp.ValidatedViewWithUiHandlers;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gsr.myschool.common.client.proxy.PieceJustifProxy;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;
import com.gsr.myschool.common.client.ui.PiecesJustifEditor;
import com.gsr.myschool.common.client.widget.EmptyResult;
import com.gsr.myschool.common.client.widget.ValidationErrorPopup;

import java.util.List;

public class PiecesJustifView extends ValidatedViewWithUiHandlers<PiecesJustifUiHandlers>
        implements PiecesJustifPresenter.MyView {
    public interface Binder extends UiBinder<Widget, PiecesJustifView> {
    }

    @UiField(provided = true)
    PiecesJustifEditor piecesJustifEditor;
    @UiField
    CellTable<PieceJustifProxy> piecesJustifTable;

    private final ListDataProvider<PieceJustifProxy> dataProvider;

    @Inject
    public PiecesJustifView(final Binder uiBinder, final ValidationErrorPopup errorPopup,
                            final UiHandlersStrategy<PiecesJustifUiHandlers> uiHandlersStrategy,
                            final SharedMessageBundle sharedMessageBundle,
                            final PiecesJustifEditor piecesJustifEditor) {
        super(uiHandlersStrategy, errorPopup);

        this.piecesJustifEditor = piecesJustifEditor;

        initWidget(uiBinder.createAndBindUi(this));
        initDataGrid();

        dataProvider = new ListDataProvider<PieceJustifProxy>();
        dataProvider.addDataDisplay(piecesJustifTable);
        piecesJustifTable.setEmptyTableWidget(new EmptyResult(sharedMessageBundle.noResultFound(), AlertType.WARNING));
    }

    @Override
    public void setData(List<PieceJustifProxy> data) {
        dataProvider.getList().clear();
        dataProvider.getList().addAll(data);
        piecesJustifTable.setPageSize(data.size());
    }

    @Override
    public void editPieceJustif(PieceJustifProxy edit) {
        piecesJustifEditor.edit(edit);
    }

    private void initDataGrid() {
        TextColumn<PieceJustifProxy> nomPrenomColumn = new TextColumn<PieceJustifProxy>() {
            @Override
            public String getValue(PieceJustifProxy object) {
                return object.getNom();
            }
        };
        nomPrenomColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        piecesJustifTable.addColumn(nomPrenomColumn, "Nom");
        piecesJustifTable.setColumnWidth(nomPrenomColumn, 70, Style.Unit.PCT);

        ActionCell<PieceJustifProxy> actionCell = new ActionCell<PieceJustifProxy>("Supprimer",
                new ActionCell.Delegate<PieceJustifProxy>() {
                    @Override
                    public void execute(PieceJustifProxy object) {
                        getUiHandlers().deletePieceJustif(object);
                    }
                });
        Column<PieceJustifProxy, PieceJustifProxy> actionColumn = new Column<PieceJustifProxy, PieceJustifProxy>(actionCell) {
            @Override
            public PieceJustifProxy getValue(PieceJustifProxy object) {
                return object;
            }
        };
        actionColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        piecesJustifTable.addColumn(actionColumn, "Action");
        piecesJustifTable.setColumnWidth(actionColumn, 30, Style.Unit.PCT);
    }

    @UiHandler("addPiece")
    void onAddPiece(ClickEvent event) {
        getUiHandlers().addPieceJustif(piecesJustifEditor.get());
    }
}
