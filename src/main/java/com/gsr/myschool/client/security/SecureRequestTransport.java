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
