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
import com.gsr.myschool.common.client.proxy.PieceJustifProxy;
import com.gsr.myschool.common.client.resource.SharedResources;
import com.gsr.myschool.common.client.util.CallbackImpl;
import com.gsr.myschool.common.client.util.EditorView;

import java.util.List;

import static com.google.gwt.query.client.GQuery.$;

public class PieceJustificatifEditor extends Composite implements EditorView<PieceJustifProxy> {
    interface Binder extends UiBinder<Widget, PieceJustificatifEditor> {
    }

    public interface Driver extends SimpleBeanEditorDriver<PieceJustifProxy, PieceJustificatifEditor> {
    }

    @UiField
    SuggestBox nom;

    private final Driver driver;
    private final SuggestionListFactory suggestionList;

    @Inject
    public PieceJustificatifEditor(final Binder uiBinder, final Driver driver,
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
        $(nom).attr("placeholder", "Editer une pièce justificative...");
    }

    @Override
    public void edit(PieceJustifProxy object) {
        driver.edit(object);
    }

    public void newPiece (PieceJustifProxy object, String nom) {
        object.setNom(nom);
        driver.edit(object);
    }

    @Override
    public PieceJustifProxy get() {
        PieceJustifProxy piece = driver.flush();
        if (driver.hasErrors()) {
            return null;
        } else {
            nom.setText("");
            return piece;
        }
    }

    public String getNewPieceNom() {
        return nom.getText();
    }

    public void initSuggestions() {
        nom.setText("");
        ((MultiWordSuggestOracle) nom.getSuggestOracle()).clear();

        suggestionList.getListPieces(new CallbackImpl<List<String>>() {
            @Override
            public void onSuccess(List<String> result) {
                ((MultiWordSuggestOracle) nom.getSuggestOracle()).addAll(result);
            }
        });
    }
}
