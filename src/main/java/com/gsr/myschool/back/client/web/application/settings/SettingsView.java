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
import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.inject.Inject;
import com.gsr.myschool.back.client.web.application.settings.renderer.NiveauEtudeInfosTreeFactory;
import com.gsr.myschool.common.client.mvp.ViewWithUiHandlers;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gsr.myschool.common.client.proxy.FiliereProxy;
import com.gsr.myschool.common.client.proxy.NiveauEtudeProxy;
import com.gsr.myschool.common.client.util.ValueList;

public class SettingsView extends ViewWithUiHandlers<SettingsUiHandlers> implements SettingsPresenter.MyView {
    public interface Binder extends UiBinder<Widget, SettingsView> {
    }

    @UiField(provided = true)
    CellTree myTree;
    @UiField
    Button activate;
    @UiField
    Button desactivate;

    @Inject
    public SettingsView(final Binder uiBinder, final ValueList valueList,
            final UiHandlersStrategy<SettingsUiHandlers> uiHandlers,
            final NiveauEtudeInfosTreeFactory niveauEtudeInfosTreeFactory) {
        super(uiHandlers);

        myTree = new CellTree(niveauEtudeInfosTreeFactory.create(valueList, setupShowDetails()), null);

        initWidget(uiBinder.createAndBindUi(this));
    }

    public void setActivate(Boolean bool) {
        activate.setEnabled(!bool);
        desactivate.setEnabled(bool);
    }

    @UiHandler("activate")
    void onActivateClicked(ClickEvent event) {
        getUiHandlers().activateInscriptions();
    }

    @UiHandler("desactivate")
    void onDesactivateClicked(ClickEvent event) {
        getUiHandlers().desactivateInscriptions();
    }

    private ActionCell.Delegate<NiveauEtudeProxy> setupShowDetails(){
        return new ActionCell.Delegate<NiveauEtudeProxy>() {
            @Override
            public void execute(NiveauEtudeProxy object) {
                getUiHandlers().showDetails(object);
            }
        };
    }
}
