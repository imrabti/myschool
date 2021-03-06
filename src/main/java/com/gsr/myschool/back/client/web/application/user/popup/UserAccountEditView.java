package com.gsr.myschool.back.client.web.application.user.popup;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.mvp.ValidatedPopupViewImplWithUiHandlers;
import com.gsr.myschool.common.client.widget.ValidationErrorPopup;
import com.gsr.myschool.common.client.proxy.UserProxy;
import com.gsr.myschool.common.client.ui.user.UserAccountEditor;
import com.gsr.myschool.common.client.widget.ModalHeader;

public class UserAccountEditView extends ValidatedPopupViewImplWithUiHandlers<UserAccountEditUiHandlers>
        implements UserAccountEditPresenter.MyView {
    public interface Binder extends UiBinder<PopupPanel, UserAccountEditView> {
    }

    @UiField(provided = true)
    ModalHeader modalHeader;
    @UiField(provided = true)
    UserAccountEditor userEditor;

    @Inject
    public UserAccountEditView(final EventBus eventBus, final Binder uiBinder,
                               final ValidationErrorPopup errorPopup,
                               final ModalHeader modalHeader,
                               final UserAccountEditor userEditor) {
        super(eventBus, errorPopup);

        this.modalHeader = modalHeader;
        this.userEditor = userEditor;

        initWidget(uiBinder.createAndBindUi(this));

        userEditor.setPasswordVisible(true);

        modalHeader.addCloseHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                hide();
            }
        });
    }

    @Override
    public void edit(UserProxy userProxy) {
        userEditor.edit(userProxy);
    }

    @UiHandler("cancel")
    void onCancelClicked(ClickEvent event) {
        hide();
    }

    @UiHandler("save")
    void onSaveClicked(ClickEvent event) {
        getUiHandlers().saveAccount(userEditor.get());
    }
}
