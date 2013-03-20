package com.gsr.myschool.back.client.web.application.validation.popup;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.HTMLPanel;

public class PiecesJustifcatifView {
    interface PiecesJustifcatifViewUiBinder extends UiBinder<HTMLPanel, PiecesJustifcatifView> {
    }

    private static PiecesJustifcatifViewUiBinder ourUiBinder = GWT.create(PiecesJustifcatifViewUiBinder.class);

    public PiecesJustifcatifView() {
        HTMLPanel rootElement = ourUiBinder.createAndBindUi(this);
    }
}
