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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;
import com.gsr.myschool.back.client.request.proxy.ValueTypeProxy;
import com.gsr.myschool.common.client.mvp.ViewWithUiHandlers;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;

public class ValueTypeView extends ViewWithUiHandlers<ValueTypeUiHandlers> implements ValueTypePresenter.MyView {
    public interface Binder extends UiBinder<Widget, ValueTypeView> {
    }

    @UiField
    CellTable<ValueTypeProxy> defLovTable;

    @UiField
    Button delete;

    @UiField
    Button modify;

    public SingleSelectionModel<ValueTypeProxy> defLovSelectionModel;

    @Inject
    public ValueTypeView(final Binder uiBinder, final UiHandlersStrategy<ValueTypeUiHandlers> uiHandlers) {
        super(uiHandlers);

        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public CellTable<ValueTypeProxy> getDefLovTable() {
        return defLovTable;
    }

    @Override
    public void buildTable() {
        TextColumn<ValueTypeProxy> nameColumn = new TextColumn<ValueTypeProxy>() {
            @Override
            public String getValue(ValueTypeProxy defLovProxy) {
                return defLovProxy.getName();
            }
        };

        TextColumn<ValueTypeProxy> regexColumn = new TextColumn<ValueTypeProxy>() {
            @Override
            public String getValue(ValueTypeProxy defLovProxy) {
                if (defLovProxy.getRegex() == null) {
                    return "";
                }
                return defLovProxy.getRegex().getLabel();
            }
        };

        TextColumn<ValueTypeProxy> parentColumn = new TextColumn<ValueTypeProxy>() {
            @Override
            public String getValue(ValueTypeProxy defLovProxy) {
                if (defLovProxy.getParent() != null) {
                    return defLovProxy.getParent().getName();
                } else {
                    return "";
                }
            }
        };

        getDefLovTable().addColumn(nameColumn, "Nom");
        getDefLovTable().addColumn(regexColumn, "Regex");
        getDefLovTable().addColumn(parentColumn, "Parent");
        this.defLovSelectionModel = new SingleSelectionModel<ValueTypeProxy>();
        defLovTable.setSelectionModel(defLovSelectionModel);
    }

    @Override
    public Button getDelete() {
        return delete;
    }

    @Override
    public Button getModify() {
        return modify;
    }

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
