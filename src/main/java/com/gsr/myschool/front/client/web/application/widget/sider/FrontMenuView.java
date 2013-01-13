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

package com.gsr.myschool.front.client.web.application.widget.sider;

import com.github.gwtbootstrap.client.ui.NavLink;
import com.github.gwtbootstrap.client.ui.NavWidget;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.mvp.ViewWithUiHandlers;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;

public class FrontMenuView extends ViewWithUiHandlers<FrontMenuUiHandlers> implements FrontMenuPresenter.MyView {
    public interface Binder extends UiBinder<Widget, FrontMenuView> {
    }

    public enum MenuItem {
        INSCRIPTION, CONVOCATION, INBOX;
    }

    @UiField
    NavLink inscription;
    @UiField
    NavLink convocation;
    @UiField
    NavWidget inbox;

    private MenuItem currentMenu;

    @Inject
    public FrontMenuView(final Binder uiBinder, UiHandlersStrategy<FrontMenuUiHandlers> uiHandlers) {
        super(uiHandlers);
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setSelectedMenu(MenuItem currentMenu) {
        this.currentMenu = currentMenu;
        clearActive();

        switch (currentMenu) {
            case INSCRIPTION:
                inscription.setActive(true);
                break;
            case CONVOCATION:
                convocation.setActive(true);
                break;
            case INBOX:
                inbox.setActive(true);
                break;
        }
    }

    @UiHandler("inscription")
    void onInscriptionClicked(ClickEvent event) {
        if (currentMenu != MenuItem.INSCRIPTION) {
            clearActive();
            inscription.setActive(true);
            currentMenu = MenuItem.INSCRIPTION;
            getUiHandlers().onMenuChanged(currentMenu);
        }
    }

    @UiHandler("convocation")
    void onConvocationClicked(ClickEvent event) {
        if (currentMenu != MenuItem.CONVOCATION) {
            clearActive();
            convocation.setActive(true);
            currentMenu = MenuItem.CONVOCATION;
            getUiHandlers().onMenuChanged(currentMenu);
        }
    }

    @UiHandler("inbox")
    void onInboxClicked(ClickEvent event) {
        if (currentMenu != MenuItem.INBOX) {
            clearActive();
            inbox.setActive(true);
            currentMenu = MenuItem.INBOX;
            getUiHandlers().onMenuChanged(currentMenu);
        }
    }

    private void clearActive() {
        inscription.setActive(false);
        convocation.setActive(false);
        inbox.setActive(false);
    }
}
