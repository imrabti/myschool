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

package com.gsr.myschool.front.client.web.application.inscription;

import com.github.gwtbootstrap.client.ui.CellTable;
import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.view.client.ListDataProvider;
import com.gsr.myschool.common.client.mvp.ViewWithUiHandlers;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.proxy.InscriptionProxy;
import com.gsr.myschool.front.client.web.application.inscription.renderer.InscriptionActionCell;
import com.gsr.myschool.front.client.web.application.inscription.renderer.InscriptionActionCellFactory;

import java.util.List;

public class InscriptionView extends ViewWithUiHandlers<InscriptionUiHandlers> implements InscriptionPresenter.MyView {
    public interface Binder extends UiBinder<Widget, InscriptionView> {
    }

    @UiField
    CellTable<InscriptionProxy> inscriptionsTable;

    private final DateTimeFormat dateFormat;
    private final ListDataProvider<InscriptionProxy> dataProvider;
    private final InscriptionActionCellFactory actionCellFactory;

    private Delegate<InscriptionProxy> previewAction;
    private Delegate<InscriptionProxy> editAction;
    private Delegate<InscriptionProxy> deleteAction;
    private Delegate<InscriptionProxy> submitAction;
    private Delegate<InscriptionProxy> printAction;

    @Inject
    public InscriptionView(final Binder uiBinder,
                           final UiHandlersStrategy<InscriptionUiHandlers> uiHandlers,
                           final InscriptionActionCellFactory actionCellFactory) {
        super(uiHandlers);

        this.actionCellFactory = actionCellFactory;

        initWidget(uiBinder.createAndBindUi(this));
        initActions();
        initDataGrid();

        dataProvider = new ListDataProvider<InscriptionProxy>();
        dataProvider.addDataDisplay(inscriptionsTable);
        dateFormat = DateTimeFormat.getFormat("LLL d yyyy");
    }

    @Override
    public void setData(List<InscriptionProxy> data) {
        dataProvider.getList().clear();
        dataProvider.getList().addAll(data);
    }

    @UiHandler("add")
    void onAddClicked(ClickEvent event) {
        getUiHandlers().addNewInscription();
    }

    private void initActions() {
        previewAction = new Delegate<InscriptionProxy>() {
            @Override
            public void execute(InscriptionProxy inscription) {
                getUiHandlers().previewInscription(inscription);
            }
        };

        editAction = new Delegate<InscriptionProxy>() {
            @Override
            public void execute(InscriptionProxy inscription) {
                getUiHandlers().editInscription(inscription);
            }
        };

        deleteAction = new Delegate<InscriptionProxy>() {
            @Override
            public void execute(InscriptionProxy inscription) {
                getUiHandlers().deleteInscription(inscription);
            }
        };

        submitAction = new Delegate<InscriptionProxy>() {
            @Override
            public void execute(InscriptionProxy inscription) {
                getUiHandlers().submitInscription(inscription);
            }
        };

        printAction = new Delegate<InscriptionProxy>() {
            @Override
            public void execute(InscriptionProxy inscription) {
                getUiHandlers().printInscription(inscription);
            }
        };
    }

    private void initDataGrid() {
        TextColumn<InscriptionProxy> idColumn = new TextColumn<InscriptionProxy>() {
            @Override
            public String getValue(InscriptionProxy object) {
                return object.getId().toString();
            }
        };
        idColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        inscriptionsTable.addColumn(idColumn, "ID");
        inscriptionsTable.setColumnWidth(idColumn, 20, Style.Unit.PCT);

        TextColumn<InscriptionProxy> statusColumn = new TextColumn<InscriptionProxy>() {
            @Override
            public String getValue(InscriptionProxy object) {
                return object.getStatus().name();
            }
        };
        statusColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        inscriptionsTable.addColumn(statusColumn, "Status");
        inscriptionsTable.setColumnWidth(statusColumn, 35, Style.Unit.PCT);

        TextColumn<InscriptionProxy> createdColumn = new TextColumn<InscriptionProxy>() {
            @Override
            public String getValue(InscriptionProxy object) {
                return dateFormat.format(object.getCreated());
            }
        };
        createdColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        inscriptionsTable.addColumn(createdColumn, "Date d'inscription");
        inscriptionsTable.setColumnWidth(createdColumn, 30, Style.Unit.PCT);

        InscriptionActionCell actionsCell = actionCellFactory.create(previewAction, editAction,
                deleteAction, submitAction, printAction);
        Column<InscriptionProxy, InscriptionProxy> actionsColumn = new
                Column<InscriptionProxy, InscriptionProxy>(actionsCell) {
            @Override
            public InscriptionProxy getValue(InscriptionProxy object) {
                return object;
            }
        };
        actionsColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        inscriptionsTable.addColumn(actionsColumn, "Actions");
        inscriptionsTable.setColumnWidth(actionsColumn, 15, Style.Unit.PCT);
    }
}
