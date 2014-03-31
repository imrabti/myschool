package com.gsr.myschool.back.client.web.application.confirmationTest.ui;

import com.github.gwtbootstrap.client.ui.CheckBox;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.github.gwtbootstrap.client.ui.ValueListBox;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestBox.DefaultSuggestionDisplay;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.back.client.util.SuggestionListFactory;
import com.gsr.myschool.common.client.proxy.DossierFilterDTOProxy;
import com.gsr.myschool.common.client.proxy.FiliereProxy;
import com.gsr.myschool.common.client.proxy.NiveauEtudeProxy;
import com.gsr.myschool.common.client.proxy.ValueListProxy;
import com.gsr.myschool.common.client.resource.SharedResources;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;
import com.gsr.myschool.common.client.ui.dossier.renderer.FiliereRenderer;
import com.gsr.myschool.common.client.ui.dossier.renderer.NiveauEtudeRenderer;
import com.gsr.myschool.common.client.util.CallbackImpl;
import com.gsr.myschool.common.client.util.EditorView;
import com.gsr.myschool.common.client.util.ValueList;
import com.gsr.myschool.common.client.widget.renderer.EnumRenderer;
import com.gsr.myschool.common.client.widget.renderer.ValueListRendererFactory;
import com.gsr.myschool.common.shared.type.DossierStatus;
import com.gsr.myschool.common.shared.type.ValueTypeCode;

import java.util.ArrayList;
import java.util.List;

public class DossierFilterEditor extends Composite implements EditorView<DossierFilterDTOProxy> {
    public interface Binder extends UiBinder<Widget, DossierFilterEditor> {
    }

    public interface Driver extends SimpleBeanEditorDriver<DossierFilterDTOProxy, DossierFilterEditor> {
    }

    @UiField
    SuggestBox numDossier;
    @UiField
    TextBox firstnameOrlastname;
    @UiField(provided = true)
    ValueListBox<NiveauEtudeProxy> niveauEtude;
    @UiField(provided = true)
    ValueListBox<FiliereProxy> filiere;
	@UiField(provided = true)
	ValueListBox<DossierStatus> status;
    @UiField
    CheckBox parentGsr;
    @UiField
    CheckBox gsrFraterie;
    @UiField(provided = true)
    ValueListBox<ValueListProxy> anneeScolaire;

    private final Driver driver;
    private final ValueList valueList;
    private final SuggestionListFactory suggestionList;

    @Inject
    public DossierFilterEditor(final Binder uiBinder, final Driver driver,
			final ValueList valueList, final SharedResources resources,
			final SuggestionListFactory suggestionList,
			final SharedMessageBundle messageBundle,
			final ValueListRendererFactory valueListRendererFactory) {
        this.driver = driver;
        this.valueList = valueList;
        this.suggestionList = suggestionList;

        this.filiere = new ValueListBox<FiliereProxy>(new FiliereRenderer());
        this.niveauEtude = new ValueListBox<NiveauEtudeProxy>(new NiveauEtudeRenderer());
        this.status = new ValueListBox<DossierStatus>(new EnumRenderer<DossierStatus>());
        this.anneeScolaire = new ValueListBox<ValueListProxy>(valueListRendererFactory.create("Année Scolaire"));

        initWidget(uiBinder.createAndBindUi(this));
        driver.initialize(this);

        // Set up CSS Style Classes
        DefaultSuggestionDisplay payeeSuggestionDisplay = (DefaultSuggestionDisplay) numDossier.getSuggestionDisplay();
        payeeSuggestionDisplay.setPopupStyleName(resources.suggestBoxStyleCss().gwtSuggestBoxPoup());

        filiere.setAcceptableValues(valueList.getFiliereList());
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
        initSuggestions();

        numDossier.setFocus(true);
        driver.edit(object);
        filiere.setAcceptableValues(valueList.getFiliereList());
        status.setValue(object.getStatus());
        if (filiere.getValue() != null) {
            niveauEtude.setAcceptableValues(valueList.getNiveauEtudeList(filiere.getValue().getId()));
        } else {
            niveauEtude.setAcceptableValues(new ArrayList<NiveauEtudeProxy>());
        }
        status.setAcceptableValues(DossierStatus.confirmationTestValues());
        setAnneeScolaireValues(valueList);
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

    private void initSuggestions() {
        ((MultiWordSuggestOracle) numDossier.getSuggestOracle()).clear();

        suggestionList.getListNumDossier(new CallbackImpl<List<String>>() {
            @Override
            public void onSuccess(List<String> result) {
                ((MultiWordSuggestOracle) numDossier.getSuggestOracle()).addAll(result);
            }
        });
    }

    private void setAnneeScolaireValues(ValueList valueList) {
        List<ValueListProxy> anneeScolaireList = valueList.getValueListByCode(ValueTypeCode.SCHOOL_YEAR, false);
        if (anneeScolaire.getValue() == null) {
            anneeScolaire.setValue(anneeScolaireList.isEmpty() ? null : anneeScolaireList.get(0));
        }
        anneeScolaire.setAcceptableValues(anneeScolaireList);
    }
}
