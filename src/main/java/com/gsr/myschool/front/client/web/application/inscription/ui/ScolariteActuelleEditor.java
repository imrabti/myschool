package com.gsr.myschool.front.client.web.application.inscription.ui;

import com.github.gwtbootstrap.client.ui.TextBox;
import com.github.gwtbootstrap.client.ui.ValueListBox;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.proxy.NiveauEtudeProxy;
import com.gsr.myschool.common.client.proxy.ScolariteActuelleDTOProxy;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.security.SecurityUtils;
import com.gsr.myschool.common.client.ui.dossier.renderer.NiveauEtudeRenderer;
import com.gsr.myschool.common.client.util.EditorView;
import com.gsr.myschool.common.client.util.ValueList;
import com.gsr.myschool.common.client.widget.renderer.EnumRenderer;
import com.gsr.myschool.common.shared.type.Authority;
import com.gsr.myschool.common.shared.type.TypeEnseignement;
import com.gsr.myschool.front.client.request.FrontRequestFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.gwt.query.client.GQuery.$;

public class ScolariteActuelleEditor extends Composite implements EditorView<ScolariteActuelleDTOProxy> {
    public interface Binder extends UiBinder<HTMLPanel, ScolariteActuelleEditor> {
    }

    public interface Driver extends SimpleBeanEditorDriver<ScolariteActuelleDTOProxy, ScolariteActuelleEditor> {
    }

    public interface Handler {
        void onSelectEtablissement();
    }

    @UiField
    @Ignore
    TextBox etablissementLabel;
    @UiField(provided = true)
    ValueListBox<TypeEnseignement> typeEnseignement;
    @UiField(provided = true)
    ValueListBox<NiveauEtudeProxy> niveauEtude;

    private final Driver driver;
    private final ValueList valueList;
    private final SecurityUtils securityUtils;
    private final FrontRequestFactory requestFactory;

    private Handler handler;

    @Inject
    public ScolariteActuelleEditor(final Binder uiBinder, final Driver driver,
                                   final ValueList valueList,
                                   final SecurityUtils securityUtils,
                                   final FrontRequestFactory requestFactory) {
        this.driver = driver;
        this.valueList = valueList;
        this.securityUtils = securityUtils;
        this.requestFactory = requestFactory;

        typeEnseignement = new ValueListBox<TypeEnseignement>(new EnumRenderer<TypeEnseignement>());
        niveauEtude = new ValueListBox<NiveauEtudeProxy>(new NiveauEtudeRenderer());

        initWidget(uiBinder.createAndBindUi(this));
        driver.initialize(this);

        typeEnseignement.setAcceptableValues(Arrays.asList(TypeEnseignement.values()));

        $(typeEnseignement).id("typeEnseignement");
        $(niveauEtude).id("niveauEtude");
    }

    @Override
    public void edit(ScolariteActuelleDTOProxy scolariteAnterieur) {
        driver.edit(scolariteAnterieur);
    }

    @Override
    public ScolariteActuelleDTOProxy get() {
        ScolariteActuelleDTOProxy scolariteAnterieur = driver.flush();
        if (driver.hasErrors()) {
            return null;
        } else {
            return scolariteAnterieur;
        }
    }

    public void setEtablissementLabel(String etablissementLabel) {
        this.etablissementLabel.setText(etablissementLabel);
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @UiHandler("selectEtablissement")
    void onSelectEtablissementClicked(ClickEvent event) {
        handler.onSelectEtablissement();
    }

    @UiHandler("typeEnseignement")
    void onTypeEnseignementChanged(ValueChangeEvent<TypeEnseignement> event) {
        if (event.getValue() != null) {
            niveauEtude.setValue(null);
            requestFactory.cachedListValueService().findNiveauEtudesByFiliere(typeEnseignement.getValue().getId(),
                    securityUtils.isSuperUser() || securityUtils.hasAuthority(Authority.ROLE_USER_VIP.name())).fire(new ReceiverImpl<List<NiveauEtudeProxy>>() {
                @Override
                public void onSuccess(List<NiveauEtudeProxy> niveauEtudeProxies) {
                    niveauEtude.setAcceptableValues(niveauEtudeProxies);
                }
            });
        } else {
            niveauEtude.setValue(null);
            niveauEtude.setAcceptableValues(new ArrayList<NiveauEtudeProxy>());
        }
    }
}
