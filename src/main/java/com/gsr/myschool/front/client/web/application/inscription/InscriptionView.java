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
import com.google.gwt.dom.client.Style;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.view.client.ListDataProvider;
import com.gsr.myschool.common.client.mvp.ViewWithUiHandlers;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.front.client.request.proxy.InscriptionProxy;

import java.util.List;

public class InscriptionView extends ViewWithUiHandlers<InscriptionUiHandlers> implements InscriptionPresenter.MyView {
    public interface Binder extends UiBinder<Widget, InscriptionView> {
    }

    @UiField
    CellTable<InscriptionProxy> inscriptionsTable;

    private final DateTimeFormat dateFormat;
    private final ListDataProvider<InscriptionProxy> dataProvider;

    @Inject
    public InscriptionView(final Binder uiBinder,
                           final UiHandlersStrategy<InscriptionUiHandlers> uiHandlers) {
        super(uiHandlers);

        initWidget(uiBinder.createAndBindUi(this));
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

    private void initDataGrid() {
        TextColumn<InscriptionProxy> idColumn = new TextColumn<InscriptionProxy>() {
            @Override
            public String getValue(InscriptionProxy object) {
                return object.getId().toString();
            }
        };

        idColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        inscriptionsTable.addColumn(idColumn, "ID");
        inscriptionsTable.setColumnWidth(idColumn, 70, Style.Unit.PX);

        TextColumn<InscriptionProxy> statusColumn = new TextColumn<InscriptionProxy>() {
            @Override
            public String getValue(InscriptionProxy object) {
                return object.getStatus().name();
            }
        };

        statusColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        inscriptionsTable.addColumn(statusColumn, "Status");
        inscriptionsTable.setColumnWidth(statusColumn, 150, Style.Unit.PX);

        TextColumn<InscriptionProxy> createdColumn = new TextColumn<InscriptionProxy>() {
            @Override
            public String getValue(InscriptionProxy object) {
                return dateFormat.format(object.getCreated());
            }
        };

        createdColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        inscriptionsTable.addColumn(createdColumn, "Date d'inscription");
        inscriptionsTable.setColumnWidth(createdColumn, 160, Style.Unit.PX);
    }
}
