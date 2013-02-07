package com.gsr.myschool.common.client.ui.user;

import com.github.gwtbootstrap.client.ui.PasswordTextBox;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.proxy.PasswordDTOProxy;
import com.gsr.myschool.common.client.util.EditorView;

import static com.google.gwt.query.client.GQuery.$;

public class PasswordEditor extends Composite implements EditorView<PasswordDTOProxy> {
    public interface Binder extends UiBinder<Widget, PasswordEditor> {
    }

    public interface Driver extends SimpleBeanEditorDriver<PasswordDTOProxy, PasswordEditor> {
    }

    @UiField
    PasswordTextBox oldPassword;
    @UiField
    PasswordTextBox password;
    @UiField
    PasswordTextBox passwordConfirmation;

    private final Driver driver;

    @Inject
    public PasswordEditor(final Binder uiBinder, final Driver driver) {
        this.driver = driver;

        initWidget(uiBinder.createAndBindUi(this));
        driver.initialize(this);

        $(oldPassword).id("oldPassword");
        $(password).id("password");
        $(passwordConfirmation).id("passwordConfirmation");
    }

    @Override
    public void edit(PasswordDTOProxy password) {
        driver.edit(password);
        oldPassword.setFocus(true);
    }

    @Override
    public PasswordDTOProxy get() {
        PasswordDTOProxy password = driver.flush();
        if (driver.hasErrors()) {
            return null;
        } else {
            return password;
        }
    }
}
