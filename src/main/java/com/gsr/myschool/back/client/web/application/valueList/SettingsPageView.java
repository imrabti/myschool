package com.gsr.myschool.back.client.web.application.valueList;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.mvp.ViewImpl;

public class SettingsPageView extends ViewImpl implements SettingsPagePresenter.MyView {
    public interface Binder extends UiBinder<Widget, SettingsPageView> {
    }

    @Inject
    public SettingsPageView(final Binder uiBinder) {
        System.out.println("construction de la view valueList");
        initWidget(uiBinder.createAndBindUi(this));
    }

    /*public SettingsPageView()
    {}*/
}
