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

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.proxy.ValueListProxy;
import com.gsr.myschool.back.client.resource.message.MessageBundle;
import com.gsr.myschool.common.client.mvp.ViewWithUiHandlers;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gsr.myschool.common.client.proxy.ValueTypeProxy;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;
import com.gsr.myschool.common.client.widget.EmptyResult;

import java.util.List;

public class ValueListView extends ViewWithUiHandlers<ValueListUiHandlers> implements ValueListPresenter.MyView {
    public interface Binder extends UiBinder<Widget, ValueListView> {
    }

    @UiField
    CellTable valueListTable;
    @UiField
    ListBox parent;
    @UiField
    ListBox defLov;
    @UiField
    Button delete;
    @UiField
    Button modify;
    SingleSelectionModel<ValueListProxy> lovSelectionModel;
    @UiField
    SimplePanel valueTypeDisplay;

    private final MessageBundle messageBundle;
    private final SingleSelectionModel<ValueListProxy> valueListSelectionModel;
    private final ListDataProvider<ValueListProxy> dataProvider;

    @Inject
    public ValueListView(final Binder uiBinder, final MessageBundle messageBundle,
                         final UiHandlersStrategy<ValueListUiHandlers> uiHandlers,
                         final SharedMessageBundle sharedMessageBundle) {
        super(uiHandlers);

        this.messageBundle = messageBundle;

        initWidget(uiBinder.createAndBindUi(this));
        initDataGrid();

        this.dataProvider = new ListDataProvider<ValueListProxy>();
        dataProvider.addDataDisplay(valueListTable);
        this.valueListSelectionModel = new SingleSelectionModel<ValueListProxy>();
        valueListTable.setSelectionModel(valueListSelectionModel);
        valueListTable.setEmptyTableWidget(new EmptyResult(sharedMessageBundle.noResultFound(), AlertType.INFO));
    }

    private void initDataGrid() {
        TextColumn<ValueListProxy> valueColumn = new TextColumn<ValueListProxy>() {
            @Override
            public String getValue(ValueListProxy valueList) {
                return valueList.getValue();
            }
        };
        valueColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        valueListTable.addColumn(valueColumn, "Valeur");
        valueListTable.setColumnWidth(valueColumn, 35, Style.Unit.PCT);

    }

    @Override
    public void setInSlot(Object slot, Widget content){
        if (content != null) {
            if (slot == ValueListPresenter.TYPE_SetValueTypeContent) {
                valueTypeDisplay.setWidget(content);
            }
        }
    }

    @Override
    public void setData(List<ValueListProxy> response) {
        dataProvider.getList().clear();
        dataProvider.getList().addAll(response);
    }

    @UiHandler("defLov")
    void onDefLovChanged(ChangeEvent event) {
        getUiHandlers().getParent();
    }

    @UiHandler("delete")
    void onDeleteClick(ClickEvent event) {
    }

    @UiHandler("modify")
    void onModifyClick(ClickEvent event) {
    }

    @UiHandler("addValueList")
    void onAddValueListClicked(ClickEvent event) {
        getUiHandlers().addValueList();
    }

    @Override
    public CellTable<ValueListProxy> getValueListTable() {
        return valueListTable;
    }

    @Override
    public ListBox getParent() {
        return parent;
    }

    @Override
    public ListBox getDefLov() {
        return defLov;
    }
}
