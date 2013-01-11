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

package com.gsr.myschool.common.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;

public class SetVisibleSiderEvent extends GwtEvent<SetVisibleSiderEvent.SetVisibleSiderHandler> {
    public interface SetVisibleSiderHandler extends EventHandler {
        void onVisibleSider(SetVisibleSiderEvent event);
    }

    private static final Type<SetVisibleSiderHandler> TYPE = new Type<SetVisibleSiderHandler>();

    @SuppressWarnings("rawtypes")
    private PresenterWidget sider;

    @SuppressWarnings("rawtypes")
    public SetVisibleSiderEvent(PresenterWidget sider) {
        this.sider = sider;
    }

    public static Type<SetVisibleSiderHandler> getType() {
        return TYPE;
    }

    @SuppressWarnings("rawtypes")
    public static void fire(HasHandlers source, PresenterWidget sider) {
        source.fireEvent(new SetVisibleSiderEvent(sider));
    }

    @SuppressWarnings("rawtypes")
    public PresenterWidget getSider() {
        return sider;
    }

    @Override
    public Type<SetVisibleSiderHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(SetVisibleSiderHandler handler) {
        handler.onVisibleSider(this);
    }
}
