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
import com.gsr.myschool.common.client.proxy.UserProxy;

public class UserAccountEditor extends Composite implements Editor<UserProxy> {
    public interface Binder extends UiBinder<Widget, UserAccountEditor> {
    }

    public interface AccountDriver extends RequestFactoryEditorDriver<UserProxy, UserAccountEditor> {
    }

    @UiField
    TextBox firstName;
    @UiField
    TextBox lastName;
    @UiField
    TextBox email;

    private final AccountDriver accountDriver;

    private UserProxy currentUser;

    @Inject
    public UserAccountEditor(final Binder uiBinder, final AccountDriver accountDriver) {
        initWidget(uiBinder.createAndBindUi(this));

        this.accountDriver = accountDriver;
        accountDriver.initialize(this);
    }

    public void edit(final UserProxy user, final RequestContext context) {
        currentUser = context.edit(user);
        accountDriver.edit(user, context);
    }

    public void onSaveClicked() {
        RequestContext context = accountDriver.flush();
        context.fire();
    }
}
