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
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.common.client.proxy.*;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.ui.dossier.renderer.BooleanListRenderer;
import com.gsr.myschool.common.client.ui.dossier.renderer.FiliereRenderer;
import com.gsr.myschool.common.client.ui.dossier.renderer.NiveauEtudeRenderer;
import com.gsr.myschool.common.client.util.EditorView;
import com.gsr.myschool.common.client.util.ValueList;
import com.gsr.myschool.common.client.widget.renderer.ValueListRendererFactory;
import com.gsr.myschool.common.shared.type.SessionStatus;
import com.gsr.myschool.common.shared.type.ValueTypeCode;

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
    @UiField(provided = true)
    ValueListBox<ValueListProxy> anneeScolaire;

    private final Driver driver;
    private final ValueList valueList;
    private final BackRequestFactory requestFactory;

    @Inject
    public DossierFilterEditor(final Binder uiBinder, final Driver driver, final ValueList valueList,
                               final ValueListRendererFactory valueListRendererFactory,
                               final BackRequestFactory requestFactory) {
        this.driver = driver;
        this.valueList = valueList;
        this.requestFactory = requestFactory;

        this.filiere = new ValueListBox<FiliereProxy>(new FiliereRenderer());
        this.niveauEtude = new ValueListBox<NiveauEtudeProxy>(new NiveauEtudeRenderer());
        this.gsrFraterie = new ValueListBox<Boolean>(new BooleanListRenderer());
        this.parentGsr = new ValueListBox<Boolean>(new BooleanListRenderer());
        this.anneeScolaire = new ValueListBox<ValueListProxy>(valueListRendererFactory.create("Année Scolaire"));
        this.session = new ListBox(true);

        initWidget(uiBinder.createAndBindUi(this));
        driver.initialize(this);

        niveauEtude.setAcceptableValues(new ArrayList<NiveauEtudeProxy>());
        gsrFraterie.setAcceptableValues(BooleanListRenderer.acceptedValues());
        parentGsr.setAcceptableValues(BooleanListRenderer.acceptedValues());

        filiere.addValueChangeHandler(new ValueChangeHandler<FiliereProxy>() {
            @Override
            public void onValueChange(ValueChangeEvent<FiliereProxy> event) {
                if (event.getValue() != null) {
                    niveauEtude.setValue(null);
                    niveauEtude.setAcceptableValues(valueList.getNiveauEtudeList(event.getValue().getId()));
                } else {
                    niveauEtude.setValue(null);
                    niveauEtude.setAcceptableValues(new ArrayList<NiveauEtudeProxy>());
                }
            }
        });

        anneeScolaire.addValueChangeHandler(new ValueChangeHandler<ValueListProxy>() {
            @Override
            public void onValueChange(ValueChangeEvent<ValueListProxy> valueListProxyValueChangeEvent) {
                session.clear();
                getSessionByAnneeScolaire(valueListProxyValueChangeEvent.getValue());
            }
        });
    }

    @Override
    public void edit(DossierFilterDTOProxy object) {
        session.clear();
        getSessionByAnneeScolaire(anneeScolaire.getValue());
        driver.edit(object);
        filiere.setAcceptableValues(valueList.getFiliereList());
        if (filiere.getValue() != null) {
            niveauEtude.setAcceptableValues(valueList.getNiveauEtudeList(filiere.getValue().getId()));
        } else {
            niveauEtude.setAcceptableValues(new ArrayList<NiveauEtudeProxy>());
        }
        setAnneeScolaireValues(valueList);
    }

    private void getSessionByAnneeScolaire(ValueListProxy anneeScolaire) {
        requestFactory.sessionService().findAllSessionsWithStatusAndAnneeScolaire(SessionStatus.CLOSED, anneeScolaire)
                .fire(new ReceiverImpl<List<SessionExamenProxy>>() {
                    @Override
                    public void onSuccess(List<SessionExamenProxy> sessionExamenProxies) {
                        for (SessionExamenProxy item : sessionExamenProxies) {
                            session.addItem(item.getNom(), item.getId().toString());
                        }
                    }
                });
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

    private void setAnneeScolaireValues(ValueList valueList) {
        List<ValueListProxy> anneeScolaireList = valueList.getValueListByCode(ValueTypeCode.SCHOOL_YEAR, false);
        if (anneeScolaire.getValue() == null) {
            anneeScolaire.setValue(anneeScolaireList.get(0));
        }
        anneeScolaire.setAcceptableValues(anneeScolaireList);
    }
}
