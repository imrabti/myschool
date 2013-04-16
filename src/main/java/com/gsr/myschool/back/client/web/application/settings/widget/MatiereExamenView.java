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
import com.gsr.myschool.common.client.proxy.MatiereExamenProxy;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;
import com.gsr.myschool.common.client.ui.MatiereExamenEditor;
import com.gsr.myschool.common.client.widget.EmptyResult;
import com.gsr.myschool.common.client.widget.ValidationErrorPopup;

import java.util.List;

public class MatiereExamenView extends ValidatedViewWithUiHandlers<MatiereExamenUiHandlers>
        implements MatiereExamenPresenter.MyView {
    public interface Binder extends UiBinder<Widget, MatiereExamenView> {
    }

    @UiField(provided = true)
    MatiereExamenEditor matiereExamenEditor;
    @UiField
    CellTable<MatiereExamenProxy> matiereExamenTable;

    private final ListDataProvider<MatiereExamenProxy> dataProvider;

    @Inject
    public MatiereExamenView(final Binder uiBinder, final ValidationErrorPopup errorPopup,
                             final SharedMessageBundle sharedMessageBundle,
                             final MatiereExamenEditor matiereExamenEditor) {
        super(errorPopup);

        this.matiereExamenEditor = matiereExamenEditor;

        initWidget(uiBinder.createAndBindUi(this));
        initDataGrid();

        dataProvider = new ListDataProvider<MatiereExamenProxy>();
        dataProvider.addDataDisplay(matiereExamenTable);
        matiereExamenTable.setEmptyTableWidget(new EmptyResult(sharedMessageBundle.noResultFound(), AlertType.WARNING));
    }

    @Override
    public void setData(List<MatiereExamenProxy> data) {
        dataProvider.getList().clear();
        dataProvider.getList().addAll(data);
        matiereExamenTable.setPageSize(data.size());
    }

    @Override
    public void editMatiereExamen(MatiereExamenProxy edit) {
        matiereExamenEditor.edit(edit);
    }

    private void initDataGrid() {
        TextColumn<MatiereExamenProxy> nomPrenomColumn = new TextColumn<MatiereExamenProxy>() {
            @Override
            public String getValue(MatiereExamenProxy object) {
                return object.getNom();
            }
        };
        nomPrenomColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        matiereExamenTable.addColumn(nomPrenomColumn, "Nom");
        matiereExamenTable.setColumnWidth(nomPrenomColumn, 70, Style.Unit.PCT);

        ActionCell<MatiereExamenProxy> actionCell = new ActionCell<MatiereExamenProxy>("Supprimer",
                new ActionCell.Delegate<MatiereExamenProxy>() {
                    @Override
                    public void execute(MatiereExamenProxy object) {
                        getUiHandlers().deleteMatiereExamen(object);
                    }
                });
        Column<MatiereExamenProxy, MatiereExamenProxy> actionColumn = new Column<MatiereExamenProxy, MatiereExamenProxy>(actionCell) {
            @Override
            public MatiereExamenProxy getValue(MatiereExamenProxy object) {
                return object;
            }
        };
        actionColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        matiereExamenTable.addColumn(actionColumn, "Action");
        matiereExamenTable.setColumnWidth(actionColumn, 30, Style.Unit.PCT);
    }

    @UiHandler("addMatiereExamen")
    void onAddMatiere(ClickEvent event) {
        getUiHandlers().addMatiereExamen(matiereExamenEditor.get());
    }
}
