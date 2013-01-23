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

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.mvp.ValidatedPopupViewImplWithUiHandlers;
import com.gsr.myschool.common.client.mvp.ValidationErrorPopup;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gsr.myschool.common.client.widget.ModalHeader;

public class AddValueListView extends ValidatedPopupViewImplWithUiHandlers<AddValueListUiHandlers> implements AddValueListPresenter.MyView {
    public interface Binder extends UiBinder<PopupPanel, AddValueListView> {
    }

    @UiField(provided = true)
    ModalHeader modalHeader;
    @UiField
    ListBox parent;
    @UiField
    ListBox defLov;
    @UiField
    TextBox value;
    @UiField
    TextBox label;

    @Inject
    public AddValueListView(final EventBus eventBus, final Binder uiBinder,
                            final UiHandlersStrategy<AddValueListUiHandlers> uiHandlers,
                            final ValidationErrorPopup errorPopup,
                            final ModalHeader modalHeader) {
        super(eventBus, errorPopup, uiHandlers);

        this.modalHeader = modalHeader;

        initWidget(uiBinder.createAndBindUi(this));

        modalHeader.addCloseHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                hide();
            }
        });
    }

    @UiHandler("cancel")
    void onCancelClicked(ClickEvent event) {
        hide();
    }

    public ListBox getParent() {
        return parent;
    }

    public ListBox getDefLov() {
        return defLov;
    }

    @Override
    public TextBox getValue() {
        return value;
    }

    @Override
    public TextBox getLabel() {
        return label;
    }

    @UiHandler("defLov")
    void onDefLovChanged(ChangeEvent event) {
        getUiHandlers().getParent();
    }

    @UiHandler("save")
    void onSaveClicked(ClickEvent event) {
        getUiHandlers().processLov();
    }
}
