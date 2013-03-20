package com.gsr.myschool.back.client.web.application.validation.popup.ui;

import com.github.gwtbootstrap.client.ui.CheckBox;
import com.github.gwtbootstrap.client.ui.Label;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.proxy.PieceJustifProxy;
import com.gsr.myschool.common.client.util.EditorView;

public class PieceJustificatifEditor extends Composite implements EditorView<PieceJustifProxy> {
    interface Binder extends UiBinder<Widget, PieceJustificatifEditor> {
    }

    public interface Driver extends SimpleBeanEditorDriver<PieceJustifProxy, PieceJustificatifEditor> {
    }

    @UiField
    @Ignore
    CheckBox checked;
    @UiField
    Label nom;

    private final Driver driver;

    @Inject
    public PieceJustificatifEditor(final Binder uiBinder, final Driver driver) {
        this.driver = driver;

        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void edit(PieceJustifProxy object) {
        driver.edit(object);
    }

    @Override
    public PieceJustifProxy get() {
        PieceJustifProxy piece = driver.flush();
        if (driver.hasErrors()) {
            return null;
        } else {
            return piece;
        }
    }
}
