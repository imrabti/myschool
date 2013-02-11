package com.gsr.myschool.common.client.util;

import com.google.gwt.core.client.GWT;

public class URLUtils {
    private static final String GWT_DEV_MOD = "?gwt.codesvr=127.0.0.1:9997";

    public static String generateURL(String hostPage, String placeToken) {
        StringBuilder builder = new StringBuilder();
        builder.append(GWT.getHostPageBaseURL());
        builder.append(hostPage);

        if (!GWT.isScript()) {
            builder.append(GWT_DEV_MOD);
        }

        builder.append("#" + placeToken);

        return builder.toString();
    }

    public static String generateURL(String placeToken) {
        StringBuilder builder = new StringBuilder();
        builder.append(GWT.getHostPageBaseURL());

        if (!GWT.isScript()) {
            builder.append(GWT_DEV_MOD);
        }

        builder.append("#" + placeToken);

        return builder.toString();
    }
}
