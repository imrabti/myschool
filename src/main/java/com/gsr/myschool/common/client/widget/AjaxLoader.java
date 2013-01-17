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

package com.gsr.myschool.common.client.widget;

import com.gsr.myschool.common.client.resource.SharedResources;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class AjaxLoader extends Widget implements HasText {
    private Timer delayedTimer = new Timer() {
        @Override
        public void run() {
            setVisible(true);
        }
    };

    @Inject
    public AjaxLoader(final SharedResources sharedResources) {
        setElement(DOM.createElement("div"));
        getElement().setInnerText("Loading...");
        setStyleName(getElement(), sharedResources.generalStyleCss().ajaxLoader());
        setVisible(false);
    }

    public void display(int time) {
        delayedTimer.schedule(time);
    }

    public void hide() {
        delayedTimer.cancel();
        setVisible(false);
    }

    @Override
    public String getText() {
        return getElement().getInnerText();
    }

    @Override
    public void setText(String text) {
        getElement().setInnerText(text);
    }
}
