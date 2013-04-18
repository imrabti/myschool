package com.gsr.myschool.front.client.web.application.inscription.popup;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.CheckBox;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.widget.ModalHeader;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;

public class ConfirmationInscriptionView extends PopupViewWithUiHandlers<ConfirmationInscriptionUiHandlers>
        implements ConfirmationInscriptionPresenter.MyView {
    public interface Binder extends UiBinder<Widget, ConfirmationInscriptionView> {
    }

    @UiField(provided = true)
    ModalHeader modalHeader;
    @UiField
    CheckBox declarationHonneur;
    @UiField
    Button validate;

    @Inject
    public ConfirmationInscriptionView(final EventBus eventBus, final Binder uiBinder,
                                       final ModalHeader modalHeader) {
        super(eventBus);

        this.modalHeader = modalHeader;

        initWidget(uiBinder.createAndBindUi(this));

        modalHeader.addCloseHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                hide();
            }
        });
    }

    @Override
    public void initConfirmation() {
        declarationHonneur.setValue(false);
        validate.setEnabled(false);
    }

    @UiHandler("declarationHonneur")
    void onDeclarationHonneurChanged(ValueChangeEvent<Boolean> event) {
        validate.setEnabled(declarationHonneur.getValue());
    }

    @UiHandler("validate")
    void onValidateClicked(ClickEvent event) {
        getUiHandlers().validateInscription(declarationHonneur.getValue());
    }

    @UiHandler("cancel")
    void onCancelClicked(ClickEvent event) {
        hide();
    }
}
