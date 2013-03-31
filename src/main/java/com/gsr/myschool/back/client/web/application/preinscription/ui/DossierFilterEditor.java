package com.gsr.myschool.back.client.web.application.preinscription.ui;

import com.github.gwtbootstrap.client.ui.CheckBox;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.github.gwtbootstrap.client.ui.ValueListBox;
import com.github.gwtbootstrap.datepicker.client.ui.DateBox;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.back.client.util.SuggestionListFactory;
import com.gsr.myschool.common.client.proxy.DossierFilterDTOProxy;
import com.gsr.myschool.common.client.proxy.FiliereProxy;
import com.gsr.myschool.common.client.proxy.NiveauEtudeProxy;
import com.gsr.myschool.common.client.ui.dossier.renderer.FiliereRenderer;
import com.gsr.myschool.common.client.ui.dossier.renderer.NiveauEtudeRenderer;
import com.gsr.myschool.common.client.util.DateUtilsClient;
import com.gsr.myschool.common.client.util.EditorView;
import com.gsr.myschool.common.client.util.ValueList;
import com.gsr.myschool.common.client.widget.renderer.EnumRenderer;
import com.gsr.myschool.common.shared.type.DossierStatus;

import java.util.ArrayList;
import java.util.Arrays;

public class DossierFilterEditor extends Composite implements EditorView<DossierFilterDTOProxy> {
    public interface Binder extends UiBinder<Widget, DossierFilterEditor> {
    }

    public interface Driver extends SimpleBeanEditorDriver<DossierFilterDTOProxy, DossierFilterEditor> {
    }

    @UiField(provided = true)
    ValueListBox<DossierStatus> status;
    @UiField(provided = true)
    ValueListBox<NiveauEtudeProxy> niveauEtude;
    @UiField(provided = true)
    ValueListBox<FiliereProxy> filiere;
    @UiField
    TextBox firstnameOrlastname;
    @UiField
    DateBox dateFrom;
    @UiField
    DateBox dateTill;
    @UiField
    CheckBox parentGsr;
    @UiField
    CheckBox gsrFraterie;

    private final Driver driver;
    private final ValueList valueList;

    @Inject
    public DossierFilterEditor(final Binder uiBinder, final Driver driver, final ValueList valueList) {
        this.driver = driver;
        this.valueList = valueList;

        this.status = new ValueListBox<DossierStatus>(new EnumRenderer<DossierStatus>());
        this.filiere = new ValueListBox<FiliereProxy>(new FiliereRenderer());
        this.niveauEtude = new ValueListBox<NiveauEtudeProxy>(new NiveauEtudeRenderer());

        initWidget(uiBinder.createAndBindUi(this));
        driver.initialize(this);

        status.setAcceptableValues(Arrays.asList(DossierStatus.values()));
        niveauEtude.setAcceptableValues(new ArrayList<NiveauEtudeProxy>());

        filiere.addValueChangeHandler(new ValueChangeHandler<FiliereProxy>() {
            @Override
            public void onValueChange(ValueChangeEvent<FiliereProxy> event) {
                if(event.getValue() != null) {
                    niveauEtude.setValue(null);
                    niveauEtude.setAcceptableValues(valueList.getNiveauEtudeList(event.getValue().getId()));
                } else {
                    niveauEtude.setValue(null);
                    niveauEtude.setAcceptableValues(new ArrayList<NiveauEtudeProxy>());
                }
            }
        });
    }

    @Override
    public void edit(DossierFilterDTOProxy object) {
        driver.edit(object);
        filiere.setAcceptableValues(valueList.getFiliereList());
        niveauEtude.setAcceptableValues(new ArrayList<NiveauEtudeProxy>());
    }

    @Override
    public DossierFilterDTOProxy get() {
        DossierFilterDTOProxy dossierFilter = driver.flush();
        if (driver.hasErrors()) {
            return null;
        } else {
            dossierFilter.setDateFrom(DateUtilsClient.correctDate(dossierFilter.getDateFrom()));
            dossierFilter.setDateTill(DateUtilsClient.correctDate(dossierFilter.getDateTill()));

            return dossierFilter;
        }
    }
}
