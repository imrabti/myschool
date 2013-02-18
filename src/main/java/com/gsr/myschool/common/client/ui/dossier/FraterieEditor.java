package com.gsr.myschool.common.client.ui.dossier;

import com.github.gwtbootstrap.client.ui.CheckBox;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.github.gwtbootstrap.client.ui.ValueBox;
import com.github.gwtbootstrap.client.ui.ValueListBox;
import com.github.gwtbootstrap.datepicker.client.ui.DateBoxAppended;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RenderablePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.proxy.EtablissementScolaireProxy;
import com.gsr.myschool.common.client.proxy.FraterieDTOProxy;
import com.gsr.myschool.common.client.proxy.NiveauEtudeProxy;
import com.gsr.myschool.common.client.ui.dossier.renderer.EtablissementRenderer;
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
    TextBox numDossierGSR;
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
    TextBox etablissement;

    private final Driver driver;
    private Handler handler;

    @Inject
    public FraterieEditor(final Binder uiBinder, final Driver driver, final ValueList valueList) {
        this.driver = driver;
        this.filiere = new ValueListBox<TypeEnseignement>(new EnumRenderer<TypeEnseignement>());
        this.niveau = new ValueListBox<NiveauEtudeProxy>(new NiveauEtudeRenderer());

        initWidget(uiBinder.createAndBindUi(this));
        driver.initialize(this);

        $(nom).id("nom");
        $(prenom).id("prenom");
        $(numDossierGSR).id("numDossierGSR");

        filiere.setAcceptableValues(Arrays.asList(TypeEnseignement.values()));
        filiere.addValueChangeHandler(new ValueChangeHandler<TypeEnseignement>() {
            @Override
            public void onValueChange(ValueChangeEvent<TypeEnseignement> event) {
                if (event.getValue() == null) {
                    niveau = new ValueListBox<NiveauEtudeProxy>(new NiveauEtudeRenderer());
                    niveau.setAcceptableValues(new ArrayList<NiveauEtudeProxy>());
                } else {
                    List<NiveauEtudeProxy> values = valueList.getNiveauEtudeList(event.getValue().getNomFiliere());
                    niveau.setValue(values.get(0));
                    niveau.setAcceptableValues(values);
                }
            }
        });

        scolariseFields.setVisible(false);
        scolarise.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
            @Override
            public void onValueChange(ValueChangeEvent<Boolean> event) {
                scolariseFields.setVisible(event.getValue());
            }
        });
    }

    @Override
    public void edit(FraterieDTOProxy fraterie) {
        driver.edit(fraterie);
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

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @UiHandler("selectEtablissement")
    void onSelectEtablissementClicked(ClickEvent event) {
        handler.onSelectEtablissement();
    }
}
