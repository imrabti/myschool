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

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.back.client.request.ValueListServiceRequest;
import com.gsr.myschool.back.client.resource.message.MessageBundle;
import com.gsr.myschool.back.client.web.application.valueList.event.ValueListChangedEvent;
import com.gsr.myschool.common.client.mvp.ValidatedPopupView;
import com.gsr.myschool.common.client.mvp.ValidationErrorPopup;
import com.gsr.myschool.common.client.proxy.ValueListProxy;
import com.gsr.myschool.common.client.proxy.ValueTypeProxy;
import com.gsr.myschool.common.client.request.ValidatedReceiverImpl;
import com.gsr.myschool.common.client.widget.messages.CloseDelay;
import com.gsr.myschool.common.client.widget.messages.Message;
import com.gsr.myschool.common.client.widget.messages.event.MessageEvent;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class AddValueListPresenter extends PresenterWidget<AddValueListPresenter.MyView>
        implements AddValueListUiHandlers {
    public interface MyView extends ValidatedPopupView, HasUiHandlers<AddValueListUiHandlers> {
        void editValue(ValueListProxy valueList);

        void flushValue();
    }

    private final BackRequestFactory requestFactory;
    private final MessageBundle messageBundle;
    private ValueListProxy currentValueList;
    private ValueListServiceRequest currentContext;

    @Inject
    public AddValueListPresenter(final EventBus eventBus, final MyView view,
                                 final BackRequestFactory requestFactory,
                                 final MessageBundle messageBundle) {
        super(eventBus, view);

        this.requestFactory = requestFactory;
        this.messageBundle = messageBundle;

        getView().setUiHandlers(this);
    }

    public void initDatas(ValueTypeProxy valueType) {
        currentContext = requestFactory.valueListServiceRequest();
        currentValueList = currentContext.create(ValueListProxy.class);
        currentValueList.setValueType(valueType);
        getView().editValue(currentValueList);
    }

    public void editDatas(ValueListProxy valuelist) {
        currentContext = requestFactory.valueListServiceRequest();
        currentValueList = currentContext.edit(valuelist);
        getView().editValue(currentValueList);
    }

    @Override
    public void saveValueList() {
        getView().flushValue();

        currentValueList.setValueType(currentContext.edit(currentValueList.getValueType()));
        currentValueList.setParent(currentValueList.getParent() != null ?
                currentContext.edit(currentValueList.getParent()) : null);
        currentContext.addValueList(currentValueList).fire(new ValidatedReceiverImpl<Void>() {

            @Override
            public void onValidationError(Set<ConstraintViolation<?>> violations) {
                getView().clearErrors();
                getView().showErrors(violations);
            }

            @Override
            public void onSuccess(Void aVoid) {
                getView().clearErrors();
                getView().editValue(currentValueList);
                Message message = new Message.Builder(messageBundle.addValueListSuccess())
                        .style(AlertType.SUCCESS)
                        .closeDelay(CloseDelay.DEFAULT)
                        .build();
                MessageEvent.fire(this, message);
                fireEvent(new ValueListChangedEvent(currentValueList));
                getView().hide();
            }
        });
    }

    @Override
    protected void onReveal(){
        super.onReveal();

        getView().clearErrors();
    }
}
