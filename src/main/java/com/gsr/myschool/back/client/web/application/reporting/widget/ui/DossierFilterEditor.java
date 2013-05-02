package com.gsr.myschool.back.client.web.application.reporting.widget.ui;

import com.github.gwtbootstrap.client.ui.ValueListBox;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.proxy.DossierFilterDTOProxy;
import com.gsr.myschool.common.client.proxy.FiliereProxy;
import com.gsr.myschool.common.client.proxy.NiveauEtudeProxy;
import com.gsr.myschool.common.client.proxy.SessionExamenProxy;
import com.gsr.myschool.common.client.ui.dossier.renderer.BooleanListRenderer;
import com.gsr.myschool.common.client.ui.dossier.renderer.FiliereRenderer;
import com.gsr.myschool.common.client.ui.dossier.renderer.NiveauEtudeRenderer;
import com.gsr.myschool.common.client.ui.dossier.renderer.SessionExamenRenderer;
import com.gsr.myschool.common.client.util.EditorView;
import com.gsr.myschool.common.client.util.ValueList;

import java.util.ArrayList;
import java.util.List;

public class DossierFilterEditor extends Composite implements EditorView<DossierFilterDTOProxy> {
    public interface Binder extends UiBinder<Widget, DossierFilterEditor> {
    }

    public interface Driver extends SimpleBeanEditorDriver<DossierFilterDTOProxy, DossierFilterEditor> {
    }

    @UiField(provided = true)
    ValueListBox<NiveauEtudeProxy> niveauEtude;
    @UiField(provided = true)
    ValueListBox<FiliereProxy> filiere;
    @UiField(provided = true)
    ListBox session;
    @UiField(provided = true)
    ValueListBox<Boolean> parentGsr;
    @UiField(provided = true)
    ValueListBox<Boolean> gsrFraterie;

    private final Driver driver;
    private final ValueList valueList;

    @Inject
    public DossierFilterEditor(final Binder uiBinder, final Driver driver, final ValueList valueList) {
        this.driver = driver;
        this.valueList = valueList;

        this.filiere = new ValueListBox<FiliereProxy>(new FiliereRenderer());
        this.niveauEtude = new ValueListBox<NiveauEtudeProxy>(new NiveauEtudeRenderer());
        this.gsrFraterie = new ValueListBox<Boolean>(new BooleanListRenderer());
        this.parentGsr = new ValueListBox<Boolean>(new BooleanListRenderer());
        this.session = new ListBox(true);

        initWidget(uiBinder.createAndBindUi(this));
        driver.initialize(this);

        niveauEtude.setAcceptableValues(new ArrayList<NiveauEtudeProxy>());
        gsrFraterie.setAcceptableValues(BooleanListRenderer.acceptedValues());
        parentGsr.setAcceptableValues(BooleanListRenderer.acceptedValues());

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
        for (SessionExamenProxy item : valueList.getClosedSessionsList()) {
            session.addItem(item.getNom(), item.getId().toString());
        }

        driver.edit(object);
        filiere.setAcceptableValues(valueList.getFiliereList());
        niveauEtude.setAcceptableValues(new ArrayList<NiveauEtudeProxy>());
        List<SessionExamenProxy> sessions = new ArrayList<SessionExamenProxy>();
        sessions.addAll(valueList.getClosedSessionsList());
        sessions.add(null);
        session.setAcceptableValues(sessions);
        session.setValue(object.getSession());
    }

    @Override
    public DossierFilterDTOProxy get() {
        DossierFilterDTOProxy dossierFilter = driver.flush();
        if (driver.hasErrors()) {
            return null;
        } else {
            StringBuffer ids = new StringBuffer();
            for (int i = 0; i < session.getItemCount(); i++) {
                if (session.isItemSelected(i)) {
                    ids.append(session.getValue(i) + ";");
                }
            }

            dossierFilter.setSessionIds(ids.toString());
        }

        return dossierFilter;
    }
}
