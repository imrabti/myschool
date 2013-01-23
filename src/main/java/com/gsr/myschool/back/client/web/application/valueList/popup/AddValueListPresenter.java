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

package com.gsr.myschool.back.client.web.application.valueList.popup;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.back.client.request.ValueListServiceRequest;
import com.gsr.myschool.back.client.request.ValueTypeServiceRequest;
import com.gsr.myschool.back.client.request.proxy.ValueListProxy;
import com.gsr.myschool.back.client.request.proxy.ValueTypeProxy;
import com.gsr.myschool.common.client.mvp.ValidatedPopupView;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;

import java.util.List;

public class AddValueListPresenter extends PresenterWidget<AddValueListPresenter.MyView>
        implements AddValueListUiHandlers {
    public interface MyView extends ValidatedPopupView, HasUiHandlers<AddValueListUiHandlers> {
        ListBox getParent();

        ListBox getDefLov();

        TextBox getValue();

        TextBox getLabel();
    }

    public final BackRequestFactory requestFactory;
    public List<ValueTypeProxy> defLovs;
    public List<ValueListProxy> parents;

    @Inject
    public AddValueListPresenter(final EventBus eventBus, final MyView view,
                                 final BackRequestFactory requestFactory) {
        super(eventBus, view);

        this.requestFactory = requestFactory;

        getView().setUiHandlers(this);
    }

    public void initDatas() {
        fillDef();
    }

    public void fillDef() {
        final int selectedDef = getView().getDefLov().getSelectedIndex();
        getView().getDefLov().clear();
        ValueTypeServiceRequest dlsr = requestFactory.valueTypeServiceRequest();
        dlsr.findAll().fire(new Receiver<List<ValueTypeProxy>>() {
            @Override
            public void onSuccess(List<ValueTypeProxy> response) {
                for (ValueTypeProxy defLovProxy : response) {
                    getView().getDefLov().addItem(defLovProxy.getName(), defLovProxy.getId().toString());
                    defLovs.add(defLovProxy);
                }
                if (selectedDef < response.size() && selectedDef >= 0) {
                    getView().getDefLov().setSelectedIndex(selectedDef);
                }
                fillParent();
            }
        });
    }

    public void fillParent() {
        getView().getParent().clear();
        ValueListServiceRequest lsr = requestFactory.valueListServiceRequest();
        lsr.findByValueTypeParentName(getView().getDefLov().getItemText(getView().getDefLov().getSelectedIndex()))
                .fire(new Receiver<List<ValueListProxy>>() {
                    @Override
                    public void onSuccess(List<ValueListProxy> response) {
                        getView().getParent().addItem("Aucun", "0");
                        parents.clear();
                        for (ValueListProxy lovProxy : response) {
                            getView().getParent().addItem(lovProxy.getValue(), lovProxy.getId().toString());
                            parents.add(lovProxy);
                        }
                    }
                });
    }

    @Override
    public void getParent() {
        fillParent();
    }

    @Override
    public void processLov() {
        ValueListServiceRequest lsr = requestFactory.valueListServiceRequest();
        ValueListProxy lp = lsr.create(ValueListProxy.class);
        lp.setValueType(defLovs.get(getView().getDefLov().getSelectedIndex()));
        getView().getParent().getSelectedIndex();
        lp.getValueType().getId();
        if ("".equals(getView().getLabel().getText())) {
            lp.setLabel(getView().getValue().getText());
        } else {
            lp.setLabel(getView().getLabel().getText());
        }
        if (getView().getParent().getSelectedIndex() != 0) {
            lp.setParent(parents.get(getView().getParent().getSelectedIndex() - 1));
            lp.getParent().getId();
        }
        lp.setValue(getView().getValue().getText());
        lp.getValue();
        lsr.add(lp).fire(new Receiver<Void>() {
            @Override
            public void onSuccess(Void response) {
                Window.alert("added");
            }
        });
    }
}
