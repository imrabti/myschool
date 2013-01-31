package com.gsr.myschool.back.client.web.application.valueList.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.gsr.myschool.common.client.proxy.ValueTypeProxy;

public class ValueTypeChangedEvent extends GwtEvent<ValueTypeChangedEvent.ValueTypeChangedHandler> {
    public static Type<ValueTypeChangedHandler> TYPE = new Type<ValueTypeChangedHandler>();

    public interface ValueTypeChangedHandler extends EventHandler {
        void onValueTypeChanged(ValueTypeChangedEvent event);
    }

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
