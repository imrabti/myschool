package com.gsr.myschool.back.client.web.application.session.popup;

import com.github.gwtbootstrap.client.ui.ValueListBox;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.mvp.PopupViewWithUiHandlers;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gsr.myschool.common.client.proxy.FiliereProxy;
import com.gsr.myschool.common.client.proxy.NiveauEtudeProxy;
import com.gsr.myschool.common.client.ui.dossier.renderer.FiliereRenderer;
import com.gsr.myschool.common.client.ui.dossier.renderer.NiveauEtudeRenderer;
import com.gsr.myschool.common.client.util.ValueList;
import com.gsr.myschool.common.client.widget.ModalHeader;
import com.gsr.myschool.common.shared.constants.GlobalParameters;

import java.util.ArrayList;
import java.util.List;

public class NewNiveauEtudeView extends PopupViewWithUiHandlers<NewNiveauEtudeUiHandlers>
        implements NewNiveauEtudePresenter.MyView {
    interface Binder extends UiBinder<Widget, NewNiveauEtudeView> {
    }

    @UiField(provided = true)
    ModalHeader modalHeader;
    @UiField(provided = true)
    ValueListBox<FiliereProxy> filiere;
    @UiField(provided = true)
    ValueListBox<NiveauEtudeProxy> niveauEtude;

    private final ValueList valueList;

    @Inject
    public NewNiveauEtudeView(final EventBus eventBus, final Binder uiBinder,
                              final UiHandlersStrategy<NewNiveauEtudeUiHandlers> uiHandlers,
                              final ModalHeader modalHeader, final ValueList valueList) {
        super(eventBus, uiHandlers);

        this.modalHeader = modalHeader;
        this.valueList = valueList;
        this.filiere = new ValueListBox<FiliereProxy>(new FiliereRenderer());
        this.niveauEtude = new ValueListBox<NiveauEtudeProxy>(new NiveauEtudeRenderer());

        initWidget(uiBinder.createAndBindUi(this));

        filiere.setAcceptableValues(valueList.getFiliereList());
        niveauEtude.setAcceptableValues(new ArrayList<NiveauEtudeProxy>());

        modalHeader.addCloseHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                hide();
            }
        });
    }

    @UiHandler("filiere")
    void onFiliereChanged(ValueChangeEvent<FiliereProxy> event) {
        if (event.getValue() != null) {
            niveauEtude.setValue(null);
            List<NiveauEtudeProxy> toShow = new ArrayList<NiveauEtudeProxy>();
            for (NiveauEtudeProxy niveauEtude : valueList.getNiveauEtudeList(event.getValue().getId())) {
                if (!GlobalParameters.NE_toute_petite_section_ids.contains(niveauEtude.getId())) {
                    toShow.add(niveauEtude);
                }
            }
            niveauEtude.setAcceptableValues(toShow);
        } else {
            niveauEtude.setValue(null);
            niveauEtude.setAcceptableValues(new ArrayList<NiveauEtudeProxy>());
        }
    }

    @Override
    public void resetData() {
        filiere.setValue(null);
        niveauEtude.setValue(null);
        niveauEtude.setAcceptableValues(new ArrayList<NiveauEtudeProxy>());
    }

    @UiHandler("save")
    void onSaveClicked(ClickEvent event) {
        if (niveauEtude.getValue() != null) {
            getUiHandlers().attachNiveauEtude(niveauEtude.getValue().getId());
        }
    }

    @UiHandler("cancel")
    void onCancelClicked(ClickEvent event) {
        hide();
    }
}