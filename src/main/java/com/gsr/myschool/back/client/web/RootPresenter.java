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

package com.gsr.myschool.back.client.web;

import com.google.gwt.event.shared.GwtEvent;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gsr.myschool.common.client.widget.messages.MessagePresenter;

public class RootPresenter extends Presenter<RootPresenter.MyView, RootPresenter.MyProxy> {
    public interface MyView extends View {
    }

    @ProxyStandard
    public interface MyProxy extends Proxy<RootPresenter> {
    }

    @ContentSlot
    public static final GwtEvent.Type<RevealContentHandler<?>> TYPE_SetMainContent = new GwtEvent.Type<RevealContentHandler<?>>();
    public static final Object TYPE_SetMessageContent = new Object();

    private final MessagePresenter messagePresenter;

    @Inject
    public RootPresenter(final EventBus eventBus, final MyView view,
                         final MyProxy proxy, final MessagePresenter messagePresenter) {
        super(eventBus, view, proxy, RevealType.RootLayout);

        this.messagePresenter = messagePresenter;
    }

    @Override
    protected void onReveal() {
        setInSlot(TYPE_SetMessageContent, messagePresenter);
    }
}