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

package com.gsr.myschool.back.client.web.application;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.place.NameTokens;
import com.gsr.myschool.back.client.web.RootPresenter;
import com.gsr.myschool.back.client.web.application.widget.header.HeaderPresenter;
import com.gsr.myschool.back.client.web.application.widget.sider.MenuPresenter;
import com.gsr.myschool.common.client.event.RequestEvent;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

public class ApplicationPresenter extends Presenter<ApplicationPresenter.MyView, ApplicationPresenter.MyProxy>
		implements RequestEvent.RequestEventHandler, ApplicationUiHandlers {
    public interface MyView extends View, HasUiHandlers<ApplicationUiHandlers> {
        public void showAjaxLoader(int timeout);

        public void hideAjaxLoader();
    }

    @ProxyStandard
    @NameToken(NameTokens.administration)
    public interface MyProxy extends ProxyPlace<ApplicationPresenter> {
    }

    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_SetMainContent = new GwtEvent.Type<RevealContentHandler<?>>();
    public static final Object TYPE_SetHeaderContent = new Object();
    public static final Object TYPE_SetSiderContent = new Object();

    private static final int LOADING_TIMEOUT = 250;

    private final HeaderPresenter headerPresenter;
    private final MenuPresenter menuPresenter;

    @Inject
    public ApplicationPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
                                final HeaderPresenter headerPresenter, final MenuPresenter menuPresenter) {
        super(eventBus, view, proxy, RootPresenter.TYPE_SetMainContent);

        this.headerPresenter = headerPresenter;
        this.menuPresenter = menuPresenter;

        getView().setUiHandlers(this);
    }

    @Override
    public void onRequestEvent(RequestEvent requestEvent) {
        if (requestEvent.getState() == RequestEvent.State.SENT) {
            getView().showAjaxLoader(LOADING_TIMEOUT);
        } else {
            getView().hideAjaxLoader();
        }
    }

    @Override
    protected void onBind() {
        addRegisteredHandler(RequestEvent.getType(), this);
    }

    @Override
    protected void onReveal() {
        setInSlot(TYPE_SetHeaderContent, headerPresenter);
        setInSlot(TYPE_SetSiderContent, menuPresenter);
    }
}
