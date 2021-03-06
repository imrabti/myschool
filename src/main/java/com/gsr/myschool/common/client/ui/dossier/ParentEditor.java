package com.gsr.myschool.common.client.ui.dossier;

import com.github.gwtbootstrap.client.ui.*;
import com.github.gwtbootstrap.datepicker.client.ui.DateBox;
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
import com.gsr.myschool.common.client.resource.message.HelpMessageBundle;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;
import com.gsr.myschool.common.client.util.DateUtilsClient;
import com.gsr.myschool.common.client.util.EditorView;
import com.gsr.myschool.common.client.util.ValueList;
import com.gsr.myschool.common.client.widget.renderer.EnumRenderer;
import com.gsr.myschool.common.client.widget.renderer.ValueListRendererFactory;
import com.gsr.myschool.common.shared.constants.GlobalParameters;
import com.gsr.myschool.common.shared.type.Gender;
import com.gsr.myschool.common.shared.type.ParentType;
import com.gsr.myschool.common.shared.type.TypeEnseignement;
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
    DateBox birthDate;
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
    @UiField
    Tooltip infoParentAdresse;
    @UiField
    Tooltip infoParentTelTravail;
    @UiField
    Tooltip infoParentTelMobile;
    @UiField
    Tooltip infoParentTelMaison;
    @UiField
    Tooltip infoParentAdresseEmail;
    @UiField
    Tooltip infoParentInstitution;
    @UiField
    Tooltip infoParentFonction;
    @UiField
    Tooltip infoParentNationnalite;
    @UiField
    Tooltip infoParentLieuNaissance;
    @UiField
    Tooltip infoParentDateNaissance;
    @UiField
    Tooltip infoParentPrenom;
    @UiField
    Tooltip infoParentNom;

    private final Driver driver;
    private final ValueList valueList;
    private final HelpMessageBundle helpMessageBundle;

    @Inject
    public ParentEditor(final Binder uiBinder, final Driver driver,
                        final ValueList valueList,
                        final SharedMessageBundle messageBundle,
                        final ValueListRendererFactory valueListRendererFactory,
                        final HelpMessageBundle helpMessageBundle) {
        this.driver = driver;
        this.valueList = valueList;
        this.helpMessageBundle = helpMessageBundle;

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

        nationality.setAcceptableValues(valueList.getValueListByCode(ValueTypeCode.NATIONALITY, true));

        formationGsr.setValue("");
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
        $(promotionGsr).id("promotionGsr");

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
            if (infoParent.getBirthDate() != null) {
                infoParent.setBirthDate(DateUtilsClient.correctDate(infoParent.getBirthDate()));
            }

            return infoParent;
        }
    }

    public void setTuteur(Boolean tuteur) {
        civiliteWrapper.setVisible(tuteur);
        lienParenteWrapper.setVisible(tuteur);
    }

    public void setType(ParentType parentType) {
        String type = "";

        if (parentType == ParentType.PERE) type = "du père";
        if (parentType == ParentType.MERE) type = "de la mère";
        if (parentType == ParentType.TUTEUR) type = "du tuteur légal";

        infoParentNom.setText(helpMessageBundle.infoParentNom(type));
        infoParentPrenom.setText(helpMessageBundle.infoParentPrenom(type));
        infoParentAdresse.setText(helpMessageBundle.infoParentAdresse(type));
        infoParentTelTravail.setText(helpMessageBundle.infoParentTelTravail(type));
        infoParentTelMobile.setText(helpMessageBundle.infoParentTelMobile(type));
        infoParentTelMaison.setText(helpMessageBundle.infoParentTelMaison(type));
        infoParentAdresseEmail.setText(helpMessageBundle.infoParentAdresseEmail(type));
        infoParentInstitution.setText(helpMessageBundle.infoParentInstitution(type));
        infoParentFonction.setText(helpMessageBundle.infoParentFonction(type));
        infoParentNationnalite.setText(helpMessageBundle.infoParentNationnalite(type));
        infoParentLieuNaissance.setText(helpMessageBundle.infoParentLieuNaissance(type));
        infoParentDateNaissance.setText(helpMessageBundle.infoParentDateNaissance(type));
    }

    private ValueListProxy getDefaultNationality() {
        for (ValueListProxy nationalityFromList : valueList.getValueListByCode(ValueTypeCode.NATIONALITY, true)) {
            if (GlobalParameters.DEFAULT_NATIONALITY.equals(nationalityFromList.getValue())) {
                return nationalityFromList;
            }
        }
        return null;
    }

    private List<String> initListFormations() {
        ArrayList<String> formations = new ArrayList<String>();
        formations.add("");
        formations.add("Bilingue enrichie");
        formations.add("Française");

        return formations;
    }
}
