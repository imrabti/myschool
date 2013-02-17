package com.gsr.myschool.common.client.ui.dossier;

import com.github.gwtbootstrap.client.ui.ControlGroup;
import com.github.gwtbootstrap.client.ui.TextArea;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.github.gwtbootstrap.client.ui.ValueListBox;
import com.github.gwtbootstrap.datepicker.client.ui.DateBoxAppended;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.proxy.InfoParentProxy;
import com.gsr.myschool.common.client.proxy.ValueListProxy;
import com.gsr.myschool.common.client.util.EditorView;
import com.gsr.myschool.common.client.util.ValueList;
import com.gsr.myschool.common.client.widget.renderer.EnumRenderer;
import com.gsr.myschool.common.client.widget.renderer.ValueListRenderer;
import com.gsr.myschool.common.shared.constants.GlobalParameters;
import com.gsr.myschool.common.shared.type.Gender;
import com.gsr.myschool.common.shared.type.ValueTypeCode;

import java.util.Arrays;

import static com.google.gwt.query.client.GQuery.$;

public class ParentEditor extends Composite implements EditorView<InfoParentProxy> {
    public interface Binder extends UiBinder<Widget, ParentEditor> {
    }

    public interface Driver extends SimpleBeanEditorDriver<InfoParentProxy, ParentEditor> {
    }

    @UiField
    TextBox nom;
    @UiField
    TextBox prenom;
    @UiField(provided = true)
    ValueListBox<Gender> civilite;
    @UiField
    TextBox lientParente;
    @UiField
    DateBoxAppended birthDate;
    @UiField
    TextBox birthLocation;
    @UiField(provided = true)
    ValueListBox<ValueListProxy> nationality;
    @UiField
    TextBox fonction;
    @UiField
    TextBox institution;
    @UiField
    TextBox email;
    @UiField
    TextBox telDom;
    @UiField
    TextBox telGsm;
    @UiField
    TextBox telBureau;
    @UiField
    TextArea address;
    @UiField
    ControlGroup civiliteWrapper;
    @UiField
    ControlGroup lienParenteWrapper;

    private final Driver driver;
    private final ValueList valueList;

    @Inject
    public ParentEditor(final Binder uiBinder, final Driver driver,
                        final ValueList valueList,
                        final ValueListRenderer valueListRenderer) {
        this.driver = driver;
        this.valueList = valueList;

        civilite = new ValueListBox<Gender>(new EnumRenderer<Gender>());
        nationality = new ValueListBox<ValueListProxy>(valueListRenderer);

        initWidget(uiBinder.createAndBindUi(this));
        driver.initialize(this);
        setTuteur(false);

        nationality.setAcceptableValues(valueList.getValueListByCode(ValueTypeCode.NATIONALITY));

        $(nom).id("nom");
        $(prenom).id("prenom");
        $(civilite).id("civilite");
        $(lientParente).id("lientParente");
        $(birthDate).id("birthDate");
        $(birthLocation).id("birthLocation");
        $(nationality).id("nationality");
        $(fonction).id("fonction");
        $(institution).id("institution");
        $(email).id("email");
        $(telDom).id("telDom");
        $(telGsm).id("telGsm");
        $(telBureau).id("telBureau");
        $(address).id("address");
    }

    @Override
    public void edit(InfoParentProxy infoParent) {
        nom.setFocus(true);
        driver.edit(infoParent);

        civilite.setValue(Gender.MALE);
        civilite.setAcceptableValues(Arrays.asList(Gender.values()));

        if (infoParent.getNationality() == null) {
            nationality.setValue(getDefaultNationality());
        }
    }

    @Override
    public InfoParentProxy get() {
        InfoParentProxy infoParent = driver.flush();
        if (driver.hasErrors()) {
            return null;
        } else {
            return infoParent;
        }
    }

    public void setTuteur(Boolean tuteur) {
        civiliteWrapper.setVisible(tuteur);
        lienParenteWrapper.setVisible(tuteur);
    }

    private ValueListProxy getDefaultNationality() {
        for (ValueListProxy nationalityFromList : valueList.getValueListByCode(ValueTypeCode.NATIONALITY)) {
            if (GlobalParameters.DEFAULT_NATIONALITY.equals(nationalityFromList.getValue())) {
                return nationalityFromList;
            }
        }
        return null;
    }
}
