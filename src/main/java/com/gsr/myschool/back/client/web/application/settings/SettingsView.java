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

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.DropdownButton;
import com.github.gwtbootstrap.client.ui.TextArea;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.ValuePicker;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.resource.style.TabsListStyle;
import com.gsr.myschool.common.client.widget.renderer.EnumCell;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import java.util.Arrays;

public class SettingsView extends ViewWithUiHandlers<SettingsUiHandlers> implements SettingsPresenter.MyView {
    public interface Binder extends UiBinder<Widget, SettingsView> {
    }

    public enum SettingsType {
        GLOBAL("Configuration globale"),
        SYSTEME_SCOLAIRE("Système scolaire"),
        MATIERES("Matières"),
        PIECES_JUSTIFICATIVES("Pièces justificatives"),
        TEMPLATE_EMAIL("Templates email");

        private String label;

        private SettingsType(String label) {
            this.label = label;
        }

        public String toString() {
            return label;
        }
    }

    @UiField(provided = true)
    ValuePicker<SettingsType> tabs;
    @UiField
    DeckPanel settingsIndexedPanel;
    @UiField
    SimplePanel systemeScolaireSettings;
    @UiField
    SimplePanel matieresSettings;
    @UiField
    SimplePanel piecesJustificativesSettings;
    @UiField
    SimplePanel emailTemplatesSettings;
    @UiField
    Button activate;
    @UiField
    Button desactivate;
    @UiField
    Button activateGeneralFiliere;
    @UiField
    Button desactivateGeneralFiliere;
    @UiField
    DropdownButton addSystemScolaire;
    @UiField
    TextArea dateLimite;

    @Inject
    public SettingsView(final Binder uiBinder, final TabsListStyle listStyle) {
        CellList<SettingsType> tabsCell = new CellList<SettingsType>(new EnumCell<SettingsType>(), listStyle);
        tabs = new ValuePicker<SettingsType>(tabsCell);

        initWidget(uiBinder.createAndBindUi(this));

        tabs.setAcceptableValues(Arrays.asList(SettingsType.values()));
        tabs.setValue(SettingsType.GLOBAL, false);
        settingsIndexedPanel.showWidget(SettingsType.GLOBAL.ordinal());
    }

    @Override
    public void setInSlot(Object slot, IsWidget content) {
        if (content != null) {
            if (slot == SettingsPresenter.TYPE_SetSystemScolaireContent) {
                systemeScolaireSettings.setWidget(content);
            } else if (slot == SettingsPresenter.TYPE_SetMatiereContent) {
                matieresSettings.setWidget(content);
            } else if (slot == SettingsPresenter.TYPE_SetPiecesJustificativesContent) {
                piecesJustificativesSettings.setWidget(content);
            } else if (slot == SettingsPresenter.TYPE_SetEmailTemplateContent) {
                emailTemplatesSettings.setWidget(content);
            }
        }
    }

    @Override
    public void setActivate(Boolean bool) {
        activate.setEnabled(!bool);
        desactivate.setEnabled(bool);
    }

    @Override
    public void setActivateGeneral(Boolean bool) {
        activateGeneralFiliere.setEnabled(!bool);
        desactivateGeneralFiliere.setEnabled(bool);
    }

    @Override
    public void setDateLimite(String value) {
        dateLimite.setText(value);
    }

    @UiHandler("tabs")
    void onTabsChanged(ValueChangeEvent<SettingsType> event) {
        settingsIndexedPanel.showWidget(tabs.getValue().ordinal());
        addSystemScolaire.setVisible(tabs.getValue() == SettingsType.SYSTEME_SCOLAIRE);
    }

    @UiHandler("activate")
    void onActivateClicked(ClickEvent event) {
        getUiHandlers().activateInscriptions();
    }

    @UiHandler("desactivate")
    void onDesactivateClicked(ClickEvent event) {
        getUiHandlers().desactivateInscriptions();
    }

    @UiHandler("activateGeneralFiliere")
    void onActivateGeneralClicked(ClickEvent event) {
        getUiHandlers().activateGenaralFilieres();
    }

    @UiHandler("desactivateGeneralFiliere")
    void onDesactivateGeneralClicked(ClickEvent event) {
        getUiHandlers().desactivateGenaralFilieres();
    }

    @UiHandler("dateLimite")
    void onDateLimiteBlur(BlurEvent event) {
        getUiHandlers().updateDateLimit(dateLimite.getValue());
    }

    @UiHandler("addFiliere")
    void onAddFiliereClick(ClickEvent event) {
        getUiHandlers().addFiliere();
    }

    @UiHandler("addNiveauEtude")
    void onAddNiveauEtudeClick(ClickEvent event) {
        getUiHandlers().addNiveauEtude();
    }

    @UiHandler("cleanProcess")
    void cleanProcess(ClickEvent event) {
        getUiHandlers().cleanProcess();
    }

    @UiHandler("deleteCreatedPrepaDossier")
    void deleteCreatedPrepaDossier(ClickEvent event) {
        getUiHandlers().deleteCreatedPrepaDossier();
    }
}
