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

import com.google.gwt.http.client.*;
import com.google.gwt.user.client.Window;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

public class ExcelRequestBuilder extends RequestBuilder {

    public ExcelRequestBuilder() {
        super(POST, "resource/excel");
        setHeader("Content-type", "application/json");
    }

    public void sendRequest(ValueProxy data) {
        AutoBean autobean = AutoBeanUtils.getAutoBean(data);
        String requestdata = AutoBeanCodex.encode(autobean).getPayload();
        setRequestData(requestdata);
        setCallback(new RequestCallback() {
            @Override
            public void onResponseReceived(Request request, Response response) {
                Window.open("/resource/excel?fileName=" + response.getText(), "_blank", "");
            }

            @Override
            public void onError(Request request, Throwable exception) {
                //TODO
            }
        });
        try {
            send();
        } catch (RequestException e) {
            e.printStackTrace();
        }
    }
}
