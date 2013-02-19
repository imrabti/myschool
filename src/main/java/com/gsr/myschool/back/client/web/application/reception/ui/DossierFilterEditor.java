package com.gsr.myschool.back.client.web.application.reception.ui;

import com.github.gwtbootstrap.client.ui.TextBox;
import com.github.gwtbootstrap.client.ui.ValueListBox;
import com.github.gwtbootstrap.datepicker.client.ui.DateBoxAppended;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.proxy.DossierFilterDTOProxy;
import com.gsr.myschool.common.client.proxy.FiliereProxy;
import com.gsr.myschool.common.client.proxy.NiveauEtudeProxy;
import com.gsr.myschool.common.client.ui.dossier.renderer.FiliereRenderer;
import com.gsr.myschool.common.client.ui.dossier.renderer.NiveauEtudeRenderer;
import com.gsr.myschool.common.client.util.EditorView;
import com.gsr.myschool.common.client.util.ValueList;
import com.gsr.myschool.common.client.widget.renderer.EnumRenderer;
import com.gsr.myschool.common.shared.type.DossierStatus;

import java.util.Arrays;

public class DossierFilterEditor extends Composite implements EditorView<DossierFilterDTOProxy> {
    public interface Binder extends UiBinder<Widget, DossierFilterEditor> {
    }

    public interface Driver extends SimpleBeanEditorDriver<DossierFilterDTOProxy, DossierFilterEditor> {
    }

    @UiField
    TextBox numDossier;
    @UiField
    TextBox firstnameOrlastname;
    @UiField(provided = true)
    ValueListBox<NiveauEtudeProxy> niveauEtude;
    @UiField(provided = true)
    ValueListBox<FiliereProxy> filiere;

    private final Driver driver;

    @Inject
    public DossierFilterEditor(final Binder uiBinder, final Driver driver, final ValueList valueList) {
        this.driver = driver;

        this.filiere = new ValueListBox<FiliereProxy>(new FiliereRenderer());
        this.niveauEtude = new ValueListBox<NiveauEtudeProxy>(new NiveauEtudeRenderer());

        initWidget(uiBinder.createAndBindUi(this));
        driver.initialize(this);

        filiere.setAcceptableValues(valueList.getFiliereList());
        filiere.addValueChangeHandler(new ValueChangeHandler<FiliereProxy>() {
            @Override
            public void onValueChange(ValueChangeEvent<FiliereProxy> event) {
                niveauEtude = new ValueListBox<NiveauEtudeProxy>(new NiveauEtudeRenderer());
                if(event.getValue() != null) {
                    niveauEtude.setAcceptableValues(valueList.getNiveauEtudeList(event.getValue().getNom()));
                }
            }
        });
    }

    @Override
    public void edit(DossierFilterDTOProxy object) {
        numDossier.setFocus(true);
        driver.edit(object);
    }

    @Override
    public DossierFilterDTOProxy get() {
        DossierFilterDTOProxy dossierFilter = driver.flush();
        if (driver.hasErrors()) {
            return null;
        } else {
            dossierFilter.setNiveauEtude(niveauEtude.getValue());
            dossierFilter.setFiliere(filiere.getValue());
            return dossierFilter;
        }
    }
}
