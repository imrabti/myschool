package com.gsr.myschool.back.client.web.application.reception.ui;

import com.github.gwtbootstrap.client.ui.TextBox;
import com.github.gwtbootstrap.client.ui.ValueListBox;
import com.github.gwtbootstrap.datepicker.client.ui.DateBoxAppended;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.proxy.DossierFilterDTOProxy;
import com.gsr.myschool.common.client.util.EditorView;
import com.gsr.myschool.common.client.widget.renderer.EnumRenderer;
import com.gsr.myschool.common.shared.type.DossierStatus;

import java.util.Arrays;

public class DossierFilterEditor extends Composite implements EditorView<DossierFilterDTOProxy> {
    public interface Binder extends UiBinder<Widget, DossierFilterEditor> {
    }

    public interface Driver extends SimpleBeanEditorDriver<DossierFilterDTOProxy, DossierFilterEditor> {
    }

    @UiField
    TextBox numDossier;
    @UiField
    TextBox firstnameOrlastname;

    private final Driver driver;

    @Inject
    public DossierFilterEditor(final Binder uiBinder, final Driver driver) {
        this.driver = driver;

        initWidget(uiBinder.createAndBindUi(this));

        driver.initialize(this);
    }

    @Override
    public void edit(DossierFilterDTOProxy object) {
        numDossier.setFocus(true);
        driver.edit(object);
    }

    @Override
    public DossierFilterDTOProxy get() {
        DossierFilterDTOProxy dossierFilter = driver.flush();
        if (driver.hasErrors()) {
            return null;
        } else {
            return dossierFilter;
        }
    }
}
