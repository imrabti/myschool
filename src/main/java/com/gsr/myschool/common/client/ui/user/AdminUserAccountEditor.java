package com.gsr.myschool.common.client.ui.user;

import com.github.gwtbootstrap.client.ui.TextBox;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.gsr.myschool.common.client.proxy.AdminUserProxy;

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

    private final AccountDriver accountDriver;

    private AdminUserProxy currentUser;

    @Inject
    public AdminUserAccountEditor(final Binder uiBinder, final AccountDriver accountDriver) {
        initWidget(uiBinder.createAndBindUi(this));

        this.accountDriver = accountDriver;
        accountDriver.initialize(this);
    }

    public void edit(final AdminUserProxy user, final RequestContext context) {
        currentUser = context.edit(user);
        accountDriver.edit(user, context);
    }

    public void onSaveClicked() {
        RequestContext context = accountDriver.flush();
        context.fire();
    }
}
