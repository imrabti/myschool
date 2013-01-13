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

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.front.client.place.NameTokens;
import com.gsr.myschool.front.client.web.application.widget.sider.FrontMenuView.MenuItem;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class FrontMenuPresenter extends PresenterWidget<FrontMenuPresenter.MyView> implements FrontMenuUiHandlers {
    public interface MyView extends View, HasUiHandlers<FrontMenuUiHandlers> {
        void setSelectedMenu(MenuItem currentMenu);
    }

    private final PlaceManager placeManager;

    @Inject
    public FrontMenuPresenter(EventBus eventBus, MyView view, final PlaceManager placeManager) {
        super(eventBus, view);

        this.placeManager = placeManager;

        getView().setUiHandlers(this);
    }

    @Override
    public void onMenuChanged(MenuItem selectedMenu) {
        switch (selectedMenu) {
            case INSCRIPTION:
                placeManager.revealPlace(new PlaceRequest(NameTokens.getInscription()));
                break;
            case CONVOCATION:
                placeManager.revealPlace(new PlaceRequest(NameTokens.getConvocation()));
                break;
            case INBOX:
                placeManager.revealPlace(new PlaceRequest(NameTokens.getInbox()));
                break;
        }
    }

    @Override
    protected void onReveal() {
        PlaceRequest currentPlace =  placeManager.getCurrentPlaceRequest();
        MenuItem currentMenu = MenuItem.INSCRIPTION;

        if (currentPlace.matchesNameToken(NameTokens.getInscription())) {
            currentMenu = MenuItem.INSCRIPTION;
        } else if (currentPlace.matchesNameToken(NameTokens.getConvocation())) {
            currentMenu = MenuItem.CONVOCATION;
        } else if (currentPlace.matchesNameToken(NameTokens.getInbox())) {
            currentMenu = MenuItem.INBOX;
        }

        getView().setSelectedMenu(currentMenu);
    }
}
