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

package com.gsr.myschool.common.client.mvp;

import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gsr.myschool.common.client.widget.ValidationErrorPopup;
import com.gwtplatform.mvp.client.UiHandlers;

public class ValidatedPopupViewImplWithUiHandlers<H extends UiHandlers> extends ValidatedPopupViewImpl
        implements UiHandlersStrategy<H> {
    private UiHandlersStrategy<H> uiHandlersStrategy;

    public ValidatedPopupViewImplWithUiHandlers(EventBus eventBus, ValidationErrorPopup errorPopup,
                                                UiHandlersStrategy<H> uiHandlersStrategy) {
        super(eventBus, errorPopup);
        this.uiHandlersStrategy = uiHandlersStrategy;
    }

    @Override
    public H getUiHandlers() {
        return uiHandlersStrategy.getUiHandlers();
    }

    @Override
    public void setUiHandlers(H uiHandlers) {
        uiHandlersStrategy.setUiHandlers(uiHandlers);
    }
}
