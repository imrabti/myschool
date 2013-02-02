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
        void editType(ValueTypeProxy valueTypeProxy);

        void flushType();
    }

    private final BackRequestFactory requestFactory;
    private ValueTypeProxy currentValueType;
    private ValueTypeServiceRequest currentContext;

    @Inject
    public AddValueTypePresenter(final EventBus eventBus, final MyView view,
                                 final BackRequestFactory requestFactory) {
        super(eventBus, view);

        this.requestFactory = requestFactory;

        getView().setUiHandlers(this);
    }

    public void initDatas() {
        currentContext = requestFactory.valueTypeServiceRequest();
        currentValueType = currentContext.create(ValueTypeProxy.class);
        getView().editType(currentValueType);
    }

    public void editDatas(ValueTypeProxy valueType) {
        currentContext = requestFactory.valueTypeServiceRequest();
        currentValueType = currentContext.edit(valueType);
        getView().editType(currentValueType);
    }

    @Override
    public void saveValueType() {
        getView().flushType();

        currentContext.updateValueType(currentValueType).fire(new ValidatedReceiverImpl<Void>(){
            @Override
            public void onValidationError(Set<ConstraintViolation<?>> violations) {
                getView().clearErrors();
                getView().showErrors(violations);
            }

            @Override
            public void onSuccess(Void aVoid) {
                getView().clearErrors();
                getView().editType(currentValueType);
                getView().hide();
            }
        });
    }
}
