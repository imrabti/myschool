package com.gsr.myschool.common.client.mvp;

import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;

public abstract class PopupViewImpl extends com.gwtplatform.mvp.client.PopupViewImpl {
    private Widget widget;

    protected PopupViewImpl(EventBus eventBus) {
        super(eventBus);
    }

    @Override
    public Widget asWidget() {
        return widget;
    }

    protected void initWidget(Widget widget) {
        this.widget = widget;
    }
}
