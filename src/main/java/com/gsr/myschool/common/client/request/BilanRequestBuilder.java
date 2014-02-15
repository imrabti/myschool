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

import com.google.common.base.Joiner;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.Window;
import com.gsr.myschool.common.shared.type.BilanType;
import com.gsr.myschool.common.shared.type.DossierStatus;

public class BilanRequestBuilder extends RequestBuilder {
    private StringBuilder postData;

    public BilanRequestBuilder() {
        super(GET, "resource/convocation");
        setHeader("Content-type", "application/x-www-form-urlencoded");
    }

    public void buildData(DossierStatus status, BilanType type, String anneeScolaire, Boolean historic) {
        postData = new StringBuilder();
        Joiner joiner = Joiner.on("&").skipNulls();
        String anneeValue = null, statusValue = null, typeValue, historicValue = null;
        if (anneeScolaire != null) {
            anneeValue = "annee=" + anneeScolaire;
        }
        if (status != null) {
            statusValue = "status=" + status.name();
        }
        if (historic != null) {
            historicValue = "historic=" + historic;
        }
        typeValue = "type=" + type.ordinal();

        joiner.appendTo(postData, anneeValue, statusValue, typeValue, historicValue);
    }

    public void sendRequest() {
        if (GWT.isScript()) {
            Window.open("/preinscription/resource/bilan?" + postData.toString(), "_blank", "");
        } else {
            Window.open("/resource/bilan?" + postData.toString(), "_blank", "");
        }
    }
}
