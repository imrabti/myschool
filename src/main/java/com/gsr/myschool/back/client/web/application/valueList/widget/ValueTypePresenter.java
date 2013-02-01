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

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.back.client.request.ValueTypeServiceRequest;
import com.gsr.myschool.back.client.resource.message.MessageBundle;
import com.gsr.myschool.back.client.web.application.valueList.event.ValueTypeChangedEvent;
import com.gsr.myschool.back.client.web.application.valueList.popup.AddValueTypePresenter;
import com.gsr.myschool.common.client.proxy.ValueTypeProxy;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

import java.util.List;

public class ValueTypePresenter extends PresenterWidget<ValueTypePresenter.MyView>
        implements ValueTypeUiHandlers {
    public interface MyView extends View, HasUiHandlers<ValueTypeUiHandlers> {
        void initDataGrid();

        void setData(List<ValueTypeProxy> data);
    }

    private final BackRequestFactory requestFactory;
    private final MessageBundle messageBundle;
    private final AddValueTypePresenter addValueTypePresenter;

    @Inject
    public ValueTypePresenter(final EventBus eventBus, final MyView view,
                              final BackRequestFactory requestFactory,
                              final MessageBundle messageBundle,
                              final AddValueTypePresenter addValueTypePresenter) {
        super(eventBus, view);

        this.requestFactory = requestFactory;
        this.messageBundle = messageBundle;
        this.addValueTypePresenter = addValueTypePresenter;

        getView().setUiHandlers(this);
    }

    @Override
    protected void onReveal() {
        super.onReveal();

        fillTable();
    }

    public void fillTable() {
        requestFactory.valueTypeServiceRequest().findAll().fire(new Receiver<List<ValueTypeProxy>>() {
            @Override
            public void onSuccess(List<ValueTypeProxy> response) {
                getView().setData(response);
            }
        });
    }

    @Override
    public void addValueType() {
        addValueTypePresenter.initDatas();
        addToPopupSlot(addValueTypePresenter);
    }

    @Override
    public void valueTypeChanged(ValueTypeProxy valueTypeProxy) {
        fireEvent(new ValueTypeChangedEvent(valueTypeProxy));
    }

    @Override
    public void editValueType(ValueTypeProxy valueType) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteValueType(ValueTypeProxy valueType) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
