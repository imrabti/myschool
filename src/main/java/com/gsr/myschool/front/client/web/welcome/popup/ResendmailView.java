package com.gsr.myschool.front.client.web.welcome.popup;

import com.github.gwtbootstrap.client.ui.TextBox;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.mvp.ValidatedPopupViewImplWithUiHandlers;
import com.gsr.myschool.common.client.widget.ValidationErrorPopup;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gsr.myschool.common.client.proxy.ForgotPasswordDTOProxy;
import com.gsr.myschool.common.client.util.EditorView;
import com.gsr.myschool.common.client.widget.ModalHeader;

public class ResendmailView extends ValidatedPopupViewImplWithUiHandlers<ResendmailUiHandlers>
        implements ResendmailPresenter.MyView, EditorView<ForgotPasswordDTOProxy> {
    public interface Binder extends UiBinder<Widget, ResendmailView> {
    }

    public interface Driver extends SimpleBeanEditorDriver<ForgotPasswordDTOProxy, ResendmailView> {
    }

    @UiField(provided = true)
    ModalHeader modalHeader;
    @UiField
    TextBox email;

    private final Driver driver;

    @Inject
    public ResendmailView(final EventBus eventBus, final Binder uiBinder,
                          final ValidationErrorPopup errorPopup, final Driver driver,
                          final UiHandlersStrategy<ResendmailUiHandlers> uiHandlersStrategy,
                          final ModalHeader modalHeader) {
        super(eventBus, errorPopup, uiHandlersStrategy);

        this.driver = driver;
        this.modalHeader = modalHeader;

        initWidget(uiBinder.createAndBindUi(this));
        driver.initialize(this);

        modalHeader.addCloseHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                hide();
            }
        });
    }

    @Override
    public void edit(ForgotPasswordDTOProxy object) {
        email.setFocus(true);
        driver.edit(object);
    }

    @Override
    public ForgotPasswordDTOProxy get() {
        ForgotPasswordDTOProxy forgotPassword = driver.flush();
        if (driver.hasErrors()) {
            return null;
        } else {
            return forgotPassword;
        }
    }

    @UiHandler("send")
    void onSendClicked(ClickEvent event) {
        ForgotPasswordDTOProxy forgotPassword = get();
        if (forgotPassword != null) {
            getUiHandlers().mailnotreceived(forgotPassword);
        }
    }

    @UiHandler("email")
    void onEmailKeyUp(KeyUpEvent event) {
        if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
            ForgotPasswordDTOProxy forgotPassword = get();
            if (forgotPassword != null) {
                getUiHandlers().mailnotreceived(forgotPassword);
            }
        }
    }

    @UiHandler("cancel")
    void onCancelClicked(ClickEvent event) {
        hide();
    }
}
