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
import com.gsr.myschool.back.client.resource.message.MessageBundle;
import com.gsr.myschool.common.client.mvp.ValidatedView;
import com.gsr.myschool.common.client.proxy.MatiereExamenProxy;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.request.ValidatedReceiverImpl;
import com.gsr.myschool.common.client.widget.messages.CloseDelay;
import com.gsr.myschool.common.client.widget.messages.Message;
import com.gsr.myschool.common.client.widget.messages.event.MessageEvent;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;

public class MatiereExamenPresenter extends PresenterWidget<MatiereExamenPresenter.MyView>
        implements MatiereExamenUiHandlers {
    public interface MyView extends ValidatedView, HasUiHandlers<MatiereExamenUiHandlers> {
        void setData(List<MatiereExamenProxy> data);

        void editMatiereExamen(MatiereExamenProxy matiere);
    }

    private final BackRequestFactory requestFactory;
    private final MessageBundle messageBundle;

    private SettingsRequest currentContext;
    private MatiereExamenProxy currentPiece;
    private Boolean matiereViolation;

    @Inject
    public MatiereExamenPresenter(final EventBus eventBus, final MyView view,
                                  final BackRequestFactory requestFactory,
                                  final MessageBundle messageBundle) {
        super(eventBus, view);

        this.requestFactory = requestFactory;
        this.messageBundle = messageBundle;

        getView().setUiHandlers(this);
    }

    @Override
    public void addMatiereExamen(MatiereExamenProxy matiere) {
        if (!matiereViolation) {
            currentContext.addMatiereExamen(matiere).fire(new ValidatedReceiverImpl<Boolean>() {
                @Override
                public void onValidationError(Set<ConstraintViolation<?>> violations) {
                    getView().clearErrors();
                    getView().showErrors(violations);
                    matiereViolation = true;
                }

                @Override
                public void onSuccess(Boolean response) {
                    currentContext = requestFactory.settingsService();
                    currentPiece = currentContext.create(MatiereExamenProxy.class);
                    matiereViolation = false;

                    getView().editMatiereExamen(currentPiece);
                    getView().clearErrors();

                    String result = response ? messageBundle.addMatiereExamenSuccess() : messageBundle.addMatiereExamenFailure();
                    AlertType alert = response ? AlertType.SUCCESS : AlertType.ERROR;

                    Message message = new Message.Builder(result)
                            .style(alert)
                            .closeDelay(CloseDelay.DEFAULT)
                            .build();
                    MessageEvent.fire(this, message);

                    loadMatiereExamen();
                }
            });
        } else {
            currentContext.fire();
        }
    }

    @Override
    public void deleteMatiereExamen(MatiereExamenProxy matiere) {
        requestFactory.settingsService().deleteMatiereExamen(matiere).fire(new ReceiverImpl<Boolean>() {
            @Override
            public void onSuccess(Boolean response) {
                String result = response ? messageBundle.deleteMatiereExamenSuccess() : messageBundle.deleteMatiereExamenFailure();
                AlertType alert = response ? AlertType.SUCCESS : AlertType.ERROR;
                Message message = new Message.Builder(result)
                        .style(alert).closeDelay(CloseDelay.DEFAULT).build();
                MessageEvent.fire(this, message);
                loadMatiereExamen();
            }
        });
    }

    @Override
    protected void onReveal() {
        currentContext = requestFactory.settingsService();
        currentPiece = currentContext.create(MatiereExamenProxy.class);
        matiereViolation = false;

        getView().editMatiereExamen(currentPiece);
        getView().clearErrors();
        loadMatiereExamen();
    }

    private void loadMatiereExamen() {
        requestFactory.settingsService().findAllMatiereExamen().fire(new ReceiverImpl<List<MatiereExamenProxy>>() {
            @Override
            public void onSuccess(List<MatiereExamenProxy> result) {
                getView().setData(result);
            }
        });
    }
}
