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

package com.gsr.myschool.back.client.web.application.valueList.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.gsr.myschool.common.client.proxy.ValueTypeProxy;

public class ValueTypeChangedEvent extends GwtEvent<ValueTypeChangedEvent.ValueTypeChangedHandler> {
    public interface ValueTypeChangedHandler extends EventHandler {
        void onValueTypeChanged(ValueTypeChangedEvent event);
    }

    public static Type<ValueTypeChangedHandler> TYPE = new Type<ValueTypeChangedHandler>();

    private ValueTypeProxy valueType;

    public ValueTypeChangedEvent() {
    }

    public ValueTypeChangedEvent(ValueTypeProxy valueType) {
        this.valueType = valueType;
    }

    public ValueTypeProxy getValueType() {
        return valueType;
    }

    @Override
    public Type<ValueTypeChangedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ValueTypeChangedHandler handler) {
        handler.onValueTypeChanged(this);
    }
}
