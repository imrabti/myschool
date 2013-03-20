package com.gsr.myschool.back.client.web.application.validation.popup.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.HTMLPanel;

/**
 * Created with IntelliJ IDEA.
 * User: imrabti
 * Date: 3/20/13
 * Time: 8:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class PieceJustificatifEditor {
    interface PieceJustificatifEditorUiBinder extends UiBinder<HTMLPanel, PieceJustificatifEditor> {
    }

    private static PieceJustificatifEditorUiBinder ourUiBinder = GWT.create(PieceJustificatifEditorUiBinder.class);

    public PieceJustificatifEditor() {
        HTMLPanel rootElement = ourUiBinder.createAndBindUi(this);

    }
}
