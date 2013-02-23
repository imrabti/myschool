package com.gsr.myschool.common.client.widget.renderer;

import com.google.gwt.text.shared.AbstractRenderer;

public class EnumRenderer<T> extends AbstractRenderer<T> {
    private String emptyMessage;

    public EnumRenderer() {
        this("");
    }

    public EnumRenderer(String emptyMessage) {
        this.emptyMessage = emptyMessage;
    }

    @Override
    public String render(T object) {
        return object == null ? emptyMessage : object.toString();
    }
}
