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
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;
import com.gsr.myschool.common.client.widget.EmptyResult;
import com.gsr.myschool.front.client.web.application.inscription.renderer.InscriptionActionCell;
import com.gsr.myschool.front.client.web.application.inscription.renderer.InscriptionActionCellFactory;

import java.util.List;

public class InscriptionView extends ViewWithUiHandlers<InscriptionUiHandlers> implements InscriptionPresenter.MyView {
    public interface Binder extends UiBinder<Widget, InscriptionView> {
    }

    @UiField
    CellTable<DossierProxy> inscriptionsTable;

    private final DateTimeFormat dateFormat;
    private final ListDataProvider<DossierProxy> dataProvider;
    private final InscriptionActionCellFactory actionCellFactory;

    private Delegate<DossierProxy> previewAction;
    private Delegate<DossierProxy> editAction;
    private Delegate<DossierProxy> deleteAction;
    private Delegate<DossierProxy> submitAction;
    private Delegate<DossierProxy> printAction;

    @Inject
    public InscriptionView(final Binder uiBinder,
                           final UiHandlersStrategy<InscriptionUiHandlers> uiHandlers,
                           final InscriptionActionCellFactory actionCellFactory,
                           final SharedMessageBundle sharedMessageBundle) {
        super(uiHandlers);

        this.actionCellFactory = actionCellFactory;

        initWidget(uiBinder.createAndBindUi(this));
        initActions();
        initDataGrid();

        dataProvider = new ListDataProvider<DossierProxy>();
        dataProvider.addDataDisplay(inscriptionsTable);
        dateFormat = DateTimeFormat.getFormat("LLL d yyyy");
        inscriptionsTable.setEmptyTableWidget(new EmptyResult(sharedMessageBundle.noResultFound()));
    }

    @Override
    public void setData(List<DossierProxy> data) {
        dataProvider.getList().clear();
        dataProvider.getList().addAll(data);
    }

    @UiHandler("add")
    void onAddClicked(ClickEvent event) {
        getUiHandlers().addNewInscription();
    }

    private void initActions() {
        previewAction = new Delegate<DossierProxy>() {
            @Override
            public void execute(DossierProxy inscription) {
                getUiHandlers().previewInscription(inscription);
            }
        };

        editAction = new Delegate<DossierProxy>() {
            @Override
            public void execute(DossierProxy inscription) {
                getUiHandlers().editInscription(inscription);
            }
        };

        deleteAction = new Delegate<DossierProxy>() {
            @Override
            public void execute(DossierProxy inscription) {
                getUiHandlers().deleteInscription(inscription);
            }
        };

        submitAction = new Delegate<DossierProxy>() {
            @Override
            public void execute(DossierProxy inscription) {
                getUiHandlers().submitInscription(inscription);
            }
        };

        printAction = new Delegate<DossierProxy>() {
            @Override
            public void execute(DossierProxy inscription) {
                getUiHandlers().printInscription(inscription);
            }
        };
    }

    private void initDataGrid() {
        TextColumn<DossierProxy> idColumn = new TextColumn<DossierProxy>() {
            @Override
            public String getValue(DossierProxy object) {
                return object.getId().toString();
            }
        };
        idColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        inscriptionsTable.addColumn(idColumn, "ID");
        inscriptionsTable.setColumnWidth(idColumn, 20, Style.Unit.PCT);

        TextColumn<DossierProxy> statusColumn = new TextColumn<DossierProxy>() {
            @Override
            public String getValue(DossierProxy object) {
                return object.getStatus().name();
            }
        };
        statusColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        inscriptionsTable.addColumn(statusColumn, "Status");
        inscriptionsTable.setColumnWidth(statusColumn, 35, Style.Unit.PCT);

        TextColumn<DossierProxy> createdColumn = new TextColumn<DossierProxy>() {
            @Override
            public String getValue(DossierProxy object) {
                if (object.getCreateDate() == null) {
                    return "";
                } else {
                    return dateFormat.format(object.getCreateDate());
                }
            }
        };
        createdColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        inscriptionsTable.addColumn(createdColumn, "Date d'inscription");
        inscriptionsTable.setColumnWidth(createdColumn, 30, Style.Unit.PCT);

        InscriptionActionCell actionsCell = actionCellFactory.create(previewAction, editAction,
                deleteAction, submitAction, printAction);
        Column<DossierProxy, DossierProxy> actionsColumn = new Column<DossierProxy, DossierProxy>(actionsCell) {
            @Override
            public DossierProxy getValue(DossierProxy object) {
                return object;
            }
        };
        actionsColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        inscriptionsTable.addColumn(actionsColumn, "Actions");
        inscriptionsTable.setColumnWidth(actionsColumn, 15, Style.Unit.PCT);
    }
}
