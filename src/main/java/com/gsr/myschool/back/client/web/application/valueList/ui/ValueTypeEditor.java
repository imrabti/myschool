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

package com.gsr.myschool.back.client.web.application.valueList.ui;

import com.github.gwtbootstrap.client.ui.CheckBox;
import com.github.gwtbootstrap.client.ui.ValueListBox;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.common.client.proxy.ValueListProxy;
import com.gsr.myschool.common.client.proxy.ValueTypeProxy;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;
import com.gsr.myschool.common.client.util.EditorView;
import com.gsr.myschool.common.client.widget.renderer.EnumRenderer;
import com.gsr.myschool.common.client.widget.renderer.ValueListRendererFactory;
import com.gsr.myschool.common.client.widget.renderer.ValueTypeRenderer;
import com.gsr.myschool.common.shared.type.ValueTypeCode;

import java.util.Arrays;
import java.util.List;

import static com.google.gwt.query.client.GQuery.$;

public class ValueTypeEditor extends Composite implements EditorView<ValueTypeProxy> {
    public interface Binder extends UiBinder<Widget, ValueTypeEditor> {
    }

    public interface Driver extends SimpleBeanEditorDriver<ValueTypeProxy, ValueTypeEditor> {
    }

    @UiField(provided = true)
    ValueListBox<ValueTypeCode> code;
    @UiField(provided = true)
    ValueListBox<ValueListProxy> regex;
    @UiField(provided = true)
    ValueListBox<ValueTypeProxy> parent;
    @UiField
    CheckBox system;

    private final Driver driver;
    private final BackRequestFactory requestFactory;

    @Inject
    public ValueTypeEditor(final Binder uiBinder, final Driver driver,
                           final BackRequestFactory requestFactory,
                           final ValueListRendererFactory valueListRendererFactory,
                           final SharedMessageBundle messageBundle,
                           final ValueTypeRenderer valueTypeRenderer) {
        this.driver = driver;
        this.requestFactory = requestFactory;
        this.code = new ValueListBox<ValueTypeCode>(new EnumRenderer<ValueTypeCode>());
        this.parent = new ValueListBox<ValueTypeProxy>(valueTypeRenderer);
        this.regex = new ValueListBox<ValueListProxy>(valueListRendererFactory.create(messageBundle.emptyValueList()));

        initWidget(uiBinder.createAndBindUi(this));
        driver.initialize(this);

        code.setAcceptableValues(Arrays.asList(ValueTypeCode.values()));
        code.setValue(ValueTypeCode.REGEXP);
        getParents();
        getRegexp();

        parent.setVisible(false);

        $(code).id("code");
    }

    @Override
    public void edit(ValueTypeProxy valueType) {
        driver.edit(valueType);
    }

    @Override
    public ValueTypeProxy get() {
        ValueTypeProxy valueTypeProxy = driver.flush();
        if (driver.hasErrors()) {
            return null;
        } else {
            valueTypeProxy.setParent(parent.getValue());
            valueTypeProxy.setRegex(regex.getValue());
            return valueTypeProxy;
        }
    }

    private void getParents() {
        requestFactory.valueTypeServiceRequest().findAll().fire(new ReceiverImpl<List<ValueTypeProxy>>() {
            @Override
            public void onSuccess(List<ValueTypeProxy> valueTypeProxies) {
                parent.setAcceptableValues(valueTypeProxies);
            }
        });
    }

    private void getRegexp() {
        requestFactory.valueListServiceRequest().findByValueTypeCode(ValueTypeCode.REGEXP).fire(new ReceiverImpl<List<ValueListProxy>>() {
            @Override
            public void onSuccess(List<ValueListProxy> valueTypeProxies) {
                regex.setAcceptableValues(valueTypeProxies);
            }
        });
    }
}
