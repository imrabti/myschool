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

package com.gsr.myschool.front.client.web.welcome.widget;

import com.github.gwtbootstrap.client.ui.PasswordTextBox;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.github.gwtbootstrap.client.ui.ValueListBox;
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
import com.gsr.myschool.common.client.widget.ValidationErrorPopup;
import com.gsr.myschool.common.client.proxy.UserProxy;
import com.gsr.myschool.common.client.util.EditorView;
import com.gsr.myschool.common.client.widget.renderer.EnumRenderer;
import com.gsr.myschool.common.shared.type.Gender;

import java.util.Arrays;

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
    TextBox firstName;
    @UiField
    TextBox lastName;
    @UiField(provided = true)
    ValueListBox<Gender> gender;
    @UiField
    PasswordTextBox password;
    @UiField
    PasswordTextBox passwordConfirmation;

    private final Driver driver;

    @Inject
    public RegisterView(final Binder uiBinder, final Driver driver,
                        final ValidationErrorPopup validationErrorPopup) {
        super(validationErrorPopup);

        this.driver = driver;
        this.gender = new ValueListBox<Gender>(new EnumRenderer<Gender>());

        initWidget(uiBinder.createAndBindUi(this));
        driver.initialize(this);

        $(email).id("email");
        $(firstName).id("firstName");
        $(lastName).id("lastName");
        $(password).id("password");
        $(gender).id("gender");
        $(passwordConfirmation).id("passwordConfirmation");
    }

    @Override
    public void edit(UserProxy user) {
        email.setFocus(true);
        driver.edit(user);

        gender.setValue(Gender.MALE);
        gender.setAcceptableValues(Arrays.asList(Gender.values()));

        initPlaceHolder();
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

    @UiHandler("resendMail")
    void onResendMailClicked(ClickEvent event) {
        getUiHandlers().resendMail();
    }

    private void processRegisterAction() {
        UserProxy user = get();
        getUiHandlers().register(user);
    }

    public native void initPlaceHolder() /*-{
        $wnd.$('input, textarea').placeholder();
    }-*/;
}
