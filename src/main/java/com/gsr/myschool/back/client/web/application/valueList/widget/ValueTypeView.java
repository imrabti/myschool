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

import com.github.gwtbootstrap.client.ui.CellTable;
import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;
import com.gsr.myschool.back.client.resource.style.CellTableStyle;
import com.gsr.myschool.common.client.proxy.ValueTypeProxy;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;
import com.gsr.myschool.common.client.widget.EmptyResult;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import java.util.List;

public class ValueTypeView extends ViewWithUiHandlers<ValueTypeUiHandlers>
        implements ValueTypePresenter.MyView {
    public interface Binder extends UiBinder<Widget, ValueTypeView> {
    }

    @UiField(provided = true)
    CellTable<ValueTypeProxy> valueTypeTable;

    private final SingleSelectionModel<ValueTypeProxy> valueTypeSelectionModel;
    private final ListDataProvider<ValueTypeProxy> dataProvider;

    @Inject
    public ValueTypeView(final Binder uiBinder,
                         final SharedMessageBundle sharedMessageBundle,
                         final CellTableStyle cellTableStyle) {
        this.dataProvider = new ListDataProvider<ValueTypeProxy>();
        this.valueTypeTable = new CellTable<ValueTypeProxy>(15, cellTableStyle);
        this.valueTypeSelectionModel = new SingleSelectionModel<ValueTypeProxy>();

        initDataGrid();
        initWidget(uiBinder.createAndBindUi(this));

        dataProvider.addDataDisplay(valueTypeTable);
        valueTypeTable.setSelectionModel(valueTypeSelectionModel);
        valueTypeTable.setEmptyTableWidget(new EmptyResult(sharedMessageBundle.noResultFound(), AlertType.WARNING));
        valueTypeSelectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                getUiHandlers().valueTypeChanged(valueTypeSelectionModel.getSelectedObject());
            }
        });
    }

    @Override
    public void setData(List<ValueTypeProxy> data) {
        valueTypeTable.setPageSize(data.size());
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
    }
}
