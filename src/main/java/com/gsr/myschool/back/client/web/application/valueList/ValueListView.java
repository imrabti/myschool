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

package com.gsr.myschool.back.client.web.application.valueList;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;
import com.gsr.myschool.back.client.web.application.valueList.renderer.ValueListActionCell;
import com.gsr.myschool.back.client.web.application.valueList.renderer.ValueListActionCellFactory;
import com.gsr.myschool.common.client.proxy.ValueListProxy;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;
import com.gsr.myschool.common.client.widget.EmptyResult;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import java.util.List;

public class ValueListView extends ViewWithUiHandlers<ValueListUiHandlers> implements ValueListPresenter.MyView {
    public interface Binder extends UiBinder<Widget, ValueListView> {
    }

    @UiField
    CellTable valueListTable;
    @UiField
    SimplePanel valueTypeDisplay;
    @UiField
    Button addValueList;

    private Delegate<ValueListProxy> deleteAction;
    private Delegate<ValueListProxy> modifyAction;

    private final SingleSelectionModel<ValueListProxy> valueListSelectionModel;
    private final ListDataProvider<ValueListProxy> dataProvider;
    private final ValueListActionCellFactory actionCellFactory;

    @Inject
    public ValueListView(final Binder uiBinder,
                         final SharedMessageBundle sharedMessageBundle,
                         final ValueListActionCellFactory actionCellFactory) {
        initWidget(uiBinder.createAndBindUi(this));
        initActions();
        initDataGrid();

        this.addValueList.setVisible(false);
        this.dataProvider = new ListDataProvider<ValueListProxy>();
        this.valueListSelectionModel = new SingleSelectionModel<ValueListProxy>();
        this.actionCellFactory = actionCellFactory;

        dataProvider.addDataDisplay(valueListTable);
        valueListTable.setSelectionModel(valueListSelectionModel);
        valueListTable.setEmptyTableWidget(new EmptyResult(sharedMessageBundle.noResultFound(), AlertType.WARNING));
    }

    @Override
    public void setInSlot(Object slot, IsWidget content) {
        if (content != null) {
            if (slot == ValueListPresenter.TYPE_SetValueTypeContent) {
                valueTypeDisplay.setWidget(content);
            }
        }
    }

    @Override
    public void setData(List<ValueListProxy> response) {
        valueListTable.setPageSize(response.size());
        dataProvider.getList().clear();
        dataProvider.getList().addAll(response);
    }

    @Override
    public void setAddButtonVisible(Boolean bool){
        addValueList.setVisible(bool);
    }

    @UiHandler("addValueList")
    void onAddValueListClicked(ClickEvent event) {
        getUiHandlers().addValueList();
    }

    private void initActions() {
        modifyAction = new Delegate<ValueListProxy>() {
            @Override
            public void execute(ValueListProxy valueList) {
                getUiHandlers().modify(valueList);
            }
        };
        deleteAction = new Delegate<ValueListProxy>() {
            @Override
            public void execute(ValueListProxy valueList) {
                getUiHandlers().delete(valueList);
            }
        };
    }

    private void initDataGrid() {
        TextColumn<ValueListProxy> labelColumn = new TextColumn<ValueListProxy>() {
            @Override
            public String getValue(ValueListProxy valueList) {
                return valueList.getLabel();
            }
        };
        labelColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        valueListTable.addColumn(labelColumn, "Label");
        valueListTable.setColumnWidth(labelColumn, 35, Style.Unit.PCT);

        TextColumn<ValueListProxy> valueColumn = new TextColumn<ValueListProxy>() {
            @Override
            public String getValue(ValueListProxy valueList) {
                return valueList.getValue();
            }
        };
        valueColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        valueListTable.addColumn(valueColumn, "Valeur");
        valueListTable.setColumnWidth(valueColumn, 35, Style.Unit.PCT);

        ValueListActionCell actionsCell = actionCellFactory.create(deleteAction, modifyAction);
        Column<ValueListProxy, ValueListProxy> actionsColumn = new
                Column<ValueListProxy, ValueListProxy>(actionsCell) {
                    @Override
                    public ValueListProxy getValue(ValueListProxy object) {
                        return object;
                    }
                };
        actionsColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        valueListTable.addColumn(actionsColumn, "Actions");
        valueListTable.setColumnWidth(actionsColumn, 35, Style.Unit.PCT);
    }
}
