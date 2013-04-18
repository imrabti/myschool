package com.gsr.myschool.back.client.web.application.settings.popup;

import com.github.gwtbootstrap.client.ui.IntegerBox;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.github.gwtbootstrap.client.ui.ValueListBox;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.proxy.FiliereProxy;
import com.gsr.myschool.common.client.proxy.NiveauEtudeProxy;
import com.gsr.myschool.common.client.ui.dossier.renderer.FiliereRenderer;
import com.gsr.myschool.common.client.util.ValueList;
import com.gsr.myschool.common.client.widget.ModalHeader;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;

public class AddNiveauEtudeView extends PopupViewWithUiHandlers<AddNiveauEtudeUiHandlers>
        implements AddNiveauEtudePresenter.MyView {
    public interface Binder extends UiBinder<Widget, AddNiveauEtudeView> {
    }

    public interface Driver extends SimpleBeanEditorDriver<NiveauEtudeProxy, AddNiveauEtudeView>{
    }

    @UiField(provided = true)
    ModalHeader modalHeader;
    @UiField(provided = true)
    ValueListBox<FiliereProxy> filiere;
    @UiField
    TextBox nom;
    @UiField
    IntegerBox annee;

    private final Driver driver;

    @Inject
    public AddNiveauEtudeView(final EventBus eventBus, final Binder uiBinder,
                              final Driver driver, final ModalHeader modalHeader,
                              final ValueList valueList) {
        super(eventBus);

        this.driver = driver;
        this.modalHeader = modalHeader;
        this.filiere = new ValueListBox<FiliereProxy>(new FiliereRenderer());

        initWidget(uiBinder.createAndBindUi(this));
        driver.initialize(this);

        filiere.setAcceptableValues(valueList.getFiliereList());
        modalHeader.addCloseHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                hide();
            }
        });
    }

    @Override
    public void edit(NiveauEtudeProxy object) {
        driver.edit(object);
    }

    @Override
    public NiveauEtudeProxy get() {
        NiveauEtudeProxy niveauEtudeProxy = driver.flush();
        if (driver.hasErrors()) {
            return null;
        }
        return niveauEtudeProxy;
    }

    @UiHandler("save")
    void onSaveClicked(ClickEvent event) {
        NiveauEtudeProxy niveauEtudeProxy = get();
        if (niveauEtudeProxy != null) {
            getUiHandlers().saveNiveauEtude(niveauEtudeProxy);
        }
    }
}
