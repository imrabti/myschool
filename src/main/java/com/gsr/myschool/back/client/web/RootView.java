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

package com.gsr.myschool.back.client.web;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.widget.ExceptionPopup;
import com.gwtplatform.mvp.client.ViewImpl;

public class RootView extends ViewImpl implements RootPresenter.MyView {
    public interface Binder extends UiBinder<Widget, RootView> {
    }

    @UiField
    SimpleLayoutPanel main;
    @UiField
    SimplePanel messageDisplay;

    private final ExceptionPopup exceptionPopup;

    @Inject
    public RootView(final Binder uiBinder,
                    final ExceptionPopup exceptionPopup) {
        this.exceptionPopup = exceptionPopup;

        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setInSlot(Object slot, IsWidget content) {
        if (slot == RootPresenter.TYPE_SetMainContent) {
            main.setWidget(content);
        } else if (slot == RootPresenter.TYPE_SetMessageContent) {
            messageDisplay.setWidget(content);
        }
    }

    @Override
    public void hideLoading() {
        Element loading = Document.get().getElementById("loading");
        loading.getParentElement().removeChild(loading);
    }

    @Override
    public void showErrorPopup(String message) {
        exceptionPopup.setMessage(message);
        if (!exceptionPopup.isVisible()) {
            exceptionPopup.center();
        }
    }
}
