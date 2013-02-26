package com.gsr.myschool.front.client.web.welcome.widget;

import com.github.gwtbootstrap.client.ui.PasswordTextBox;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.mvp.ValidatedViewWithUiHandlers;
import com.gsr.myschool.common.client.mvp.ValidationErrorPopup;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gsr.myschool.common.client.proxy.ResetPasswordDTOProxy;
import com.gsr.myschool.common.client.util.EditorView;

import static com.google.gwt.query.client.GQuery.$;

public class ResetPasswordView extends ValidatedViewWithUiHandlers<ResetPasswordUiHandlers>
        implements ResetPasswordPresenter.MyView, EditorView<ResetPasswordDTOProxy> {
    public interface Binder extends UiBinder<Widget, ResetPasswordView> {
    }

    public interface Driver extends SimpleBeanEditorDriver<ResetPasswordDTOProxy, ResetPasswordView>{
    }

    @UiField
    PasswordTextBox password;
    @UiField
    PasswordTextBox passwordConfirmation;

    private final Driver driver;

    @Inject
    public ResetPasswordView(final Binder uiBinder, final ValidationErrorPopup errorPopup,
                             final UiHandlersStrategy<ResetPasswordUiHandlers> uiHandlersStrategy,
                             final Driver driver) {
        super(uiHandlersStrategy, errorPopup);

        this.driver = driver;

        initWidget(uiBinder.createAndBindUi(this));
        driver.initialize(this);

        $(password).id("password");
        $(passwordConfirmation).id("passwordConfirmation");
    }

    @Override
    public void edit(ResetPasswordDTOProxy object) {
        password.setFocus(true);
        driver.edit(object);
    }

    @Override
    public ResetPasswordDTOProxy get() {
        ResetPasswordDTOProxy resetPassword = driver.flush();
        if (driver.hasErrors()) {
            return null;
        } else {
            return resetPassword;
        }
    }

    @UiHandler("save")
    void onSaveClicked(ClickEvent event) {
        ResetPasswordDTOProxy resetPassword = get();
        if (resetPassword != null) {
            getUiHandlers().saveNewPassword(get());
        }
    }

    @UiHandler("passwordConfirmation")
    void onPasswordConfirmationKeyUp(KeyUpEvent event) {
        if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
            ResetPasswordDTOProxy resetPassword = get();
            if (resetPassword != null) {
                getUiHandlers().saveNewPassword(get());
            }
        }
    }
}
