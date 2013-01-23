package com.gsr.myschool.front.client.web.application.popup;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.mvp.ValidatedPopupViewImplWithUiHandlers;
import com.gsr.myschool.common.client.mvp.ValidationErrorPopup;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gsr.myschool.common.client.widget.ModalHeader;
import com.gsr.myschool.front.client.web.application.ui.PasswordEditor;
import com.gsr.myschool.front.client.web.application.ui.UserAccountEditor;

public class AccountSettingsView extends ValidatedPopupViewImplWithUiHandlers<AccountSettingsUiHandlers>
        implements AccountSettingsPresenter.MyView {
    public interface Binder extends UiBinder<PopupPanel, AccountSettingsView> {
    }

    @UiField(provided = true)
    ModalHeader modalHeader;
    @UiField(provided = true)
    UserAccountEditor userAccountEditor;
    @UiField(provided = true)
    PasswordEditor passwordEditor;

    @Inject
    public AccountSettingsView(final EventBus eventBus, final Binder uiBinder,
                               final UiHandlersStrategy<AccountSettingsUiHandlers> uiHandlers,
                               final ValidationErrorPopup errorPopup,
                               final ModalHeader modalHeader,
                               final UserAccountEditor userAccountEditor,
                               final PasswordEditor passwordEditor) {
        super(eventBus, errorPopup, uiHandlers);

        this.modalHeader = modalHeader;
        this.userAccountEditor = userAccountEditor;
        this.passwordEditor = passwordEditor;

        initWidget(uiBinder.createAndBindUi(this));

        modalHeader.addCloseHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                hide();
            }
        });
    }

    @UiHandler("cancel")
    void onCancelClicked(ClickEvent event) {
        hide();
    }
}