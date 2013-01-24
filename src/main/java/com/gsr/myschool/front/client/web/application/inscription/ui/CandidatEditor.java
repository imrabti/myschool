package com.gsr.myschool.front.client.web.application.inscription.ui;

import com.github.gwtbootstrap.client.ui.TextBox;
import com.github.gwtbootstrap.datepicker.client.ui.DateBoxAppended;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.proxy.CandidatProxy;
import com.gsr.myschool.common.client.util.EditorView;

import static com.google.gwt.query.client.GQuery.$;

public class CandidatEditor extends Composite implements EditorView<CandidatProxy> {
    public interface Binder extends UiBinder<Widget, CandidatEditor> {
    }

    public interface Driver extends SimpleBeanEditorDriver<CandidatProxy, CandidatEditor> {
    }

    @UiField
    TextBox firstname;
    @UiField
    TextBox lastname;
    @UiField
    DateBoxAppended birthDate;
    @UiField
    TextBox birthLocation;
    @UiField
    TextBox email;
    @UiField
    TextBox phone;
    @UiField
    TextBox cin;
    @UiField
    TextBox cne;

    private final Driver driver;

    @Inject
    public CandidatEditor(final Binder uiBinder, final Driver driver) {
        this.driver = driver;

        initWidget(uiBinder.createAndBindUi(this));
        driver.initialize(this);

        $(firstname).id("firstname");
        $(lastname).id("lastname");
        $(birthDate).id("birthDate");
        $(birthLocation).id("birthLocation");
        $(email).id("email");
        $(phone).id("phone");
        $(cin).id("cin");
        $(cne).id("cne");
    }

    @Override
    public void edit(CandidatProxy candidat) {
        firstname.setFocus(true);
        driver.edit(candidat);
    }

    @Override
    public CandidatProxy get() {
        CandidatProxy candidat = driver.flush();
        if (driver.hasErrors()) {
            return null;
        } else {
            return candidat;
        }
    }
}
