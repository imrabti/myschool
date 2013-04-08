package com.gsr.myschool.back.client.web.application.settings.popup.ui;

import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox.DefaultSuggestionDisplay;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.back.client.util.SuggestionListFactory;
import com.gsr.myschool.common.client.proxy.MatiereExamenProxy;
import com.gsr.myschool.common.client.resource.SharedResources;
import com.gsr.myschool.common.client.util.CallbackImpl;
import com.gsr.myschool.common.client.util.EditorView;

import java.util.List;

import static com.google.gwt.query.client.GQuery.$;

public class MatiereExamenEditor extends Composite implements EditorView<MatiereExamenProxy> {
    interface Binder extends UiBinder<Widget, MatiereExamenEditor> {
    }

    public interface Driver extends SimpleBeanEditorDriver<MatiereExamenProxy, MatiereExamenEditor> {
    }

    @UiField
    SuggestBox nom;

    private final Driver driver;
    private final SuggestionListFactory suggestionList;

    @Inject
    public MatiereExamenEditor(final Binder uiBinder, final Driver driver,
                               final SharedResources resources,
                               final SuggestionListFactory suggestionList) {

        this.driver = driver;
        this.suggestionList = suggestionList;

        initWidget(uiBinder.createAndBindUi(this));
        driver.initialize(this);

        // Set up CSS Style Classes
        DefaultSuggestionDisplay pieceSuggestionDisplay = (DefaultSuggestionDisplay) nom.getSuggestionDisplay();
        pieceSuggestionDisplay.setPopupStyleName(resources.suggestBoxStyleCss().gwtSuggestBoxPoup());

        $(nom).id("nom");
        $(nom).attr("placeholder", "Editer une matière d'examen...");
    }

    @Override
    public void edit(MatiereExamenProxy object) {
        driver.edit(object);
    }

    public void newMatiere (MatiereExamenProxy object, String nom) {
        object.setNom(nom);
        driver.edit(object);
    }

    @Override
    public MatiereExamenProxy get() {
        MatiereExamenProxy matiere = driver.flush();
        if (driver.hasErrors()) {
            return null;
        } else {
            nom.setText("");
            return matiere;
        }
    }

    public String getNewMatiereNom() {
        return nom.getText();
    }

    public void initSuggestions() {
        nom.setText("");
        ((MultiWordSuggestOracle) nom.getSuggestOracle()).clear();

        suggestionList.getListMatieres(new CallbackImpl<List<String>>() {
            @Override
            public void onSuccess(List<String> result) {
                ((MultiWordSuggestOracle) nom.getSuggestOracle()).addAll(result);
            }
        });
    }
}
