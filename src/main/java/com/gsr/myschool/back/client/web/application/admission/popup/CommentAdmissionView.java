package com.gsr.myschool.back.client.web.application.admission.popup;

import com.github.gwtbootstrap.client.ui.TextArea;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.mvp.ValidatedPopupViewImplWithUiHandlers;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gsr.myschool.common.client.widget.ModalHeader;
import com.gsr.myschool.common.client.widget.ValidationErrorPopup;

public class CommentAdmissionView extends ValidatedPopupViewImplWithUiHandlers<CommentAdmissionUiHandlers>
        implements CommentAdmissionPresenter.MyView {
    public interface Binder extends UiBinder<PopupPanel, CommentAdmissionView> {
    }

    @UiField(provided = true)
    ModalHeader modalHeader;
    @UiField
    TextArea reason;

    @Inject
    public CommentAdmissionView(final EventBus eventBus, final Binder uiBinder,
            final UiHandlersStrategy<CommentAdmissionUiHandlers> uiHandlers,
            final ValidationErrorPopup errorPopup,
            final ModalHeader modalHeader) {
        super(eventBus, errorPopup, uiHandlers);

        this.modalHeader = modalHeader;

        initWidget(uiBinder.createAndBindUi(this));

        modalHeader.addCloseHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                hide();
            }
        });
    }

    @Override
    public void resetReason() {
        reason.setText("");
    }

    @UiHandler("cancel")
    void onCancelClicked(ClickEvent event) {
        hide();
    }

    @UiHandler("save")
    void onSaveClicked(ClickEvent event) {
        getUiHandlers().setAdmissionComment(reason.getText());
        hide();
    }
}
