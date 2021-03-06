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

package com.gsr.myschool.back.client.request;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import com.gsr.myschool.common.client.proxy.MatiereExamenProxy;
import com.gsr.myschool.common.client.proxy.NiveauEtudeProxy;
import com.gsr.myschool.common.client.proxy.PieceJustifProxy;
import com.gsr.myschool.server.service.impl.NiveauEtudeServiceImpl;
import com.gsr.myschool.server.util.SpringServiceLocator;

import java.util.List;

@Service(value = NiveauEtudeServiceImpl.class, locator = SpringServiceLocator.class)
public interface NiveauEtudeRequest extends RequestContext {
    Request<List<MatiereExamenProxy>> getMatiereExamenByNiveau(Long niveauEtude);

    Request<List<PieceJustifProxy>> getPieceJustfByNiveau(Long niveauEtude);

    Request<Boolean> editPieceDuNiveau(PieceJustifProxy pieceJustifProxy, NiveauEtudeProxy niveauEtudeProxy,
            Boolean removeIt);

    Request<Boolean> editMatiereDuNiveau(MatiereExamenProxy object, NiveauEtudeProxy currentNiveauEtude,
            Boolean removeIt);
}
