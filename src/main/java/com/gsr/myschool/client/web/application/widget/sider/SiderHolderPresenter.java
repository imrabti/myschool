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

package com.gsr.myschool.client.web.application.widget.sider;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.client.event.SetVisibleSiderEvent;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class SiderHolderPresenter extends PresenterWidget<SiderHolderPresenter.MyView>
        implements SetVisibleSiderEvent.SetVisibleSiderHandler {
    public interface MyView extends View {
    }

    public static final Object TYPE_SetSiderContent = new Object();

    @Inject
    public SiderHolderPresenter(EventBus eventBus, MyView view) {
        super(eventBus, view);
    }

    @Override
    public void onVisibleSider(SetVisibleSiderEvent event) {
        clearSlot(TYPE_SetSiderContent);
        setInSlot(TYPE_SetSiderContent, event.getSider());
    }

    @Override
    protected void onBind() {
        addRegisteredHandler(SetVisibleSiderEvent.getType(), this);
    }
}
