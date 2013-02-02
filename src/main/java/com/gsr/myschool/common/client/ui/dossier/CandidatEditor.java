package com.gsr.myschool.common.client.ui.dossier;

import com.github.gwtbootstrap.client.ui.TextBox;
import com.github.gwtbootstrap.client.ui.ValueListBox;
import com.github.gwtbootstrap.datepicker.client.ui.DateBoxAppended;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.proxy.CandidatProxy;
import com.gsr.myschool.common.client.proxy.ValueListProxy;
import com.gsr.myschool.common.client.util.EditorView;
import com.gsr.myschool.common.client.util.ValueList;
import com.gsr.myschool.common.client.widget.renderer.ValueListRenderer;
import com.gsr.myschool.common.shared.type.ValueTypeCode;

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
    @UiField(provided = true)
    ValueListBox<ValueListProxy> nationality;
    @UiField(provided = true)
    ValueListBox<ValueListProxy> bacSerie;
    @UiField(provided = true)
    ValueListBox<ValueListProxy> bacYear;

    private final Driver driver;

    @Inject
    public CandidatEditor(final Binder uiBinder, final Driver driver,
                          final ValueList valueList,
                          final ValueListRenderer valueListRenderer) {
        this.driver = driver;

        nationality = new ValueListBox<ValueListProxy>(valueListRenderer);
        bacSerie = new ValueListBox<ValueListProxy>(valueListRenderer);
        bacYear = new ValueListBox<ValueListProxy>(valueListRenderer);

        initWidget(uiBinder.createAndBindUi(this));
        driver.initialize(this);

        nationality.setAcceptableValues(valueList.getValueListByCode(ValueTypeCode.NATIONALITY));
        bacYear.setAcceptableValues(valueList.getValueListByCode(ValueTypeCode.BAC_YEAR));
        bacSerie.setAcceptableValues(valueList.getValueListByCode(ValueTypeCode.BAC_SERIE));

        $(firstname).id("firstname");
        $(lastname).id("lastname");
        $(birthDate).id("birthDate");
        $(birthLocation).id("birthLocation");
        $(email).id("email");
        $(phone).id("phone");
        $(cin).id("cin");
        $(cne).id("cne");
        $(nationality).id("nationality");
        $(bacYear).id("bacYear");
        $(bacSerie).id("bacSerie");
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
