package com.gsr.myschool.back.client.web.application.validation.popup.ui;

import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.proxy.PiecejustifDTOProxy;
import com.gsr.myschool.common.client.resource.SharedResources;
import com.gsr.myschool.common.client.util.EditorView;

import static com.google.gwt.query.client.GQuery.$;

public class PieceJustificatifEditor extends Composite implements EditorView<PiecejustifDTOProxy> {
    interface Binder extends UiBinder<Widget, PieceJustificatifEditor> {
    }

    public interface Driver extends SimpleBeanEditorDriver<PiecejustifDTOProxy, PieceJustificatifEditor> {
    }

    @UiField(provided = true)
    ToggleButton available;
    @UiField
    Label nom;
    @UiField
    TextBox motif;

    private final Driver driver;

    @Inject
    public PieceJustificatifEditor(final Binder uiBinder, final Driver driver,
                                   final SharedResources resource) {
        Image upImage =  new Image(resource.notChecked().getSafeUri());
        Image downImage = new Image(resource.checked().getSafeUri());

        this.driver = driver;
        this.available = new ToggleButton(upImage, downImage);

        initWidget(uiBinder.createAndBindUi(this));
        driver.initialize(this);

        $(motif).attr("placeholder", "Motif...");
    }

    @Override
    public void edit(PiecejustifDTOProxy object) {
        driver.edit(object);
    }

    @Override
    public PiecejustifDTOProxy get() {
        PiecejustifDTOProxy piece = driver.flush();
        if (driver.hasErrors()) {
            return null;
        } else {
            return piece;
        }
    }

    @UiHandler("available")
    void onAvailableClicked(ClickEvent event) {
        if (available.getValue()) {
            $(nom).css("text-decoration", "line-through");
            $(nom).css("color", "#999999");

            motif.setEnabled(false);
            motif.setText("");
        } else {
            $(nom).css("text-decoration", "none");
            $(nom).css("color", "#545454");

            motif.setEnabled(true);
        }
    }
}
