package com.gsr.myschool.common.client.ui.dossier;

import com.github.gwtbootstrap.client.ui.ValueListBox;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.client.proxy.FiliereProxy;
import com.gsr.myschool.common.client.proxy.NiveauEtudeProxy;
import com.gsr.myschool.common.client.security.SecurityUtils;
import com.gsr.myschool.common.client.ui.dossier.renderer.FiliereRenderer;
import com.gsr.myschool.common.client.ui.dossier.renderer.NiveauEtudeRenderer;
import com.gsr.myschool.common.client.util.EditorView;
import com.gsr.myschool.common.client.util.ValueList;
import com.gsr.myschool.common.shared.constants.GlobalParameters;
import com.gsr.myschool.common.shared.type.TypeEnseignement;

import java.util.ArrayList;
import java.util.List;

import static com.google.gwt.query.client.GQuery.$;

public class NiveauScolaireEditor extends Composite implements EditorView<DossierProxy> {
    public interface Binder extends UiBinder<Widget, NiveauScolaireEditor> {
    }

    public interface Driver extends SimpleBeanEditorDriver<DossierProxy, NiveauScolaireEditor> {
    }

    @UiField(provided = true)
    ValueListBox<FiliereProxy> filiere;
    @UiField(provided = true)
    ValueListBox<NiveauEtudeProxy> niveauEtude;

    @UiField(provided = true)
    ValueListBox<FiliereProxy> filiere2;
    @UiField(provided = true)
    ValueListBox<NiveauEtudeProxy> niveauEtude2;

    private final ValueList valueList;
    private final Driver driver;

    private FiliereProxy sectionFr;
    private FiliereProxy sectionBilingue;
    private List<FiliereProxy> filieres;

    @Inject
    public NiveauScolaireEditor(final Binder uiBinder, final ValueList valueList,
                                final Driver driver, final SecurityUtils securityUtils) {
        this.valueList = valueList;
        this.driver = driver;

        filiere = new ValueListBox<FiliereProxy>(new FiliereRenderer());
        niveauEtude = new ValueListBox<NiveauEtudeProxy>(new NiveauEtudeRenderer());
        filiere2 = new ValueListBox<FiliereProxy>(new FiliereRenderer());
        niveauEtude2 = new ValueListBox<NiveauEtudeProxy>(new NiveauEtudeRenderer());

        initWidget(uiBinder.createAndBindUi(this));
        driver.initialize(this);

        filieres = valueList.getFiliereList(securityUtils.isSuperUser());
        initFilieres(filieres);

        filiere.setAcceptableValues(filieres);
        niveauEtude.setAcceptableValues(new ArrayList<NiveauEtudeProxy>());

        filiere2.setAcceptableValues(new ArrayList<FiliereProxy>());
        niveauEtude2.setAcceptableValues(new ArrayList<NiveauEtudeProxy>());

        $(filiere).id("filiere");
        $(niveauEtude).id("niveauEtude");
        $(filiere2).id("filiere2");
        $(niveauEtude2).id("niveauEtude2");
    }

    @Override
    public void edit(DossierProxy dossier) {
        driver.edit(dossier);
    }

    @Override
    public DossierProxy get() {
        DossierProxy dossier = driver.flush();
        if (driver.hasErrors()) {
            return null;
        } else {
            return dossier;
        }
    }

    @UiHandler("filiere")
    void onFiliereChanged(ValueChangeEvent<FiliereProxy> event) {
        if (event.getValue() != null) {
            niveauEtude.setValue(null);
            List<NiveauEtudeProxy> toShow = new ArrayList<NiveauEtudeProxy>();
            for (NiveauEtudeProxy niveauEtude : valueList.getNiveauEtudeList(event.getValue().getId())) {
                if (!GlobalParameters.NE_toute_petite_section_ids.contains(niveauEtude.getId())) {
                    toShow.add(niveauEtude);
                }
            }
            niveauEtude.setAcceptableValues(toShow);
        } else {
            niveauEtude.setValue(null);
            niveauEtude.setAcceptableValues(new ArrayList<NiveauEtudeProxy>());
        }

        initSecondChoce(event.getValue());
    }

    @UiHandler("filiere2")
    void onFiliere2Changed(ValueChangeEvent<FiliereProxy> event) {
        if (event.getValue() != null) {
            niveauEtude2.setValue(null);
            List<NiveauEtudeProxy> toShow = new ArrayList<NiveauEtudeProxy>();
            for (NiveauEtudeProxy niveauEtude : valueList.getNiveauEtudeList(event.getValue().getId())) {
                if (!GlobalParameters.NE_toute_petite_section_ids.contains(niveauEtude.getId())) {
                    toShow.add(niveauEtude);
                }
            }
            niveauEtude2.setAcceptableValues(toShow);
        } else {
            niveauEtude2.setValue(null);
            niveauEtude2.setAcceptableValues(new ArrayList<NiveauEtudeProxy>());
        }
    }

    private void initFilieres(List<FiliereProxy> list) {
        for (FiliereProxy filiere : list) {
            if (TypeEnseignement.BILINGUE.getId() == filiere.getId()) sectionBilingue = filiere;
            if (TypeEnseignement.MISSION.getId() == filiere.getId()) sectionFr = filiere;
        }
    }

    private void initSecondChoce(FiliereProxy filiere) {
        if (filiere == null) {
            filiere2.setValue(null);
            filiere2.setAcceptableValues(new ArrayList<FiliereProxy>());
            niveauEtude2.setValue(null);
            niveauEtude2.setAcceptableValues(new ArrayList<NiveauEtudeProxy>());
            return;
        }
        if (TypeEnseignement.BILINGUE.getId() == filiere.getId()) {
            filiere2.setValue(sectionFr);
            filiere2.setEnabled(false);
            niveauEtude2.setValue(null);
            List<NiveauEtudeProxy> toShow = new ArrayList<NiveauEtudeProxy>();
            for (NiveauEtudeProxy niveauEtude : valueList.getNiveauEtudeList(sectionFr.getId())) {
                if (!GlobalParameters.NE_toute_petite_section_ids.contains(niveauEtude.getId())) {
                    toShow.add(niveauEtude);
                }
            }
            niveauEtude2.setAcceptableValues(toShow);
        } else if (TypeEnseignement.MISSION.getId() == filiere.getId()) {
            filiere2.setValue(sectionBilingue);
            filiere2.setEnabled(false);
            niveauEtude2.setValue(null);
            List<NiveauEtudeProxy> toShow = new ArrayList<NiveauEtudeProxy>();
            for (NiveauEtudeProxy niveauEtude : valueList.getNiveauEtudeList(sectionBilingue.getId())) {
                if (!GlobalParameters.NE_toute_petite_section_ids.contains(niveauEtude.getId())) {
                    toShow.add(niveauEtude);
                }
            }
            niveauEtude2.setAcceptableValues(toShow);
        } else {
            ArrayList<FiliereProxy> filieres2 = new ArrayList<FiliereProxy>();
            ArrayList<FiliereProxy> toRemove = new ArrayList<FiliereProxy>();
            filieres2.addAll(filieres);
            for (FiliereProxy f : filieres2) {
                if (TypeEnseignement.BILINGUE.getId() == f.getId()) toRemove.add(f);
                if (TypeEnseignement.MISSION.getId() == f.getId()) toRemove.add(f);
                if (f.getNom().equals(filiere.getNom())) toRemove.add(f);
            }
            filieres2.removeAll(toRemove);
            filiere2.setEnabled(true);
            filiere2.setValue(null);
            filiere2.setAcceptableValues(filieres2);

            niveauEtude2.setValue(null);
            niveauEtude2.setAcceptableValues(new ArrayList<NiveauEtudeProxy>());
        }
    }
}
