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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.mvp.ValidatedPopupViewImplWithUiHandlers;
import com.gsr.myschool.common.client.widget.ValidationErrorPopup;
import com.gsr.myschool.common.client.proxy.InboxProxy;
import com.gsr.myschool.common.client.widget.ModalHeader;
import com.gsr.myschool.front.client.resource.message.MessageBundle;

public class InboxDetailsView extends ValidatedPopupViewImplWithUiHandlers<InboxDetailsUiHandlers>
        implements InboxDetailsPresenter.MyView {
    public interface Binder extends UiBinder<PopupPanel, InboxDetailsView> {
    }

    @UiField(provided = true)
    ModalHeader modalHeader;
    @UiField
    HTML content;
    @UiField
    Label date;

    private final MessageBundle messages;
    private final DateTimeFormat dateFormat;

    @Inject
    protected InboxDetailsView(final EventBus eventBus, final Binder uiBinder,
                               final ValidationErrorPopup errorPopup,
                               final ModalHeader modalHeader,
                               final MessageBundle messages) {
        super(eventBus, errorPopup);

        this.messages = messages;
        this.modalHeader = modalHeader;

        initWidget(uiBinder.createAndBindUi(this));

        modalHeader.addCloseHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                getUiHandlers().readComplete();
                hide();
            }
        });

        dateFormat = DateTimeFormat.getFormat("dd/MM/yyyy 'à' HH:mm");
    }

    public void setDatas(InboxProxy value){
        content.setHTML(value.getContent());
        modalHeader.setText(value.getSubject());
        date.setText(messages.inboxDateMessage(dateFormat.format(value.getMsgDate())));
    }
}
