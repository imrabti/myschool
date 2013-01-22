package com.gsr.myschool.front.client.web.application.inscription.widget;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.mvp.ViewImpl;
import com.gsr.myschool.front.client.web.application.inscription.ui.NiveauScolaireEditor;

public class NiveauScolaireView extends ViewImpl implements NiveauScolairePresenter.MyView {
    public interface Binder extends UiBinder<Widget, NiveauScolaireView> {
    }

    @UiField(provided = true)
    NiveauScolaireEditor niveauScolaireEditor;

    @Inject
    public NiveauScolaireView(final Binder uiBinder, final NiveauScolaireEditor niveauScolaireEditor) {
        this.niveauScolaireEditor = niveauScolaireEditor;

        initWidget(uiBinder.createAndBindUi(this));
    }
}
