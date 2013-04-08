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

package com.gsr.myschool.common.client.ui;

import com.github.gwtbootstrap.client.ui.TextArea;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.proxy.EmailTemplateProxy;
import com.gsr.myschool.common.client.proxy.MatiereExamenProxy;
import com.gsr.myschool.common.client.util.EditorView;

import static com.google.gwt.query.client.GQuery.$;

public class EmailTemplateEditor extends Composite implements EditorView<EmailTemplateProxy> {
    public interface Binder extends UiBinder<Widget, EmailTemplateEditor> {
    }

    public interface Driver extends SimpleBeanEditorDriver<EmailTemplateProxy, EmailTemplateEditor> {
    }

    @UiField
    TextArea message;
    @UiField
    TextBox subject;

    private final Driver driver;

    @Inject
    public EmailTemplateEditor(final Binder uiBinder, final Driver driver) {
        this.driver = driver;

        initWidget(uiBinder.createAndBindUi(this));
        driver.initialize(this);

        $(message).id("message");
        $(subject).id("subject");
    }

    @Override
    public void edit(EmailTemplateProxy template) {
        driver.edit(template);
    }

    @Override
    public EmailTemplateProxy get() {
        EmailTemplateProxy template = driver.flush();
        if (driver.hasErrors()) {
            return null;
        } else {
            return template;
        }
    }
}
