package com.gsr.myschool.back.client.web.application.user.popup;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.request.UserServiceRequest;
import com.gsr.myschool.common.client.mvp.ValidatedPopupViewImplWithUiHandlers;
import com.gsr.myschool.common.client.mvp.ValidationErrorPopup;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
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
            final UiHandlersStrategy<UserAccountEditUiHandlers> uiHandlers,
            final ValidationErrorPopup errorPopup,
            final ModalHeader modalHeader,
            final UserAccountEditor userEditor) {
        super(eventBus, errorPopup, uiHandlers);

        this.modalHeader = modalHeader;
        this.userEditor = userEditor;

        initWidget(uiBinder.createAndBindUi(this));

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

    @Override
    public void refreshUserList() {
        getUiHandlers().reloadUsers();
    }

    @UiHandler("cancel")
    void onCancelClicked(ClickEvent event) {
        hide();
    }

    @UiHandler("save")
    void onSaveClicked(ClickEvent event) {
        userEditor.get();
        hide();
    }
}
