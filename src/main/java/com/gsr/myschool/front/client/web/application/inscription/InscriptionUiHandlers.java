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

package com.gsr.myschool.front.client.web.application.inscription;

import com.gsr.myschool.common.client.proxy.InscriptionProxy;
import com.gwtplatform.mvp.client.UiHandlers;

public interface InscriptionUiHandlers extends UiHandlers {
    void previewInscription(InscriptionProxy inscription);

    void editInscription(InscriptionProxy inscription);

    void deleteInscription(InscriptionProxy inscription);

    void submitInscription(InscriptionProxy inscription);

    void printInscription(InscriptionProxy inscription);
}
