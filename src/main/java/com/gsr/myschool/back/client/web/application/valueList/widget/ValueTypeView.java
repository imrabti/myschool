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

package com.gsr.myschool.back.client.web.application.valueList.widget;

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.common.base.Strings;
import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.mvp.ViewWithUiHandlers;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.client.proxy.ValueTypeProxy;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;
import com.gsr.myschool.common.client.widget.EmptyResult;

import java.util.List;

public class ValueTypeView extends ViewWithUiHandlers<ValueTypeUiHandlers> implements ValueTypePresenter.MyView {
    public interface Binder extends UiBinder<Widget, ValueTypeView> {
    }

    @UiField()
    CellTable<ValueTypeProxy> valueTypeTable;

    @UiField
    Button delete;

    @UiField
    Button modify;

    private final SingleSelectionModel<ValueTypeProxy> valueTypeSelectionModel;
    private final ListDataProvider<ValueTypeProxy> dataProvider;

    @Inject
    public ValueTypeView(final Binder uiBinder, final UiHandlersStrategy<ValueTypeUiHandlers> uiHandlers,
                         final SharedMessageBundle sharedMessageBundle) {
        super(uiHandlers);

        initWidget(uiBinder.createAndBindUi(this));
        initDataGrid();

        this.dataProvider = new ListDataProvider<ValueTypeProxy>();
        dataProvider.addDataDisplay(valueTypeTable);
        this.valueTypeSelectionModel = new SingleSelectionModel<ValueTypeProxy>();
        valueTypeTable.setSelectionModel(valueTypeSelectionModel);
        valueTypeTable.setEmptyTableWidget(new EmptyResult(sharedMessageBundle.noResultFound(), AlertType.INFO));
    }

    @Override
    public CellTable<ValueTypeProxy> getValueTypeTable() {
        return valueTypeTable;
    }

    @Override
    public void setData(List<ValueTypeProxy> data) {
        dataProvider.getList().clear();
        dataProvider.getList().addAll(data);
    }

    @Override
    public void initDataGrid() {
        TextColumn<ValueTypeProxy> codeColumn = new TextColumn<ValueTypeProxy>() {
            @Override
            public String getValue(ValueTypeProxy valueType) {
                return valueType.getCode().toString();
            }
        };
        codeColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        valueTypeTable.addColumn(codeColumn, "Code");
        valueTypeTable.setColumnWidth(codeColumn, 35, Style.Unit.PCT);

//        TextColumn<ValueTypeProxy> nameColumn = new TextColumn<ValueTypeProxy>() {
//            @Override
//            public String getValue(ValueTypeProxy valueType) {
//                return valueType.getRegex().toString();
//            }
//        };
//        nameColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
//        valueTypeTable.addColumn(nameColumn, "Expréssion réguliaire");
//        valueTypeTable.setColumnWidth(nameColumn, 35, Style.Unit.PCT);

    }

//    private void initActions() {
//        modify = new ActionCell.Delegate<ValueTypeProxy>() {
//            @Override
//            public void execute(ValueTypeProxy valueType) {
//                getUiHandlers().editValueType(valueType);
//            }
//        };
//    }

    @UiHandler("modify")
    void onModifyClick(ClickEvent event) {
        getUiHandlers().modify();
    }

    @UiHandler("delete")
    void OnDeleteClick(ClickEvent event) {
        getUiHandlers().delete();
    }

    @UiHandler("addValueType")
    void onAddValueTypeClicked(ClickEvent event) {
        getUiHandlers().addValueType();
    }
}
