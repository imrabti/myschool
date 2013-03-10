package com.gsr.myschool.front.client.web.application.help.ui;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.resource.SharedResources;

public class HelpTutorial extends PopupPanel {
    interface Binder extends UiBinder<Widget, HelpTutorial> {
    }

    @Inject
    public HelpTutorial(final Binder uiBinder, final SharedResources resources) {
        setWidget(uiBinder.createAndBindUi(this));

        setStyleName(resources.popupStyleCss().dropShadowPopup());
        setModal(false);
        setAutoHideEnabled(true);
    }
}
