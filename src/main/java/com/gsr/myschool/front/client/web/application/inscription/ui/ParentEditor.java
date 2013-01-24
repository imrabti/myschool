package com.gsr.myschool.front.client.web.application.inscription.ui;

import com.github.gwtbootstrap.client.ui.TextArea;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.github.gwtbootstrap.client.ui.ValueListBox;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.proxy.InfoParentProxy;
import com.gsr.myschool.common.client.util.EditorView;
import com.gsr.myschool.common.client.widget.renderer.EnumRenderer;
import com.gsr.myschool.common.shared.type.ParentType;

import java.util.Arrays;

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
    @UiField(provided = true)
    ValueListBox<ParentType> parentType;
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

        parentType = new ValueListBox<ParentType>(new EnumRenderer<ParentType>());
        parentType.setValue(ParentType.PERE);
        parentType.setAcceptableValues(Arrays.asList(ParentType.values()));

        initWidget(uiBinder.createAndBindUi(this));
        driver.initialize(this);

        $(nom).id("nom");
        $(prenom).id("prenom");
        $(parentType).id("parentType");
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
