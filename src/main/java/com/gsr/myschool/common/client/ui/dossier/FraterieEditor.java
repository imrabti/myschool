package com.gsr.myschool.common.client.ui.dossier;

import com.github.gwtbootstrap.client.ui.CheckBox;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.github.gwtbootstrap.client.ui.ValueListBox;
import com.github.gwtbootstrap.datepicker.client.ui.DateBoxAppended;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RenderablePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.proxy.FraterieDTOProxy;
import com.gsr.myschool.common.client.proxy.NiveauEtudeProxy;
import com.gsr.myschool.common.client.ui.dossier.renderer.NiveauEtudeRenderer;
import com.gsr.myschool.common.client.util.EditorView;
import com.gsr.myschool.common.client.util.ValueList;
import com.gsr.myschool.common.client.widget.renderer.EnumRenderer;
import com.gsr.myschool.common.shared.type.TypeEnseignement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.gwt.query.client.GQuery.$;

public class FraterieEditor extends Composite implements EditorView<FraterieDTOProxy> {
    public interface Binder extends UiBinder<Widget, FraterieEditor> {
    }

    public interface Driver extends SimpleBeanEditorDriver<FraterieDTOProxy, FraterieEditor> {
    }

    public interface Handler {
        void onSelectEtablissement();
    }

    @UiField
    TextBox nom;
    @UiField
    TextBox prenom;
    @UiField
    RenderablePanel scolariseFields;
    @UiField
    DateBoxAppended birthDate;
    @UiField
    TextBox birthLocation;
    @UiField
    CheckBox scolarise;
    @UiField(provided = true)
    ValueListBox<TypeEnseignement> filiere;
    @UiField(provided = true)
    ValueListBox<NiveauEtudeProxy> niveau;
    @UiField
    @Ignore
    TextBox etablissementLabel;

    private final Driver driver;
    private final ValueList valueList;

    private Handler handler;

    @Inject
    public FraterieEditor(final Binder uiBinder, final Driver driver, final ValueList valueList) {
        this.driver = driver;
        this.valueList = valueList;

        filiere = new ValueListBox<TypeEnseignement>(new EnumRenderer<TypeEnseignement>());
        niveau = new ValueListBox<NiveauEtudeProxy>(new NiveauEtudeRenderer());

        initWidget(uiBinder.createAndBindUi(this));
        driver.initialize(this);

        $(nom).id("nom");
        $(prenom).id("prenom");
        $(birthDate).id("birthDate");
        $(birthLocation).id("birthLocation");

        filiere.setValue(TypeEnseignement.BILINGUE);
        filiere.setAcceptableValues(Arrays.asList(TypeEnseignement.values()));
        scolariseFields.setVisible(false);
    }

    @Override
    public void edit(FraterieDTOProxy fraterie) {
        driver.edit(fraterie);
        scolariseFields.setVisible(false);

        filiere.setValue(TypeEnseignement.BILINGUE);
        filiere.setAcceptableValues(Arrays.asList(TypeEnseignement.values()));

        List<NiveauEtudeProxy> values = valueList.getNiveauEtudeList(TypeEnseignement.BILINGUE.getNomFiliere());
        niveau.setValue(values.get(0));
        niveau.setAcceptableValues(values);
    }

    @Override
    public FraterieDTOProxy get() {
        FraterieDTOProxy fraterie = driver.flush();
        if (driver.hasErrors()) {
            return null;
        } else {
            return fraterie;
        }
    }

    public void setEtablissementLabel(String etablissementLabel) {
        this.etablissementLabel.setText(etablissementLabel);
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @UiHandler("selectEtablissement")
    void onSelectEtablissementClicked(ClickEvent event) {
        handler.onSelectEtablissement();
    }

    @UiHandler("filiere")
    void onTypeEnseignementChanged(ValueChangeEvent<TypeEnseignement> event) {
        if (event.getValue() == null) {
            niveau.setValue(null);
            niveau.setAcceptableValues(new ArrayList<NiveauEtudeProxy>());
        } else {
            List<NiveauEtudeProxy> values = valueList.getNiveauEtudeList(event.getValue().getNomFiliere());
            niveau.setValue(values.get(0));
            niveau.setAcceptableValues(values);
        }
    }

    @UiHandler("scolarise")
    void onScolariseChanged(ValueChangeEvent<Boolean> event) {
        scolariseFields.setVisible(event.getValue());
    }
}
