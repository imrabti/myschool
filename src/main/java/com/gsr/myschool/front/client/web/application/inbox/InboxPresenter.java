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

package com.gsr.myschool.front.client.web.application.inbox;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.security.LoggedInGatekeeper;
import com.gsr.myschool.front.client.place.NameTokens;
import com.gsr.myschool.front.client.web.application.ApplicationPresenter;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

public class InboxPresenter extends Presenter<InboxPresenter.MyView, InboxPresenter.MyProxy>
        implements InboxUiHandlers {
    public interface MyView extends View, HasUiHandlers<InboxUiHandlers> {
    }

    @ProxyStandard
    @NameToken(NameTokens.inbox)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface MyProxy extends ProxyPlace<InboxPresenter> {
    }

    @Inject
    public InboxPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy) {
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);

        getView().setUiHandlers(this);
    }
}
