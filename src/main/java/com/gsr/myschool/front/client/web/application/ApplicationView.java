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

package com.gsr.myschool.front.client.web.application;

import com.arcbees.core.client.mvp.ViewImpl;
import com.gsr.myschool.common.client.widget.AjaxLoader;
import com.gsr.myschool.front.client.web.application.widget.footer.FooterView;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class ApplicationView extends ViewImpl implements ApplicationPresenter.MyView {
    public interface Binder extends UiBinder<Widget, ApplicationView> {
    }

    @UiField
    SimplePanel headerDisplay;
    @UiField
    SimplePanel siderDisplay;
    @UiField
    SimpleLayoutPanel mainDisplay;
    @UiField(provided = true)
    FooterView footer;
    @UiField(provided = true)
    AjaxLoader ajaxLoader;

    @Inject
    public ApplicationView(final Binder uiBinder, final FooterView footer,
                           final AjaxLoader ajaxLoader) {
        this.footer = footer;
        this.ajaxLoader = ajaxLoader;

        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setInSlot(Object slot, Widget content) {
        if (content != null) {
            if (slot == ApplicationPresenter.TYPE_SetMainContent) {
                mainDisplay.setWidget(content);
            } else if (slot == ApplicationPresenter.TYPE_SetHeaderContent) {
                headerDisplay.setWidget(content);
            } else if (slot == ApplicationPresenter.TYPE_SetSiderContent) {
                siderDisplay.setWidget(content);
            }
        }
    }

    @Override
    public void showAjaxLoader(int timeout) {
        ajaxLoader.display(timeout);
    }

    @Override
    public void hideAjaxLoader() {
        ajaxLoader.hide();
    }
}
