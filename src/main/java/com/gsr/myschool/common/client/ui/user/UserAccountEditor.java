package com.gsr.myschool.common.client.ui.user;

import com.github.gwtbootstrap.client.ui.ControlGroup;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.github.gwtbootstrap.client.ui.ValueListBox;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.proxy.UserProxy;
import com.gsr.myschool.common.client.util.EditorView;
import com.gsr.myschool.common.client.widget.renderer.EnumRenderer;
import com.gsr.myschool.common.shared.type.UserStatus;

import java.util.Arrays;

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
    @UiField(provided = true)
    ValueListBox<UserStatus> status;
    @UiField
    ControlGroup statusGroup;

    private final Diver driver;

    @Inject
    public UserAccountEditor(final Binder uiBinder, final Diver driver) {
        this.driver = driver;

        status = new ValueListBox<UserStatus>(new EnumRenderer<UserStatus>());
        status.setValue(UserStatus.ACTIVE);
        status.setAcceptableValues(Arrays.asList(UserStatus.values()));

        initWidget(uiBinder.createAndBindUi(this));
        driver.initialize(this);

        $(firstName).id("firstName");
        $(lastName).id("lastName");
        $(email).id("email");
        $(status).id("status");
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

    public void setStatusVisible(Boolean bool) {
        this.statusGroup.setVisible(bool);
    }
}
