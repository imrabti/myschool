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

package com.gsr.myschool.back.client.web.application.preinscription;

import com.github.gwtbootstrap.client.ui.CellTable;
import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.gwt.dom.client.Style;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.inject.Inject;
import com.gsr.myschool.back.client.web.application.preinscription.renderer.PreInscriptionActionCell;
import com.gsr.myschool.back.client.web.application.preinscription.renderer.PreInscriptionActionCellFactory;
import com.gsr.myschool.common.client.mvp.ViewWithUiHandlers;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gsr.myschool.common.client.proxy.DossierProxy;

import java.util.List;

public class PreInscriptionView extends ViewWithUiHandlers<PreInscriptionUiHandlers>
        implements PreInscriptionPresenter.MyView {
    public interface Binder extends UiBinder<Widget, PreInscriptionView> {
    }

    @UiField
    CellTable<DossierProxy> preInscriptionsTable;

    private final DateTimeFormat dateFormat;
    private final ListDataProvider<DossierProxy> dataProvider;
    private final PreInscriptionActionCellFactory actionCellFactory;

    private Delegate<DossierProxy> viewDetailsAction;

    @Inject
    public PreInscriptionView(final Binder uiBinder,
            final UiHandlersStrategy<PreInscriptionUiHandlers> uiHandlers,
            final PreInscriptionActionCellFactory actionCellFactory) {
        super(uiHandlers);

        this.actionCellFactory = actionCellFactory;

        initWidget(uiBinder.createAndBindUi(this));
        initActions();
        initDataGrid();

        dataProvider = new ListDataProvider<DossierProxy>();
        dataProvider.addDataDisplay(preInscriptionsTable);
        dateFormat = DateTimeFormat.getFormat("LLL d yyyy");
    }

    @Override
    public void setData(List<DossierProxy> data) {
        dataProvider.getList().clear();
        dataProvider.getList().addAll(data);
    }

    private void initActions() {
        viewDetailsAction = new Delegate<DossierProxy>() {
            @Override
            public void execute(DossierProxy dossier) {
                getUiHandlers().viewDetails(dossier);
            }
        };
    }

    private void initDataGrid() {
        TextColumn<DossierProxy> refColumn = new TextColumn<DossierProxy>() {
            @Override
            public String getValue(DossierProxy object) {
                return object.getNote();
            }
        };
        refColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        preInscriptionsTable.addColumn(refColumn, "N° Dossier");
        preInscriptionsTable.setColumnWidth(refColumn, 20, Style.Unit.PCT);

        TextColumn<DossierProxy> statusColumn = new TextColumn<DossierProxy>() {
            @Override
            public String getValue(DossierProxy object) {
                return object.getStatus().name();
            }
        };
        statusColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        preInscriptionsTable.addColumn(statusColumn, "Status");
        preInscriptionsTable.setColumnWidth(statusColumn, 40, Style.Unit.PCT);

        TextColumn<DossierProxy> submittedColumn = new TextColumn<DossierProxy>() {
            @Override
            public String getValue(DossierProxy object) {
                return dateFormat.format(object.getCreateDate());
            }
        };
        submittedColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        preInscriptionsTable.addColumn(submittedColumn, "Date de soumission");
        preInscriptionsTable.setColumnWidth(submittedColumn, 30, Style.Unit.PCT);

        PreInscriptionActionCell actionsCell = actionCellFactory.create(viewDetailsAction);
        Column<DossierProxy, DossierProxy> actionsColumn = new
                Column<DossierProxy, DossierProxy>(actionsCell) {
            @Override
            public DossierProxy getValue(DossierProxy object) {
                return object;
            }
        };
        actionsColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        preInscriptionsTable.addColumn(actionsColumn, "Détails");
        preInscriptionsTable.setColumnWidth(actionsColumn, 10, Style.Unit.PCT);
    }
}