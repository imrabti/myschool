package com.gsr.myschool.front.client.web.application.inscription.widget;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.mvp.ValidatedViewImpl;
import com.gsr.myschool.common.client.mvp.ValidationErrorPopup;
import com.gsr.myschool.common.client.mvp.ViewImpl;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.client.ui.dossier.NiveauScolaireEditor;

public class NiveauScolaireView extends ValidatedViewImpl implements NiveauScolairePresenter.MyView {
    public interface Binder extends UiBinder<Widget, NiveauScolaireView> {
    }

    @UiField(provided = true)
    NiveauScolaireEditor niveauScolaireEditor;

    @Inject
    public NiveauScolaireView(final Binder uiBinder, final ValidationErrorPopup errorPopup,
                              final NiveauScolaireEditor niveauScolaireEditor) {
        super(errorPopup);

        this.niveauScolaireEditor = niveauScolaireEditor;

        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void editDossier(DossierProxy dossier) {
        niveauScolaireEditor.edit(dossier);
    }

    @Override
    public void flushDossier() {
        niveauScolaireEditor.get();
    }
}
