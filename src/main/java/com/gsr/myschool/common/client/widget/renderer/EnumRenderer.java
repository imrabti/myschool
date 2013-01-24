package com.gsr.myschool.common.client.widget.renderer;

import com.google.gwt.text.shared.AbstractRenderer;

public class EnumRenderer<T> extends AbstractRenderer<T> {
    @Override
    public String render(T object) {
        return object == null ? "" : object.toString();
    }
}
