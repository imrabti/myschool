package com.gsr.myschool.common.client.ui.dossier;

import com.github.gwtbootstrap.client.ui.*;
import com.github.gwtbootstrap.datepicker.client.ui.DateBoxAppended;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.text.shared.AbstractRenderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RenderablePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.proxy.InfoParentProxy;
import com.gsr.myschool.common.client.proxy.ValueListProxy;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;
import com.gsr.myschool.common.client.util.EditorView;
import com.gsr.myschool.common.client.util.ValueList;
import com.gsr.myschool.common.client.widget.renderer.EnumRenderer;
import com.gsr.myschool.common.client.widget.renderer.ValueListRendererFactory;
import com.gsr.myschool.common.shared.constants.GlobalParameters;
import com.gsr.myschool.common.shared.type.Gender;
import com.gsr.myschool.common.shared.type.ValueTypeCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    @UiField
    RenderablePanel parentGsrPanel;
    @UiField
    CheckBox parentGsr;
    @UiField
    TextBox promotionGsr;
    @UiField(provided = true)
    ValueListBox<String> formationGsr;

    private final Driver driver;
    private final ValueList valueList;

    @Inject
    public ParentEditor(final Binder uiBinder, final Driver driver,
                        final ValueList valueList,
                        final SharedMessageBundle messageBundle,
                        final ValueListRendererFactory valueListRendererFactory) {
        this.driver = driver;
        this.valueList = valueList;

        civilite = new ValueListBox<Gender>(new EnumRenderer<Gender>());
        nationality = new ValueListBox<ValueListProxy>(valueListRendererFactory.create(messageBundle.emptyValueList()));
        formationGsr = new ValueListBox<String>(new AbstractRenderer<String>() {
            @Override
            public String render(String object) {
                return object;
            }
        });

        initWidget(uiBinder.createAndBindUi(this));
        driver.initialize(this);
        setTuteur(false);

        nationality.setAcceptableValues(valueList.getValueListByCode(ValueTypeCode.NATIONALITY));

        formationGsr.setValue("Française");
        formationGsr.setAcceptableValues(initListFormations());

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

        parentGsr.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
            @Override
            public void onValueChange(ValueChangeEvent<Boolean> event) {
                parentGsrPanel.setVisible(event.getValue());
            }
        });
    }

    @Override
    public void edit(InfoParentProxy infoParent) {
        nom.setFocus(true);
        driver.edit(infoParent);

        civilite.setValue(Gender.MALE);
        civilite.setAcceptableValues(Arrays.asList(Gender.values()));

        formationGsr.setValue("Française");
        formationGsr.setAcceptableValues(initListFormations());

        parentGsrPanel.setVisible(parentGsr.getValue());

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

    private List<String> initListFormations() {
        ArrayList<String> formations = new ArrayList<String>();
        formations.add("Bilingue enrichie");
        formations.add("Française");

        return formations;
    }
}
