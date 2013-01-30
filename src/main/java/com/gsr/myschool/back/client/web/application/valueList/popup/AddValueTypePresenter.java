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
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueListBox;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.back.client.request.ValueListServiceRequest;
import com.gsr.myschool.back.client.request.ValueTypeServiceRequest;
import com.gsr.myschool.common.client.proxy.ValueListProxy;
import com.gsr.myschool.common.client.proxy.ValueTypeProxy;
import com.gsr.myschool.common.client.mvp.ValidatedPopupView;
import com.gsr.myschool.common.client.request.ValidatedReceiverImpl;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;

public class AddValueTypePresenter extends PresenterWidget<AddValueTypePresenter.MyView>
        implements AddValueTypeUiHandlers {
    public interface MyView extends ValidatedPopupView, HasUiHandlers<AddValueTypeUiHandlers> {
        void fillParentList(List<ValueTypeProxy> parents);

        void fillRegexList(List<ValueListProxy> regex);

        void editType(ValueTypeProxy valueTypeProxy);

        void flushType();
    }

    private final BackRequestFactory requestFactory;
    private ValueTypeProxy currentValueType;

    @Inject
    public AddValueTypePresenter(final EventBus eventBus, final MyView view,
                                 final BackRequestFactory requestFactory) {
        super(eventBus, view);

        this.requestFactory = requestFactory;

        getView().setUiHandlers(this);
    }

    public void initDatas() {
        getAllDefLov();
        getRegexes();
    }

    public void getAllDefLov() {
        ValueTypeServiceRequest dlsr = requestFactory.valueTypeServiceRequest();

        dlsr.findAll().fire(new Receiver<List<ValueTypeProxy>>() {
            @Override
            public void onSuccess(List<ValueTypeProxy> response) {
                getView().fillParentList(response);
            }
        });
    }

    public void getRegexes() {
        ValueListServiceRequest lsr = requestFactory.valueListServiceRequest();
        /*lsr.findByValueTypeName("Regex").fire(new Receiver<List<ValueListProxy>>() {
            @Override
            public void onSuccess(List<ValueListProxy> response) {
                getView().fillRegexList(response);
            }
        });*/
    }

    @Override
    protected void onReveal() {
        super.onReveal();
        getView().editType(currentValueType);
    }

    @Override
    public void saveValueType() {
        getView().flushType();

        requestFactory.valueTypeServiceRequest().add(currentValueType).fire(new ValidatedReceiverImpl<Void>(){


            @Override
            public void onValidationError(Set<ConstraintViolation<?>> violations) {
                getView().clearErrors();
                getView().showErrors(violations);
            }

            @Override
            public void onSuccess(Void aVoid) {
                getView().clearErrors();
                getView().editType(currentValueType);
            }
        });
    }
}
