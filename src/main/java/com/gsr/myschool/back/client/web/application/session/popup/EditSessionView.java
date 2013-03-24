package com.gsr.myschool.back.client.web.application.session.popup;

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

public class EditSessionView extends PopupViewWithUiHandlers<EditSessionUiHandlers>
        implements EditSessionPresenter.MyView {
    interface Binder extends UiBinder<Widget, EditSessionView> {
    }

    @UiField(provided = true)
    ModalHeader modalHeader;

    @Inject
    public EditSessionView(final EventBus eventBus, final Binder uiBinder,
                           final UiHandlersStrategy<EditSessionUiHandlers> uiHandlers,
                           final ModalHeader modalHeader) {
        super(eventBus, uiHandlers);

        this.modalHeader = modalHeader;

        initWidget(uiBinder.createAndBindUi(this));

        modalHeader.addCloseHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                hide();
            }
        });
    }

    @UiHandler("save")
    void onSaveClicked(ClickEvent event) {
        // TODO : Call UiHandler for save button
    }

    @UiHandler("cancel")
    void onCancelClicked(ClickEvent event) {
        hide();
    }
}