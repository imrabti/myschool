package com.gsr.myschool.common.client.ui.user;

import com.github.gwtbootstrap.client.ui.TextBox;
import com.github.gwtbootstrap.client.ui.ValueListBox;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.gsr.myschool.common.client.proxy.AdminUserProxy;
import com.gsr.myschool.common.client.widget.renderer.EnumRenderer;
import com.gsr.myschool.common.shared.type.Authority;
import com.gsr.myschool.common.shared.type.UserStatus;

import java.util.Arrays;

import static com.google.gwt.query.client.GQuery.$;

public class AdminUserAccountEditor extends Composite implements Editor<AdminUserProxy> {
    public interface Binder extends UiBinder<Widget, AdminUserAccountEditor> {
    }

    public interface AccountDriver extends RequestFactoryEditorDriver<AdminUserProxy, AdminUserAccountEditor> {
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

    private final AccountDriver accountDriver;

    private AdminUserProxy currentUser;

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
    }

    public void edit(final AdminUserProxy user, final RequestContext context) {
        currentUser = context.edit(user);
        accountDriver.edit(user, context);
    }

    public void changeUserStatus(AdminUserProxy user, final RequestContext context) {
        accountDriver.edit(user, context);
        if (user.getStatus().equals(UserStatus.ACTIVE)) {
            status.setValue(UserStatus.INACTIVE);
        } else if (user.getStatus().equals(UserStatus.INACTIVE)) {
            status.setValue(UserStatus.ACTIVE);
        }
        saveAccount();
    }

    public AdminUserProxy saveAccount() {
        RequestContext context = accountDriver.flush();
        if (accountDriver.hasErrors()) {
            return null;
        } else {
            context.fire();
            return currentUser;
        }
    }
}
