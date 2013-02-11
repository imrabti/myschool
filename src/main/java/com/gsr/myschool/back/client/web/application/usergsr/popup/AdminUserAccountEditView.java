package com.gsr.myschool.back.client.web.application.usergsr.popup;

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
import com.gsr.myschool.common.client.proxy.AdminUserProxy;
import com.gsr.myschool.common.client.ui.user.AdminUserAccountEditor;
import com.gsr.myschool.common.client.widget.ModalHeader;

public class AdminUserAccountEditView extends ValidatedPopupViewImplWithUiHandlers<AdminUserAccountEditUiHandlers>
        implements AdminUserAccountEditPresenter.MyView {
    public interface Binder extends UiBinder<PopupPanel, AdminUserAccountEditView> {
    }

    @UiField(provided = true)
    ModalHeader modalHeader;
    @UiField(provided = true)
    AdminUserAccountEditor adminEditor;

    @Inject
    public AdminUserAccountEditView(final EventBus eventBus, final Binder uiBinder,
            final UiHandlersStrategy<AdminUserAccountEditUiHandlers> uiHandlers,
            final ValidationErrorPopup errorPopup, final ModalHeader modalHeader,
            final AdminUserAccountEditor adminEditor) {
        super(eventBus, errorPopup, uiHandlers);

        this.modalHeader = modalHeader;
        this.adminEditor = adminEditor;

        initWidget(uiBinder.createAndBindUi(this));

        modalHeader.addCloseHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                hide();
            }
        });
    }

    @Override
    public void edit(AdminUserProxy userProxy) {
        adminEditor.edit(userProxy);
    }

    @Override
    public void flush() {
        adminEditor.get();
    }

    @UiHandler("cancel")
    void onCancelClicked(ClickEvent event) {
        hide();
    }

    @UiHandler("save")
    void onSaveClicked(ClickEvent event) {
        getUiHandlers().saveAccount();
    }
}
