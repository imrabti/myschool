package com.gsr.myschool.common.client.ui.dossier;

import com.github.gwtbootstrap.client.ui.TextBox;
import com.github.gwtbootstrap.client.ui.ValueListBox;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.proxy.FraterieProxy;
import com.gsr.myschool.common.client.util.EditorView;
import com.gsr.myschool.common.client.widget.renderer.EnumRenderer;
import com.gsr.myschool.common.shared.type.TypeFraterie;
import com.gsr.myschool.common.shared.type.TypeNiveauEtude;

import java.util.Arrays;

import static com.google.gwt.query.client.GQuery.$;

public class FraterieEditor extends Composite implements EditorView<FraterieProxy> {
    public interface Binder extends UiBinder<Widget, FraterieEditor> {
    }

    public interface Driver extends SimpleBeanEditorDriver<FraterieProxy, FraterieEditor> {
    }

    @UiField
    TextBox nom;
    @UiField
    TextBox prenom;
    @UiField
    TextBox numDossierGSR;
    @UiField
    TextBox classe;
    @UiField(provided = true)
    ValueListBox<TypeNiveauEtude> niveau;
    @UiField
    TextBox etablissement;
    @UiField(provided = true)
    ValueListBox<TypeFraterie> typeFraterie;

    private final Driver driver;

    @Inject
    public FraterieEditor(final Binder uiBinder, final Driver driver) {
        this.driver = driver;
        this.typeFraterie = new ValueListBox<TypeFraterie>(new EnumRenderer<TypeFraterie>());
        this.niveau = new ValueListBox<TypeNiveauEtude>(new EnumRenderer<TypeNiveauEtude>());

        initWidget(uiBinder.createAndBindUi(this));
        driver.initialize(this);

        $(nom).id("nom");
        $(prenom).id("prenom");
        $(numDossierGSR).id("numDossierGSR");
        $(classe).id("classe");
        $(niveau).id("niveau");
        $(etablissement).id("etablissement");
        $(typeFraterie).id("typeFraterie");

        niveau.setAcceptableValues(Arrays.asList(TypeNiveauEtude.values()));
        typeFraterie.setAcceptableValues(Arrays.asList(TypeFraterie.values()));
    }

    @Override
    public void edit(FraterieProxy fraterie) {
        driver.edit(fraterie);
    }

    @Override
    public FraterieProxy get() {
        FraterieProxy fraterie = driver.flush();
        if (driver.hasErrors()) {
            return null;
        } else {
            return fraterie;
        }
    }
}
