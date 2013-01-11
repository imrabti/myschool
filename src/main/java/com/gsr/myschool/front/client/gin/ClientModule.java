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

package com.gsr.myschool.front.client.gin;

import com.gsr.myschool.common.client.security.CurrentUserProvider;
import com.gsr.myschool.front.client.web.RootModule;
import com.gsr.myschool.common.client.event.EventSourceRequestTransport;
import com.gsr.myschool.front.client.place.DefaultPlace;
import com.gsr.myschool.front.client.place.NameTokens;
import com.gsr.myschool.front.client.place.PlaceManager;
import com.gsr.myschool.front.client.request.MyRequestFactory;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.request.ValidatedReceiverImpl;
import com.gsr.myschool.front.client.resource.Resources;
import com.gsr.myschool.front.client.resource.message.MessageBundle;
import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;
import com.gsr.myschool.common.client.security.HasRoleGatekeeper;
import com.gsr.myschool.common.client.security.LoggedInGatekeeper;
import com.gsr.myschool.common.client.security.SecurityUtils;

public class ClientModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        install(new DefaultModule(PlaceManager.class));
        install(new RootModule());

        bind(MyRequestFactory.class).toProvider(RequestFactoryProvider.class).in(Singleton.class);
        bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.home);

        bind(Resources.class).in(Singleton.class);
        bind(MessageBundle.class).in(Singleton.class);
        bind(SecurityUtils.class).in(Singleton.class);
        bind(CurrentUserProvider.class).in(Singleton.class);

        bind(LoggedInGatekeeper.class).in(Singleton.class);
        bind(HasRoleGatekeeper.class).in(Singleton.class);

        requestStaticInjection(ReceiverImpl.class);
        requestStaticInjection(ValidatedReceiverImpl.class);
    }

    static class RequestFactoryProvider implements Provider<MyRequestFactory> {
        private final MyRequestFactory requestFactory;

        @Inject
        public RequestFactoryProvider(EventBus eventBus, SecurityUtils securityUtils) {
            requestFactory = GWT.create(MyRequestFactory.class);
            requestFactory.initialize(eventBus, new EventSourceRequestTransport(eventBus, securityUtils));
        }

        public MyRequestFactory get() {
            return requestFactory;
        }
    }
}
