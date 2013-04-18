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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.web.application.valueList.ui.ValueTypeEditor;
import com.gsr.myschool.common.client.mvp.ValidatedPopupViewImplWithUiHandlers;
import com.gsr.myschool.common.client.widget.ValidationErrorPopup;
import com.gsr.myschool.common.client.proxy.ValueTypeProxy;
import com.gsr.myschool.common.client.widget.ModalHeader;

public class AddValueTypeView extends ValidatedPopupViewImplWithUiHandlers<AddValueTypeUiHandlers>
        implements AddValueTypePresenter.MyView {
    interface Binder extends UiBinder<PopupPanel, AddValueTypeView> {
    }

    @UiField(provided = true)
    ModalHeader modalHeader;
    @UiField(provided = true)
    ValueTypeEditor valueTypeEditor;

    @Inject
    public AddValueTypeView(final EventBus eventBus, final Binder uiBinder,
                            final ValidationErrorPopup errorPopup,
                            final ModalHeader modalHeader,
                            final ValueTypeEditor valueTypeEditor) {
        super(eventBus, errorPopup);

        this.modalHeader = modalHeader;
        this.valueTypeEditor = valueTypeEditor;

        initWidget(uiBinder.createAndBindUi(this));

        modalHeader.addCloseHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                hide();
            }
        });
    }

    @Override
    public void flushType() {
        valueTypeEditor.get();
    }

    @Override
    public void editType(ValueTypeProxy valueType) {
        valueTypeEditor.edit(valueType);
    }

    @UiHandler("save")
    void onSaveClick(ClickEvent event) {
        getUiHandlers().saveValueType();
    }

    @UiHandler("cancel")
    void onCancelClicked(ClickEvent event) {
        hide();
    }
}
