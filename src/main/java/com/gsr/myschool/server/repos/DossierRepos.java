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

import com.gsr.myschool.common.shared.type.DossierStatus;
import com.gsr.myschool.common.shared.dto.BilanDTO;
import com.gsr.myschool.server.business.Dossier;
import com.gsr.myschool.server.business.valuelist.ValueList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DossierRepos extends JpaRepository<Dossier, Long>, JpaSpecificationExecutor<Dossier> {
    @Query("select d.generatedNumDossier from Dossier d")
    List<String> findAllNumDossier();

    List<Dossier> findByOwnerIdOrderByIdDesc(Long userId);

    @Query("select new com.gsr.myschool.common.shared.dto.BilanDTO(d.niveauEtude.nom, d.niveauEtude.filiere.id, count(d)) " +
            "FROM Dossier d WHERE d.status = :status AND d.anneeScolaire = :anneeScolaire group by d.niveauEtude ")
    List<BilanDTO> findBilanDossier(@Param("status") DossierStatus status, @Param("anneeScolaire") ValueList anneeScolaire);

    @Query("select new com.gsr.myschool.common.shared.dto.BilanDTO(d.niveauEtude.type, d.niveauEtude.filiere.id, count(d)) " +
            "FROM Dossier d WHERE d.status = :status AND d.anneeScolaire = :anneeScolaire group by d.niveauEtude.type ")
    List<BilanDTO> findBilanCycle(@Param("status") DossierStatus status, @Param("anneeScolaire") ValueList anneeScolaire);

    @Query("select new com.gsr.myschool.common.shared.dto.BilanDTO(d.niveauEtude.nom, d.niveauEtude.filiere.id, count(d)) " +
            "FROM Dossier d WHERE d.anneeScolaire = :anneeScolaire group by d.niveauEtude ")
    List<BilanDTO> findBilanDossier(@Param("anneeScolaire") ValueList anneeScolaire);

    @Query("select new com.gsr.myschool.common.shared.dto.BilanDTO(d.niveauEtude.type, d.niveauEtude.filiere.id, count(d)) " +
            "FROM Dossier d WHERE d.anneeScolaire = :anneeScolaire group by d.niveauEtude.type ")
    List<BilanDTO> findBilanCycle(@Param("anneeScolaire") ValueList anneeScolaire);

    List<Dossier> findByOwnerIdAndAnneeScolaireId(Long userId, Long anneeScolaireId);

    List<Dossier> findByOwnerIdAndAnneeScolaireIdAndStatus(Long userId, Long anneeScolaireId, DossierStatus status);
}
