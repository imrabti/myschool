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
import com.gsr.myschool.common.client.proxy.ValueListProxy;

public class ValueListChangedEvent extends GwtEvent<ValueListChangedEvent.ValueListChangedHandler> {
    public interface ValueListChangedHandler extends EventHandler {
        void onValueListChanged(ValueListChangedEvent event);
    }

    public static Type<ValueListChangedHandler> TYPE = new Type<ValueListChangedHandler>();

    private ValueListProxy valueList;

    public ValueListChangedEvent(){
    }

    public ValueListChangedEvent(ValueListProxy valueList){
        this.valueList = valueList;
    }

    public ValueListProxy getValueList(){
        return valueList;
    }

    @Override
    public Type<ValueListChangedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ValueListChangedHandler handler) {
        handler.onValueListChanged(this);
    }
}
