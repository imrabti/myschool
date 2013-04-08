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
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.back.client.request.SettingsRequest;
import com.gsr.myschool.common.client.mvp.ValidatedView;
import com.gsr.myschool.common.client.proxy.EmailTemplateProxy;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;
import com.gsr.myschool.common.client.widget.messages.CloseDelay;
import com.gsr.myschool.common.client.widget.messages.Message;
import com.gsr.myschool.common.client.widget.messages.event.MessageEvent;
import com.gsr.myschool.common.shared.type.EmailType;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;

public class EmailTemplatePresenter extends PresenterWidget<EmailTemplatePresenter.MyView>
        implements EmailTemplateUiHandlers {
    public interface MyView extends ValidatedView, HasUiHandlers<EmailTemplateUiHandlers> {
        void setData(EmailTemplateProxy email);
    }

    private final BackRequestFactory requestFactory;
    private final SharedMessageBundle messageBundle;

    private SettingsRequest currentContext;
    private EmailTemplateProxy currentTemplate;

    @Inject
    public EmailTemplatePresenter(final EventBus eventBus, final MyView view,
                                  final BackRequestFactory requestFactory,
                                  final SharedMessageBundle messageBundle) {
        super(eventBus, view);

        this.requestFactory = requestFactory;
        this.messageBundle = messageBundle;

        getView().setUiHandlers(this);
    }

    @Override
    public void loadTemplate(EmailType code) {
        requestFactory.settingsService().findEmailTemplateByCode(code).fire(new ReceiverImpl<EmailTemplateProxy>() {
            @Override
            public void onSuccess(EmailTemplateProxy result) {
                currentContext = requestFactory.settingsService();
                currentTemplate = currentContext.edit(result);
                getView().setData(currentTemplate);
            }
        });
    }

    @Override
    public void updateTemplate(EmailTemplateProxy templateProxy) {
        currentContext.updateTemplateEmail(templateProxy).fire(new ReceiverImpl<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                String result = aBoolean ? messageBundle.operationSuccess() : messageBundle.operationFailure();
                AlertType alert = aBoolean ? AlertType.SUCCESS : AlertType.ERROR;
                Message message = new Message.Builder(result)
                        .style(alert).closeDelay(CloseDelay.DEFAULT).build();
                MessageEvent.fire(this, message);

                loadTemplate(currentTemplate.getCode());
            }
        });
    }

    @Override
    protected void onReveal() {
        loadTemplate(EmailType.REGISTRATION);
        getView().clearErrors();
    }
}
