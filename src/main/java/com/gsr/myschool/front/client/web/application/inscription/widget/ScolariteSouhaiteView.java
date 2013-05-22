package com.gsr.myschool.front.client.web.application.inscription.widget;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.mvp.ValidatedViewImpl;
import com.gsr.myschool.common.client.widget.ValidationErrorPopup;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.front.client.web.application.inscription.ui.NiveauScolaireEditor;

public class ScolariteSouhaiteView extends ValidatedViewImpl implements SolariteSouhaitePresenter.MyView {
    public interface Binder extends UiBinder<Widget, ScolariteSouhaiteView> {
    }

    @UiField(provided = true)
    NiveauScolaireEditor niveauScolaireEditor;

    @Inject
    public ScolariteSouhaiteView(final Binder uiBinder, final ValidationErrorPopup errorPopup,
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
