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

package com.gsr.myschool.common.client.security;

import com.google.inject.Inject;
import com.gwtplatform.mvp.client.proxy.GatekeeperWithParams;

public class HasRoleGatekeeper implements GatekeeperWithParams {
    private final SecurityUtils securityUtils;

    private String[] requiredRoles;

    @Inject
    public HasRoleGatekeeper(final SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    @Override
    public GatekeeperWithParams withParams(String[] params) {
        requiredRoles = params;
        return this;
    }

    @Override
    public boolean canReveal() {
        for (String role : requiredRoles) {
            if (securityUtils.hasAuthority(role)) {
                return true;
            }
        }
        return false;
    }
}
