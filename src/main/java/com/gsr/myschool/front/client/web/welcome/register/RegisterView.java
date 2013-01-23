/**
 * Copyright 2012 Nuvola Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.gsr.myschool.front.client.web.welcome.register;

import com.google.common.base.Strings;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.mvp.ValidatedViewWithUiHandlers;
import com.gsr.myschool.common.client.mvp.ValidationErrorPopup;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gsr.myschool.common.client.proxy.UserProxy;
import com.gsr.myschool.common.client.util.EditorView;
import com.gsr.myschool.front.client.resource.message.MessageBundle;

import static com.google.gwt.query.client.GQuery.$;

public class RegisterView extends ValidatedViewWithUiHandlers<RegisterUiHandlers>
        implements RegisterPresenter.MyView, EditorView<UserProxy> {
    public interface Binder extends UiBinder<Widget, RegisterView> {
    }

    public interface Driver extends SimpleBeanEditorDriver<UserProxy, RegisterView> {
    }

    @UiField
    TextBox email;
    @UiField
    TextBox username;
    @UiField
    TextBox firstName;
    @UiField
    TextBox lastName;
    @UiField
    PasswordTextBox password;
    @UiField
    @Ignore
    PasswordTextBox passwordConfirmation;

    private final Driver driver;

    @Inject
    public RegisterView(final Binder uiBinder, final Driver driver,
                        final UiHandlersStrategy<RegisterUiHandlers> uiHandlersStrategy,
                        final ValidationErrorPopup validationErrorPopup) {
        super(uiHandlersStrategy, validationErrorPopup);

        this.driver = driver;

        initWidget(uiBinder.createAndBindUi(this));
        driver.initialize(this);

        $(email).id("email");
        $(username).id("username");
        $(password).id("password");
        $(firstName).id("firstName");
        $(lastName).id("lastName");
    }

    @Override
    public void edit(UserProxy user) {
        email.setFocus(true);
        driver.edit(user);
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

    @UiHandler("register")
    void onRegisterClicked(ClickEvent event) {
        processRegisterAction();
    }

    @UiHandler("password")
    void onPasswordKeyUp(KeyUpEvent event) {
        if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
            processRegisterAction();
        }
    }

    private void processRegisterAction() {
        UserProxy user = get();
        getUiHandlers().register(user);
    }
}
