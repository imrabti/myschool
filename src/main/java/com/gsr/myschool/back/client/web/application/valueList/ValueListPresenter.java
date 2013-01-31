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
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ListBox;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.gsr.myschool.back.client.place.NameTokens;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.back.client.web.application.valueList.event.ValueTypeChangedEvent;
import com.gsr.myschool.common.client.proxy.ValueListProxy;
import com.gsr.myschool.common.client.proxy.ValueTypeProxy;
import com.gsr.myschool.back.client.resource.message.MessageBundle;
import com.gsr.myschool.back.client.web.application.ApplicationPresenter;
import com.gsr.myschool.back.client.web.application.valueList.popup.AddValueListPresenter;
import com.gsr.myschool.back.client.web.application.valueList.widget.ValueTypePresenter;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.security.LoggedInGatekeeper;
import com.gsr.myschool.common.client.widget.messages.CloseDelay;
import com.gsr.myschool.common.client.widget.messages.Message;
import com.gsr.myschool.common.client.widget.messages.event.MessageEvent;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import java.util.List;

public class ValueListPresenter extends Presenter<ValueListPresenter.MyView, ValueListPresenter.MyProxy>
        implements ValueListUiHandlers, ValueTypeChangedEvent.ValueTypeChangedHandler{
    public interface MyView extends View, HasUiHandlers<ValueListUiHandlers> {
        CellTable<ValueListProxy> getValueListTable();

        ListBox getParent();

        ListBox getDefLov();

        void setData(List<ValueListProxy> response);
    }

    @ProxyStandard
    @NameToken(value = NameTokens.valueList)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface MyProxy extends ProxyPlace<ValueListPresenter> {
    }

    public static final Object TYPE_SetValueTypeContent = new Object();

    private final AddValueListPresenter addValueListPresenter;
    private final ValueTypePresenter valueTypePresenter;
    private final BackRequestFactory requestFactory;
    private final MessageBundle messageBundle;

    @Inject
    public ValueListPresenter(final EventBus eventBus, final MyView view,
                              final MyProxy proxy,
                              final MessageBundle messageBundle,
                              final AddValueListPresenter addValueListPresenter,
                              final ValueTypePresenter valueTypePresenter,
                              final BackRequestFactory requestFactory) {
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);

        this.addValueListPresenter = addValueListPresenter;
        this.valueTypePresenter = valueTypePresenter;
        this.requestFactory = requestFactory;
        this.messageBundle = messageBundle;

        getView().setUiHandlers(this);
    }

    @Override
    protected void onBind(){
        super.onBind();

        addRegisteredHandler(ValueTypeChangedEvent.TYPE, this);
    }

    @Override
    protected void onReveal() {
        super.onReveal();

        setInSlot(TYPE_SetValueTypeContent, valueTypePresenter);
        fillTable();
    }

    @Override
    public void addValueList() {
        addValueListPresenter.initDatas();
        addToPopupSlot(addValueListPresenter);
    }

    @Override
    public void getParent() {
        fillParent();
    }

    @Override
    public void delete(ValueListProxy valueListProxy) {
        /*backRequestFactory.valueListServiceRequest().delete(valueListProxy.getId()).fire(new ReceiverImpl<Void>() {
            @Override
            public void onSuccess(Void response) {
                Message message = new Message.Builder(messageBundle.deleteValueListSuccess())
                        .style(AlertType.SUCCESS)
                        .closeDelay(CloseDelay.DEFAULT)
                        .build();
                MessageEvent.fire(this, message);
            }
        });*/
    }

    @Override
    public void modify(ValueListProxy valueListProxy) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void fillTable() {
        requestFactory.valueListServiceRequest().findAll().fire(new Receiver<List<ValueListProxy>>() {
            @Override
            public void onSuccess(List<ValueListProxy> response) {
                getView().setData(response);
            }
        });
    }

    public void fillDef() {
        getView().getDefLov().clear();
        /*backRequestFactory.valueTypeServiceRequest().findAll().fire(new ReceiverImpl<List<ValueTypeProxy>>() {
            @Override
            public void onSuccess(List<ValueTypeProxy> response) {
                for (ValueTypeProxy defLovProxy : response) {
                    getView().getDefLov().addItem(defLovProxy.getName(), defLovProxy.getId().toString());
                }
                fillParent();
            }
        });*/
    }

    public void fillParent() {
        getView().getParent().clear();
        /*backRequestFactory.valueListServiceRequest()
                .findByValueTypeName(getView().getDefLov().getItemText(getView().getDefLov().getSelectedIndex()))
                .fire(new ReceiverImpl<List<ValueListProxy>>() {
                    @Override
                    public void onSuccess(List<ValueListProxy> response) {
                        getView().getParent().addItem("Aucun", "0");
                        for (ValueListProxy lovProxy : response) {
                            getView().getParent().addItem(lovProxy.getValue(), lovProxy.getId().toString());
                        }
                    }
                });*/
    }

    @Override
    public void onValueTypeChanged(ValueTypeChangedEvent event) {
        Window.alert(event.getValueType().getCode().toString());
    }
}
