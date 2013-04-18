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

package com.gsr.myschool.back.client.web.application.settings.widget;

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.mvp.ValidatedViewWithUiHandlers;
import com.gsr.myschool.common.client.proxy.EmailTemplateProxy;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;
import com.gsr.myschool.common.client.resource.style.DetailsListStyle;
import com.gsr.myschool.common.client.ui.EmailTemplateEditor;
import com.gsr.myschool.common.client.widget.EmptyResult;
import com.gsr.myschool.common.client.widget.ValidationErrorPopup;
import com.gsr.myschool.common.shared.type.EmailType;

import java.util.Arrays;

public class EmailTemplateView extends ValidatedViewWithUiHandlers<EmailTemplateUiHandlers>
        implements EmailTemplatePresenter.MyView {
    public interface Binder extends UiBinder<Widget, EmailTemplateView> {
    }

    @UiField(provided = true)
    EmailTemplateEditor emailTemplateEditor;
    @UiField(provided = true)
    CellList<EmailType> templates;

    private final ListDataProvider<EmailType> dataProvider;
    private final SingleSelectionModel<EmailType> selectionModel;

    @Inject
    public EmailTemplateView(final Binder uiBinder, final ValidationErrorPopup errorPopup,
                             final SharedMessageBundle sharedMessageBundle,
                             final EmailTemplateEditor emailTemplateEditor,
                             final DetailsListStyle listStyle) {
        super(errorPopup);

        this.emailTemplateEditor = emailTemplateEditor;
        this.dataProvider = new ListDataProvider<EmailType>(Arrays.asList(EmailType.values()));
        this.templates = new CellList<EmailType>(new AbstractCell<EmailType>() {
            @Override
            public void render(Context context, EmailType emailType, SafeHtmlBuilder safeHtmlBuilder) {
                safeHtmlBuilder.appendEscaped(emailType.toString());
            }
        }, listStyle);

        initWidget(uiBinder.createAndBindUi(this));

        dataProvider.addDataDisplay(templates);

        selectionModel = new SingleSelectionModel<EmailType>();
        templates.setSelectionModel(selectionModel);
        templates.getSelectionModel().addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                EmailType code = selectionModel.getSelectedObject();
                if (code != null)
                    getUiHandlers().loadTemplate(code);
            }
        });
        templates.setEmptyListWidget(new EmptyResult(sharedMessageBundle.noResultFound(), AlertType.WARNING));
        selectionModel.setSelected(EmailType.REGISTRATION, true);
    }

    @Override
    public void setData(EmailTemplateProxy template) {
        emailTemplateEditor.edit(template);
    }

    @UiHandler("updateTemplate")
    void onUpdateClicker(ClickEvent event) {
        getUiHandlers().updateTemplate(emailTemplateEditor.get());
    }
}
