package com.gsr.myschool.common.client.ui.dossier;

import com.github.gwtbootstrap.client.ui.TextBox;
import com.github.gwtbootstrap.client.ui.ValueListBox;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.proxy.EtablissementScolaireProxy;
import com.gsr.myschool.common.client.proxy.ScolariteActuelleDTOProxy;
import com.gsr.myschool.common.client.proxy.ValueListProxy;
import com.gsr.myschool.common.client.ui.dossier.renderer.EtablissementRenderer;
import com.gsr.myschool.common.client.util.EditorView;
import com.gsr.myschool.common.client.util.ValueList;
import com.gsr.myschool.common.client.widget.renderer.EnumRenderer;
import com.gsr.myschool.common.client.widget.renderer.ValueListRenderer;
import com.gsr.myschool.common.shared.type.TypeNiveauEtude;
import com.gsr.myschool.common.shared.type.ValueTypeCode;

import java.util.Arrays;

import static com.google.gwt.query.client.GQuery.$;

public class ScolariteActuelleEditor extends Composite implements EditorView<ScolariteActuelleDTOProxy> {
    public interface Binder extends UiBinder<HTMLPanel, ScolariteActuelleEditor> {
    }

    public interface Driver extends SimpleBeanEditorDriver<ScolariteActuelleDTOProxy, ScolariteActuelleEditor> {
    }

    private static final String AUTRES = "- Autres -";

    @UiField(provided = true)
    ValueListBox<EtablissementScolaireProxy> etablissement;
    @UiField
    TextBox newEtablissementScolaire;
    @UiField(provided = true)
    ValueListBox<TypeNiveauEtude> typeNiveauEtude;
    @UiField
    TextBox classe;
    @UiField(provided = true)
    ValueListBox<ValueListProxy> anneeScolaire;

    private final Driver driver;

    @Inject
    public ScolariteActuelleEditor(final Binder uiBinder, final Driver driver,
                                   final ValueList valueList,
                                   final ValueListRenderer valueListRenderer) {
        this.driver = driver;

        etablissement = new ValueListBox<EtablissementScolaireProxy>(new EtablissementRenderer());
        typeNiveauEtude = new ValueListBox<TypeNiveauEtude>(new EnumRenderer<TypeNiveauEtude>());
        anneeScolaire = new ValueListBox<ValueListProxy>(valueListRenderer);

        initWidget(uiBinder.createAndBindUi(this));
        driver.initialize(this);

        etablissement.setAcceptableValues(valueList.getEtablissementScolaireList());
        typeNiveauEtude.setAcceptableValues(Arrays.asList(TypeNiveauEtude.values()));
        anneeScolaire.setAcceptableValues(valueList.getValueListByCode(ValueTypeCode.SCHOOL_YEAR));

        $(etablissement).id("etablissement");
        $(newEtablissementScolaire).id("newEtablissementScolaire");
        $(typeNiveauEtude).id("typeNiveauEtude");
        $(classe).id("classe");
        $(anneeScolaire).id("anneeScolaire");

        newEtablissementScolaire.setEnabled(false);
    }

    @Override
    public void edit(ScolariteActuelleDTOProxy scolariteAnterieur) {
        driver.edit(scolariteAnterieur);
    }

    @Override
    public ScolariteActuelleDTOProxy get() {
        ScolariteActuelleDTOProxy scolariteAnterieur = driver.flush();
        if (driver.hasErrors()) {
            return null;
        } else {
            return scolariteAnterieur;
        }
    }

    @UiHandler("etablissement")
    void onEtablissementChanged(ValueChangeEvent<EtablissementScolaireProxy> event) {
        if (etablissement.getValue().getNom().equals(AUTRES)) {
            newEtablissementScolaire.setEnabled(true);
        } else {
            newEtablissementScolaire.setEnabled(false);
            newEtablissementScolaire.setText("");
        }
    }
}
