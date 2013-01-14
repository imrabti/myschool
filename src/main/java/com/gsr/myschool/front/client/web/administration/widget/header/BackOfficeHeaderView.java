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

package com.gsr.myschool.front.client.web.administration.widget.header;

import com.github.gwtbootstrap.client.ui.NavText;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.mvp.ViewWithUiHandlers;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gsr.myschool.front.client.resource.message.MessageBundle;

public class BackOfficeHeaderView extends ViewWithUiHandlers<BackOfficeHeaderUiHandlers> implements BackOfficeHeaderPresenter.MyView {
    public interface Binder extends UiBinder<Widget, BackOfficeHeaderView> {
    }

    @UiField
    NavText welcomeMessage;

    private final MessageBundle messageBundle;

    @Inject
    public BackOfficeHeaderView(final Binder uiBinder, final MessageBundle messageBundle,
			final UiHandlersStrategy<BackOfficeHeaderUiHandlers> uiHandlersStrategy) {
        super(uiHandlersStrategy);

        this.messageBundle = messageBundle;

        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void displayUserInfo(String username) {
        welcomeMessage.setText(messageBundle.welcomeMessage(username));
    }

    @UiHandler("logout")
    void onLogoutClicked(ClickEvent event) {
        getUiHandlers().logout();
    }

    @UiHandler("setting")
    void onSettingClicked(ClickEvent event) {
        getUiHandlers().setting();
    }
}
