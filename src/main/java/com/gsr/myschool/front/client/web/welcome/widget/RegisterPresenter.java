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

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.mvp.ValidatedView;
import com.gsr.myschool.common.client.proxy.UserProxy;
import com.gsr.myschool.common.client.request.ValidatedReceiverImpl;
import com.gsr.myschool.common.client.util.URLUtils;
import com.gsr.myschool.common.client.widget.messages.Message;
import com.gsr.myschool.common.client.widget.messages.event.MessageEvent;
import com.gsr.myschool.front.client.place.NameTokens;
import com.gsr.myschool.front.client.request.FrontRequestFactory;
import com.gsr.myschool.front.client.request.RegistrationRequest;
import com.gsr.myschool.front.client.resource.message.MessageBundle;
import com.gsr.myschool.front.client.web.welcome.popup.ResendmailPresenter;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class RegisterPresenter extends PresenterWidget<RegisterPresenter.MyView> implements RegisterUiHandlers {
    public interface MyView extends ValidatedView, HasUiHandlers<RegisterUiHandlers> {
        void edit(UserProxy userProxy);
    }

    private final FrontRequestFactory requestFactory;
    private final MessageBundle messageBundle;
    private final ResendmailPresenter resendmailPresenter;

    private RegistrationRequest currentContext;
    private Boolean registerViolation;

    @Inject
    public RegisterPresenter(final EventBus eventBus, final MyView view,
                             final FrontRequestFactory requestFactory,
                             final MessageBundle messageBundle,
                             final ResendmailPresenter resendmailPresenter) {
        super(eventBus, view);

        this.messageBundle = messageBundle;
        this.requestFactory = requestFactory;
        this.resendmailPresenter = resendmailPresenter;

        getView().setUiHandlers(this);
    }

    @Override
    public void register(UserProxy user) {
        if (!registerViolation) {
            String confirmationLink = URLUtils.generateURL(NameTokens.getWelcome());
            currentContext.register(user, confirmationLink).fire(new ValidatedReceiverImpl<Boolean>() {
                @Override
                public void onValidationError(Set<ConstraintViolation<?>> violations) {
                    registerViolation = true;
                    getView().clearErrors();
                    getView().showErrors(violations);
                }

                @Override
                public void onSuccess(Boolean aBoolean) {
                    registerViolation = false;
                    getView().clearErrors();

                    String messageString = aBoolean ? messageBundle.registerSuccess() : messageBundle.registerFailure();
                    AlertType alertType = aBoolean ? AlertType.SUCCESS : AlertType.ERROR;
                    Message message = new Message.Builder(messageString).style(alertType).build();
                    MessageEvent.fire(this, message);

                    currentContext = requestFactory.registrationService();
                    getView().edit(currentContext.create(UserProxy.class));
                }
            });
        } else {
            currentContext.fire();
        }
    }

    @Override
    public void resendMail() {
        addToPopupSlot(resendmailPresenter);
    }

    protected void onReveal() {
        currentContext = requestFactory.registrationService();
        registerViolation = false;
        getView().edit(currentContext.create(UserProxy.class));
    }
}
