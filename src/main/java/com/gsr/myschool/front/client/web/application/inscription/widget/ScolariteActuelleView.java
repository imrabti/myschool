package com.gsr.myschool.front.client.web.application.inscription.widget;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.mvp.ValidatedViewWithUiHandlers;
import com.gsr.myschool.common.client.mvp.ValidationErrorPopup;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gsr.myschool.common.client.proxy.ScolariteActuelleDTOProxy;
import com.gsr.myschool.common.client.ui.dossier.ScolariteActuelleEditor;

public class ScolariteActuelleView extends ValidatedViewWithUiHandlers<ScolariteActuelleUiHandlers>
        implements ScolariteActuellePresenter.MyView {
    public interface Binder extends UiBinder<Widget, ScolariteActuelleView> {
    }

    @UiField(provided = true)
    ScolariteActuelleEditor scolariteActuelleEditor;

    @Inject
    public ScolariteActuelleView(final Binder uiBinder, final ValidationErrorPopup errorPopup,
                                 final UiHandlersStrategy<ScolariteActuelleUiHandlers> uiHandlersStrategy,
                                 final ScolariteActuelleEditor scolariteActuelleEditor) {
        super(uiHandlersStrategy, errorPopup);

        this.scolariteActuelleEditor = scolariteActuelleEditor;

        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void editScolariteAnterieur(ScolariteActuelleDTOProxy scolariteAnterieur) {
        scolariteActuelleEditor.edit(scolariteAnterieur);
    }

    @Override
    public void flushScolariteActuelle() {
        scolariteActuelleEditor.get();
    }
}
