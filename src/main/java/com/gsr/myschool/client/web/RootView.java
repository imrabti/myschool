package com.gsr.myschool.client.web;

import com.arcbees.core.client.mvp.ViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class RootView extends ViewImpl implements RootPresenter.MyView {
    public interface Binder extends UiBinder<Widget, RootView> {
    }

    @UiField
    SimpleLayoutPanel main;
    @UiField
    SimplePanel messageDisplay;

    @Inject
    public RootView(final Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setInSlot(Object slot, Widget content) {
        if (slot == RootPresenter.TYPE_SetMainContent) {
            main.setWidget(content);
        } else if (slot == RootPresenter.TYPE_SetMessageContent) {
            messageDisplay.setWidget(content);
        }
    }
}
