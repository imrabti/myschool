package com.gsr.myschool.common.client.ui.user;

import com.github.gwtbootstrap.client.ui.PasswordTextBox;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.github.gwtbootstrap.client.ui.ValueListBox;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.gsr.myschool.common.client.proxy.AdminUserProxy;
import com.gsr.myschool.common.client.util.EditorView;
import com.gsr.myschool.common.client.widget.renderer.EnumRenderer;
import com.gsr.myschool.common.shared.type.Authority;
import com.gsr.myschool.common.shared.type.UserStatus;

import java.util.Arrays;

import static com.google.gwt.query.client.GQuery.$;

public class AdminUserAccountEditor extends Composite implements EditorView<AdminUserProxy> {
    public interface Binder extends UiBinder<Widget, AdminUserAccountEditor> {
    }

    public interface AccountDriver extends SimpleBeanEditorDriver<AdminUserProxy, AdminUserAccountEditor> {
    }

    @UiField
    TextBox firstName;
    @UiField
    TextBox lastName;
    @UiField
    TextBox email;
    @UiField(provided = true)
    ValueListBox<UserStatus> status;
    @UiField(provided = true)
    ValueListBox<Authority> authority;
    @UiField
    PasswordTextBox password;

    private final AccountDriver accountDriver;

    @Inject
    public AdminUserAccountEditor(final Binder uiBinder, final AccountDriver accountDriver) {
        authority = new ValueListBox<Authority>(new EnumRenderer<Authority>());
        authority.setValue(Authority.ROLE_OPERATOR);
        authority.setAcceptableValues(Arrays.asList(Authority.adminRoles()));

        status = new ValueListBox<UserStatus>(new EnumRenderer<UserStatus>());
        status.setAcceptableValues(Arrays.asList(UserStatus.values()));

        initWidget(uiBinder.createAndBindUi(this));

        this.accountDriver = accountDriver;
        accountDriver.initialize(this);

        $(firstName).id("firstName");
        $(lastName).id("lastName");
        $(email).id("email");
        $(status).id("status");
        $(authority).id("authority");
        $(password).id("password");
    }

    @Override
    public void edit(final AdminUserProxy user) {
        accountDriver.edit(user);
    }

    @Override
    public AdminUserProxy get() {
        AdminUserProxy adminUser = accountDriver.flush();
        if (accountDriver.hasErrors()) {
            return null;
        } else {
            return adminUser;
        }
    }
}
