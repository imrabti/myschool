package com.gsr.myschool.common.client.ui.user;

import com.github.gwtbootstrap.client.ui.TextBox;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.proxy.UserProxy;
import com.gsr.myschool.common.client.util.EditorView;

import static com.google.gwt.query.client.GQuery.$;

public class UserAccountEditor extends Composite implements EditorView<UserProxy> {
    public interface Binder extends UiBinder<Widget, UserAccountEditor> {
    }

    public interface Diver extends SimpleBeanEditorDriver<UserProxy, UserAccountEditor> {
    }

    @UiField
    TextBox firstName;
    @UiField
    TextBox lastName;
    @UiField
    TextBox email;

    private final Diver driver;

    @Inject
    public UserAccountEditor(final Binder uiBinder, final Diver driver) {
        this.driver = driver;

        initWidget(uiBinder.createAndBindUi(this));
        driver.initialize(this);

        $(firstName).id("firstName");
        $(lastName).id("lastName");
        $(email).id("email");
    }

    @Override
    public void edit(final UserProxy user) {
        driver.edit(user);
        firstName.setFocus(true);
    }

    @Override
    public UserProxy get() {
        UserProxy user = driver.flush();
        if (driver.hasErrors()) {
            return null;
        } else {
            return user;
        }
    }
}
