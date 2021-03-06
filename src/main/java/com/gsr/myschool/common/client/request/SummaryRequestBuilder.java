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

package com.gsr.myschool.common.client.request;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.Window;

public class SummaryRequestBuilder extends RequestBuilder {
    private StringBuffer postData;

    public SummaryRequestBuilder() {
        super(GET, "resource/summary");
        setHeader("Content-type", "application/x-www-form-urlencoded");
    }

    public void buildData(String dossierId) {
        postData = new StringBuffer();
        postData.append("annee=").append(dossierId);
    }

    public void sendRequest() {
        if (GWT.isScript()) {
            Window.open("/preinscription/resource/summary?" + postData.toString(), "_blank", "");
        } else {
            Window.open("/resource/summary?" + postData.toString(), "_blank", "");
        }
    }
}
