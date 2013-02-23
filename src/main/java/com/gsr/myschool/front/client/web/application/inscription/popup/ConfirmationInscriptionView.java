package com.gsr.myschool.front.client.web.application.inscription.popup;

import com.github.gwtbootstrap.client.ui.CheckBox;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.mvp.PopupViewWithUiHandlers;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gsr.myschool.common.client.widget.ModalHeader;

public class ConfirmationInscriptionView extends PopupViewWithUiHandlers<ConfirmationInscriptionUiHandlers>
        implements ConfirmationInscriptionPresenter.MyView {
    public interface Binder extends UiBinder<Widget, ConfirmationInscriptionView> {
    }

    @UiField(provided = true)
    ModalHeader modalHeader;
    @UiField
    CheckBox declarationHonneur;

    @Inject
    public ConfirmationInscriptionView(final EventBus eventBus, final Binder uiBinder,
                                       final UiHandlersStrategy<ConfirmationInscriptionUiHandlers> uiHandlersStrategy,
                                       final ModalHeader modalHeader) {
        super(eventBus, uiHandlersStrategy);

        this.modalHeader = modalHeader;

        initWidget(uiBinder.createAndBindUi(this));

        modalHeader.addCloseHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                hide();
            }
        });
    }

    @UiHandler("validate")
    void onValidateClicked(ClickEvent event) {
        getUiHandlers().validateInscription(declarationHonneur.getValue());
    }
}
