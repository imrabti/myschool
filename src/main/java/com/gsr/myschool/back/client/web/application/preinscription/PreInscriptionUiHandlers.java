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

package com.gsr.myschool.back.client.web.application.preinscription;

import com.gsr.myschool.common.client.proxy.DossierFilterDTOProxy;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gwtplatform.mvp.client.UiHandlers;

public interface PreInscriptionUiHandlers extends UiHandlers {
    void fetchData(Integer offset, Integer limit);

    void viewDetails(DossierProxy dossier);

    void searchWithFilter(DossierFilterDTOProxy dossierFilter);

    void init();

    void export(DossierFilterDTOProxy dossierFilter);

    void printInscription(DossierProxy inscription);

    void delete(DossierProxy inscription);

    void printConvocationAction(DossierProxy inscription);

    void sendConvocationAction(DossierProxy inscription);
}
