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

package com.gsr.myschool.front.client.web.application.inbox.popup;

import com.github.gwtbootstrap.client.ui.Label;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.mvp.ValidatedPopupViewImpl;
import com.gsr.myschool.common.client.mvp.ValidationErrorPopup;
import com.gsr.myschool.common.client.proxy.InboxProxy;
import com.gsr.myschool.common.client.widget.ModalHeader;

public class InboxDetailsView extends ValidatedPopupViewImpl
        implements InboxDetailsPresenter.MyView {
    public interface Binder extends UiBinder<PopupPanel, InboxDetailsView> {
    }

    @UiField(provided = true)
    ModalHeader modalHeader;
    @UiField
    HTML content;
    @UiField
    TextBox subject;
    @UiField
    TextBox date;

    private final DateTimeFormat dateFormat;

    @Inject
    protected InboxDetailsView(EventBus eventBus, final Binder uiBinder,
                               final ValidationErrorPopup errorPopup,
                               final ModalHeader modalHeader) {
        super(eventBus, errorPopup);

        this.modalHeader = modalHeader;

        initWidget(uiBinder.createAndBindUi(this));

        modalHeader.addCloseHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                hide();
            }
        });

        dateFormat = DateTimeFormat.getFormat("LLL d yyyy");
    }

    @UiHandler("cancel")
    void onCancelClicked(ClickEvent event) {
        hide();
    }

    public void setDatas(InboxProxy value){
        content.setHTML(value.getContent());
        subject.setText(value.getSubject());
        date.setText(dateFormat.format(value.getMsgDate()));
    }
}
