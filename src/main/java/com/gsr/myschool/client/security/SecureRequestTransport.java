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

package com.gsr.myschool.client.security;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.Window;
import com.google.web.bindery.requestfactory.gwt.client.DefaultRequestTransport;
import com.google.web.bindery.requestfactory.shared.RequestFactory;
import com.gsr.myschool.client.util.Base64;

import static com.google.gwt.user.client.rpc.RpcRequestBuilder.STRONG_NAME_HEADER;

public class SecureRequestTransport extends DefaultRequestTransport {
    private final SecurityUtils securityUtils;

    public SecureRequestTransport(final SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    @Override
    protected void configureRequestBuilder(RequestBuilder builder) {
        builder.setHeader("Content-Type", RequestFactory.JSON_CONTENT_TYPE_UTF8);
        builder.setHeader("pageurl", Window.Location.getHref());
        builder.setHeader(STRONG_NAME_HEADER, GWT.getPermutationStrongName());

        if (securityUtils.isLoggedIn()) {
            String encodedCredentials = Base64.encode(securityUtils.getUsername() +
                    ":" + securityUtils.getPassword());
            builder.setHeader("Authorization", "Basic " + encodedCredentials);
        }
    }
}
