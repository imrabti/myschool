package com.gsr.myschool.common.client.ui.dossier;

import com.github.gwtbootstrap.client.ui.TextArea;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.proxy.InfoParentProxy;
import com.gsr.myschool.common.client.util.EditorView;

import static com.google.gwt.query.client.GQuery.$;

public class ParentEditor extends Composite implements EditorView<InfoParentProxy> {
    public interface Binder extends UiBinder<Widget, ParentEditor> {
    }

    public interface Driver extends SimpleBeanEditorDriver<InfoParentProxy, ParentEditor> {
    }

    @UiField
    TextBox nom;
    @UiField
    TextBox prenom;
    @UiField
    TextBox fonction;
    @UiField
    TextBox institution;
    @UiField
    TextBox email;
    @UiField
    TextBox telDom;
    @UiField
    TextBox telGsm;
    @UiField
    TextBox telBureau;
    @UiField
    TextArea address;

    private final Driver driver;

    @Inject
    public ParentEditor(final Binder uiBinder, final Driver driver) {
        this.driver = driver;

        initWidget(uiBinder.createAndBindUi(this));
        driver.initialize(this);

        $(nom).id("nom");
        $(prenom).id("prenom");
        $(fonction).id("fonction");
        $(institution).id("institution");
        $(email).id("email");
        $(telDom).id("telDom");
        $(telGsm).id("telGsm");
        $(telBureau).id("telBureau");
        $(address).id("address");
    }

    @Override
    public void edit(InfoParentProxy infoParent) {
        nom.setFocus(true);
        driver.edit(infoParent);
    }

    @Override
    public InfoParentProxy get() {
        InfoParentProxy infoParent = driver.flush();
        if (driver.hasErrors()) {
            return null;
        } else {
            return infoParent;
        }
    }
}
