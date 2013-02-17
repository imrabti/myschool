package com.gsr.myschool.common.client.request;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.Window;

public class ReportRequestBuilder extends RequestBuilder {
    private StringBuffer postData;

    public ReportRequestBuilder() {
        super(GET, "/resource/report");
        setHeader("Content-type", "application/x-www-form-urlencoded");
    }

    public void buildData(String dossierId) {
        postData = new StringBuffer();
        postData.append("dossierId=" + dossierId);
    }

    public void sendRequest() {
        Window.open("/resource/report?"+postData.toString(), "Impression Dossier GSR", "_blank");
    }
}
