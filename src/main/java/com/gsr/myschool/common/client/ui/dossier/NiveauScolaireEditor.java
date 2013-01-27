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
import com.gsr.myschool.common.client.util.EditorView;
import com.gsr.myschool.front.client.util.ValueList;
import com.gsr.myschool.front.client.web.application.inscription.renderer.FiliereRenderer;
import com.gsr.myschool.front.client.web.application.inscription.renderer.NiveauEtudeRenderer;

import java.util.ArrayList;

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

    private final ValueList valueList;
    private final Driver driver;

    @Inject
    public NiveauScolaireEditor(final Binder uiBinder, final ValueList valueList,
                                final Driver driver) {
        this.valueList = valueList;
        this.driver = driver;
        this.filiere = new ValueListBox<FiliereProxy>(new FiliereRenderer());
        this.niveauEtude = new ValueListBox<NiveauEtudeProxy>(new NiveauEtudeRenderer());

        initWidget(uiBinder.createAndBindUi(this));
        driver.initialize(this);

        filiere.setAcceptableValues(valueList.getFiliereList());
        niveauEtude.setAcceptableValues(new ArrayList<NiveauEtudeProxy>());

        $(filiere).id("filiere");
        $(niveauEtude).id("niveauEtude");
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
        niveauEtude.setAcceptableValues(valueList.getNiveauEtudeList(filiere.getValue().getNom()));
    }
}
