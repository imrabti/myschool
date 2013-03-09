package com.gsr.myschool.front.client.web.application.help;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.mvp.ViewImpl;

public class HelpView extends ViewImpl implements HelpPresenter.MyView {
    public interface Binder extends UiBinder<Widget, HelpView> {
    }

    @Inject
    public HelpView(final Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }
}
