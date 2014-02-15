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

package com.gsr.myschool.server.repos;

import com.gsr.myschool.common.shared.dto.BilanDTO;
import com.gsr.myschool.common.shared.type.DossierStatus;
import com.gsr.myschool.server.business.DossierHistoric;
import com.gsr.myschool.server.business.valuelist.ValueList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DossierHistoricRepo extends JpaRepository<DossierHistoric, Long> {
    @Query("select new com.gsr.myschool.common.shared.dto.BilanDTO(d.dossier.niveauEtude.type, d.dossier.niveauEtude.filiere.id, count(distinct d.dossier))" +
            " FROM DossierHistoric d WHERE d.status = :status AND d.dossier.anneeScolaire = :anneeScolaire group by d.dossier.niveauEtude.type ")
    List<BilanDTO> findBilanDossierHistoric(@Param("status") DossierStatus status, @Param("anneeScolaire") ValueList anneeScolaire);

    @Query("select new com.gsr.myschool.common.shared.dto.BilanDTO(d.dossier.niveauEtude.type, d.dossier.niveauEtude.filiere.id, count(distinct d.dossier))" +
            " FROM DossierHistoric d WHERE d.status = :status AND d.dossier.anneeScolaire = :anneeScolaire group by d.dossier.niveauEtude ")
    List<BilanDTO> findBilanCycleHistoric(@Param("status") DossierStatus status, @Param("anneeScolaire") ValueList anneeScolaire);

    @Query("select new com.gsr.myschool.common.shared.dto.BilanDTO(d.dossier.niveauEtude.type, d.dossier.niveauEtude.filiere.id, count(distinct d.dossier))" +
            " FROM DossierHistoric d WHERE d.dossier.anneeScolaire = :anneeScolaire group by d.dossier.niveauEtude.type ")
    List<BilanDTO> findBilanDossierHistoric(@Param("anneeScolaire") ValueList anneeScolaire);

    @Query("select new com.gsr.myschool.common.shared.dto.BilanDTO(d.dossier.niveauEtude.type, d.dossier.niveauEtude.filiere.id, count(distinct d.dossier)) " +
            "FROM DossierHistoric d WHERE d.dossier.anneeScolaire = :anneeScolaire group by d.dossier.niveauEtude ")
    List<BilanDTO> findBilanCycleHistoric(@Param("anneeScolaire") ValueList anneeScolaire);
}
