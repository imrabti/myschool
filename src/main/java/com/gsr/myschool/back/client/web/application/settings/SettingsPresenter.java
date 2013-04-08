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

package com.gsr.myschool.back.client.web.application.settings;

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.place.NameTokens;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.back.client.resource.message.MessageBundle;
import com.gsr.myschool.back.client.web.application.ApplicationPresenter;
import com.gsr.myschool.back.client.web.application.settings.popup.AddFilierePresenter;
import com.gsr.myschool.back.client.web.application.settings.popup.AddNiveauEtudePresenter;
import com.gsr.myschool.back.client.web.application.settings.widget.MatiereExamenPresenter;
import com.gsr.myschool.back.client.web.application.settings.widget.PiecesJustifPresenter;
import com.gsr.myschool.back.client.web.application.settings.widget.SystemScolairePresenter;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.security.HasRoleGatekeeper;
import com.gsr.myschool.common.client.widget.messages.CloseDelay;
import com.gsr.myschool.common.client.widget.messages.Message;
import com.gsr.myschool.common.client.widget.messages.event.MessageEvent;
import com.gsr.myschool.common.shared.constants.GlobalParameters;
import com.gsr.myschool.common.shared.type.SettingsKey;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.GatekeeperParams;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

public class SettingsPresenter extends Presenter<SettingsPresenter.MyView, SettingsPresenter.MyProxy>
        implements SettingsUiHandlers {
    public interface MyView extends View, HasUiHandlers<SettingsUiHandlers> {
        void setActivate(Boolean bool);

        void setActivateGeneral(Boolean bool);

        void setDateLimite(String dateLimite);
    }

    @ProxyStandard
    @NameToken(NameTokens.generalSettings)
    @UseGatekeeper(HasRoleGatekeeper.class)
    @GatekeeperParams({GlobalParameters.ROLE_ADMIN})
    public interface MyProxy extends ProxyPlace<SettingsPresenter> {
    }

    public static final Object TYPE_SetSystemScolaireContent = new Object();
    public static final Object TYPE_SetMatiereContent = new Object();
    public static final Object TYPE_SetPiecesJustificativesContent = new Object();

    private final BackRequestFactory requestFactory;
    private final MessageBundle messageBundle;
    private final MatiereExamenPresenter matiereExamenPresenter;
    private final PiecesJustifPresenter piecesJustifPresenter;
    private final SystemScolairePresenter systemScolairePresenter;
    private final AddFilierePresenter addFilierePresenter;
    private final AddNiveauEtudePresenter addNiveauEtudePresenter;

    @Inject
    public SettingsPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
                             final BackRequestFactory requestFactory,
                             final MessageBundle messageBundle,
                             final SystemScolairePresenter systemScolairePresenter,
                             final AddFilierePresenter addFilierePresenter,
                             final AddNiveauEtudePresenter addNiveauEtudePresenter,                              final PiecesJustifPresenter piecesJustifPresenter,
                             final MatiereExamenPresenter matiereExamenPresenter) {
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);

        this.requestFactory = requestFactory;
        this.messageBundle = messageBundle;
        this.systemScolairePresenter = systemScolairePresenter;
        this.addFilierePresenter = addFilierePresenter;
        this.addNiveauEtudePresenter = addNiveauEtudePresenter;
        this.matiereExamenPresenter = matiereExamenPresenter;
        this.piecesJustifPresenter = piecesJustifPresenter;

        getView().setUiHandlers(this);
    }

    @Override
    public void desactivateInscriptions() {
        requestFactory.settingsService().updateSettings(SettingsKey.STATUS, GlobalParameters.APP_STATUS_CLOSED)
                .fire(new ReceiverImpl<Void>() {
                    @Override
                    public void onSuccess(Void response) {
                        Message message = new Message.Builder(messageBundle.closeInscriptionSuccess())
                                .style(AlertType.SUCCESS)
                                .closeDelay(CloseDelay.DEFAULT)
                                .build();
                        MessageEvent.fire(this, message);
                        getView().setActivate(false);
                    }
                });
    }

    @Override
    public void activateInscriptions() {
        requestFactory.settingsService().updateSettings(SettingsKey.STATUS, GlobalParameters.APP_STATUS_OPENED)
                .fire(new ReceiverImpl<Void>() {
                    @Override
                    public void onSuccess(Void response) {
                        Message message = new Message.Builder(messageBundle.openInscriptionSuccess())
                                .style(AlertType.SUCCESS)
                                .closeDelay(CloseDelay.DEFAULT)
                                .build();
                        MessageEvent.fire(this, message);
                        getView().setActivate(true);
                    }
                });
    }

    @Override
    public void desactivateGenaralFilieres() {
        requestFactory.settingsService().updateSettings(SettingsKey.FILIERE_GENERAL_CLOSED, GlobalParameters.APP_STATUS_CLOSED)
                .fire(new ReceiverImpl<Void>() {
                    @Override
                    public void onSuccess(Void response) {
                        Message message = new Message.Builder(messageBundle.desactivateGeneralFiliereSuccess())
                                .style(AlertType.SUCCESS)
                                .closeDelay(CloseDelay.DEFAULT)
                                .build();
                        MessageEvent.fire(this, message);
                        getView().setActivateGeneral(false);
                    }
                });
    }


    @Override
    public void activateGenaralFilieres() {
        requestFactory.settingsService().updateSettings(SettingsKey.FILIERE_GENERAL_CLOSED, GlobalParameters.APP_STATUS_OPENED)
                .fire(new ReceiverImpl<Void>() {
                    @Override
                    public void onSuccess(Void response) {
                        Message message = new Message.Builder(messageBundle.activateGeneralFiliereSuccess())
                                .style(AlertType.SUCCESS)
                                .closeDelay(CloseDelay.DEFAULT)
                                .build();
                        MessageEvent.fire(this, message);
                        getView().setActivateGeneral(true);
                    }
                });
    }

    @Override
    public void updateDateLimit(String value) {
        requestFactory.settingsService().updateSettings(SettingsKey.DATE_LIMITE, value).fire(new ReceiverImpl<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Message message = new Message.Builder(messageBundle.dateLimiteUpdatedSuccess())
                        .style(AlertType.SUCCESS)
                        .closeDelay(CloseDelay.DEFAULT)
                        .build();
                MessageEvent.fire(this, message);
            }
        });
    }

    @Override
    public void addFiliere() {
        addToPopupSlot(addFilierePresenter);
    }

    @Override
    public void addNiveauEtude() {
        addToPopupSlot(addNiveauEtudePresenter);
    }

    @Override
    protected void onReveal() {
        requestFactory.settingsService().getSetting(SettingsKey.STATUS).fire(new ReceiverImpl<String>() {
            @Override
            public void onSuccess(String response) {
                getView().setActivate(GlobalParameters.APP_STATUS_OPENED.equals(response));
            }
        });

        requestFactory.settingsService().getSetting(SettingsKey.FILIERE_GENERAL_CLOSED).fire(new ReceiverImpl<String>() {
            @Override
            public void onSuccess(String response) {
                getView().setActivateGeneral(GlobalParameters.APP_STATUS_OPENED.equals(response));
            }
        });

        requestFactory.settingsService().getSetting(SettingsKey.DATE_LIMITE).fire(new ReceiverImpl<String>() {
            @Override
            public void onSuccess(String response) {
                getView().setDateLimite(response);
            }
        });

        setInSlot(TYPE_SetSystemScolaireContent, systemScolairePresenter);
        setInSlot(TYPE_SetMatiereContent, matiereExamenPresenter);
        setInSlot(TYPE_SetPiecesJustificativesContent, piecesJustifPresenter);
    }
}
