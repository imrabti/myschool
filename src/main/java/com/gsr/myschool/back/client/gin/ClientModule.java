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

package com.gsr.myschool.back.client.gin;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.place.DefaultPlace;
import com.gsr.myschool.back.client.place.NameTokens;
import com.gsr.myschool.back.client.place.PlaceManager;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.back.client.resource.AdminResources;
import com.gsr.myschool.back.client.resource.message.MessageBundle;
import com.gsr.myschool.back.client.security.CurrentUserProvider;
import com.gsr.myschool.back.client.util.ValueListImpl;
import com.gsr.myschool.back.client.web.RootModule;
import com.gsr.myschool.common.client.CommonModule;
import com.gsr.myschool.common.client.event.EventSourceRequestTransport;
import com.gsr.myschool.common.client.request.RequestUrl;
import com.gsr.myschool.common.client.security.SecurityUtils;
import com.gsr.myschool.common.client.util.ValueList;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;

public class ClientModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        install(new DefaultModule(PlaceManager.class));
        install(new CommonModule());
        install(new RootModule());

        bind(BackRequestFactory.class).toProvider(RequestFactoryProvider.class).in(Singleton.class);
        bind(ValueList.class).to(ValueListImpl.class).in(Singleton.class);
        bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.login);
        bindConstant().annotatedWith(RequestUrl.class).to(NameTokens.requestUrl);

        bind(AdminResources.class).in(Singleton.class);
        bind(MessageBundle.class).in(Singleton.class);
        bind(SecurityUtils.class).in(Singleton.class);
        bind(CurrentUserProvider.class).in(Singleton.class);
    }

    static class RequestFactoryProvider implements Provider<BackRequestFactory> {
        private final BackRequestFactory requestFactory;

        @Inject
        public RequestFactoryProvider(EventBus eventBus, SecurityUtils securityUtils, @RequestUrl String requestUrl) {
            requestFactory = GWT.create(BackRequestFactory.class);
            requestFactory.initialize(eventBus, new EventSourceRequestTransport(eventBus, securityUtils, requestUrl));
        }

        public BackRequestFactory get() {
            return requestFactory;
        }
    }
}
