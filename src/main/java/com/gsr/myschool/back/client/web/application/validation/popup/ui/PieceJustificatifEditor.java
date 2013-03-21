package com.gsr.myschool.back.client.web.application.validation.popup.ui;

import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.proxy.PiecejustifDTOProxy;
import com.gsr.myschool.common.client.util.EditorView;

public class PieceJustificatifEditor extends Composite implements EditorView<PiecejustifDTOProxy> {
    interface Binder extends UiBinder<Widget, PieceJustificatifEditor> {
    }

    public interface Driver extends SimpleBeanEditorDriver<PiecejustifDTOProxy, PieceJustificatifEditor> {
    }

    @UiField
    ToggleButton available;
    @UiField
    Label nom;
    @UiField
    TextBox motif;

    private final Driver driver;

    @Inject
    public PieceJustificatifEditor(final Binder uiBinder, final Driver driver) {
        this.driver = driver;

        initWidget(uiBinder.createAndBindUi(this));
        driver.initialize(this);
    }

    @Override
    public void edit(PiecejustifDTOProxy object) {
        driver.edit(object);
    }

    @Override
    public PiecejustifDTOProxy get() {
        PiecejustifDTOProxy piece = driver.flush();
        if (driver.hasErrors()) {
            return null;
        } else {
            return piece;
        }
    }
}
