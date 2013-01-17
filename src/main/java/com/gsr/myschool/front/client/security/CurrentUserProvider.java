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

package com.gsr.myschool.front.client.security;

import com.gsr.myschool.front.client.request.FrontRequestFactory;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.proxy.UserProxy;
import com.gsr.myschool.common.client.util.CallbackImpl;

import javax.inject.Inject;

public class CurrentUserProvider {
    private final FrontRequestFactory requestFactory;

    private UserProxy currentUser;

    @Inject
    public CurrentUserProvider(final FrontRequestFactory requestFactory) {
        this.requestFactory = requestFactory;
    }

    public void load(final CallbackImpl<UserProxy> callback) {
        requestFactory.authenticationService().currentUser().fire(new ReceiverImpl<UserProxy>() {
            @Override
            public void onSuccess(UserProxy userProxy) {
                currentUser = userProxy;
                callback.onSuccess(userProxy);
            }
        });
    }

    public UserProxy get() {
        return currentUser;
    }
}
