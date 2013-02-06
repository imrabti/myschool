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

package com.gsr.myschool.front.client.web.welcome.login;

import com.github.gwtbootstrap.client.ui.PasswordTextBox;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.google.common.base.Strings;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.mvp.ViewWithUiHandlers;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gsr.myschool.front.client.resource.message.MessageBundle;
import com.gsr.myschool.common.client.util.EditorView;
import com.gsr.myschool.common.shared.dto.UserCredentials;

public class LoginView extends ViewWithUiHandlers<LoginUiHandlers> implements LoginPresenter.MyView,
        EditorView<UserCredentials> {
    public interface Binder extends UiBinder<Widget, LoginView> {
    }

    public interface Driver extends SimpleBeanEditorDriver<UserCredentials, LoginView> {
    }

    @UiField
    TextBox username;
    @UiField
    PasswordTextBox password;
    @UiField
    @Ignore
    Label loginError;

    private final MessageBundle messageBundle;
    private final Driver driver;

    @Inject
    public LoginView(final Binder uiBinder, final MessageBundle messageBundle,
                     final UiHandlersStrategy<LoginUiHandlers> uiHandlers,
                     final Driver driver) {
        super(uiHandlers);

        this.messageBundle = messageBundle;
        this.driver = driver;

        initWidget(uiBinder.createAndBindUi(this));
        driver.initialize(this);
    }

    @Override
    public void edit(UserCredentials credentials) {
        username.setFocus(true);
        driver.edit(credentials);
        loginError.setVisible(false);
    }

    @Override
    public UserCredentials get() {
        UserCredentials credentials = driver.flush();
        if (driver.hasErrors()) {
            return null;
        } else {
            return credentials;
        }
    }

    @Override
    public void displayLoginError(Boolean visible) {
        loginError.setVisible(visible);
        loginError.setText(messageBundle.wrongLoginOrPassword());
    }

    @UiHandler("register")
    void onRegisterClicked(ClickEvent event) {
        getUiHandlers().register();
    }

    @UiHandler("login")
    void onLoginClicked(ClickEvent event) {
        processLoginAction();
    }

    @UiHandler("password")
    void onPasswordKeyUp(KeyUpEvent event) {
        if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
            processLoginAction();
        }
    }

    private void processLoginAction() {
        UserCredentials credentials = get();

        if (!Strings.isNullOrEmpty(credentials.getUsername()) && !Strings.isNullOrEmpty(credentials.getPassword())) {
            getUiHandlers().login(credentials);
            loginError.setVisible(false);
        } else {
            loginError.setVisible(true);
            loginError.setText(messageBundle.loginPasswordRequired());
        }
    }
}
