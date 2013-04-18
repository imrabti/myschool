package com.gsr.myschool.back.client.web.application.settings.popup;

import com.github.gwtbootstrap.client.ui.TextArea;
import com.github.gwtbootstrap.client.ui.TextBox;
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
import com.gsr.myschool.common.client.widget.ModalHeader;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;

public class AddFiliereView extends PopupViewWithUiHandlers<AddFiliereUiHandlers>
        implements AddFilierePresenter.MyView {
    public interface Binder extends UiBinder<Widget, AddFiliereView> {
    }

    public interface Driver extends SimpleBeanEditorDriver<FiliereProxy, AddFiliereView> {
    }

    @UiField(provided = true)
    ModalHeader modalHeader;
    @UiField
    TextBox nom;
    @UiField
    TextArea description;

    private final Driver driver;

    @Inject
    public AddFiliereView(final EventBus eventBus, final Binder uiBinder,
                          final Driver driver, final ModalHeader modalHeader) {
        super(eventBus);

        this.driver = driver;
        this.modalHeader = modalHeader;

        initWidget(uiBinder.createAndBindUi(this));
        driver.initialize(this);

        modalHeader.addCloseHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                hide();
            }
        });
    }

    @Override
    public void edit(FiliereProxy object) {
        driver.edit(object);
        nom.setFocus(true);
    }

    @Override
    public FiliereProxy get() {
        FiliereProxy filiereProxy = driver.flush();
        if (driver.hasErrors()) {
            return null;
        }
        return filiereProxy;
    }

    @UiHandler("save")
    void onSaveClicked(ClickEvent event) {
        FiliereProxy filiereProxy = get();
        if (filiereProxy != null) {
            getUiHandlers().saveFiliere(filiereProxy);
        }
    }
}
