package com.gsr.myschool.front.client.web.welcome;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.mvp.ViewImpl;

public class WelcomeView extends ViewImpl implements WelcomePresenter.MyView {
    public interface Binder extends UiBinder<Widget, WelcomeView> {
    }

    @UiField
    SimplePanel loginPanel;
    @UiField
    SimplePanel registerPanel;

    @Inject
    public WelcomeView(final Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setInSlot(Object slot, Widget content) {
        if (content != null) {
            if (slot == WelcomePresenter.TYPE_SetLoginContent) {
                loginPanel.setWidget(content);
            } else if (slot == WelcomePresenter.TYPE_SetRegisterContent) {
                registerPanel.setWidget(content);
            }
        }
    }
}
