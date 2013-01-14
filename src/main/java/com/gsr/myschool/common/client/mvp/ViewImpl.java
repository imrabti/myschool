package com.gsr.myschool.common.client.mvp;

import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.View;

public abstract class ViewImpl implements View {
    private Widget widget;

    @Override
    public void addToSlot(Object slot, Widget content) {
    }

    @Override
    public void removeFromSlot(Object slot, Widget content) {
    }

    @Override
    public void setInSlot(Object slot, Widget content) {
    }

    @Override
    public Widget asWidget() {
        return widget;
    }

    protected void initWidget(Widget widget) {
        this.widget = widget;
    }
}
