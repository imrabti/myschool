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

package com.gsr.myschool.back.client.security;

import com.google.inject.Inject;
import com.gsr.myschool.back.client.request.proxy.AdminUserProxy;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.util.CallbackImpl;
import com.gsr.myschool.back.client.request.BackRequestFactory;

public class CurrentUserProvider {
    private final BackRequestFactory requestFactory;

	private AdminUserProxy currentAdmin;

    @Inject
    public CurrentUserProvider(final BackRequestFactory requestFactory) {
        this.requestFactory = requestFactory;
    }

	public void load(final CallbackImpl<AdminUserProxy> callback) {
		requestFactory.adminAuthenticationService().currentUser().fire(new ReceiverImpl<AdminUserProxy>() {
			@Override
			public void onSuccess(AdminUserProxy userProxy) {
				currentAdmin = userProxy;
				callback.onSuccess(userProxy);
			}
		});
	}

	public AdminUserProxy get() {
		return currentAdmin;
	}
}
