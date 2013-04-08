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

package com.gsr.myschool.server.service;

import com.gsr.myschool.common.shared.type.SettingsKey;
import com.gsr.myschool.server.business.core.Filiere;
import com.gsr.myschool.server.business.core.MatiereExamen;
import com.gsr.myschool.server.business.core.NiveauEtude;
import com.gsr.myschool.server.business.core.PieceJustif;

import java.util.List;

public interface SettingsService {
    void updateSettings(SettingsKey key, String value);

    String getSetting(SettingsKey key);

    Boolean addFiliere(Filiere filiere);

    Boolean addNiveauEtude(NiveauEtude niveauEtude);

    List<PieceJustif> findAllPieceJustif();

    List<MatiereExamen> findAllMatiereExamen();

    Boolean deletePieceJustif(PieceJustif piece);

    Boolean deleteMatiereExamen(MatiereExamen matiere);

    Boolean addPieceJustif(PieceJustif piece);

    Boolean addMatiereExamen(MatiereExamen matiere);
}
