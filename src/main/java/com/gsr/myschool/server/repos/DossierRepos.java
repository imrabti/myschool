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
import com.gsr.myschool.server.business.Dossier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface DossierRepos extends JpaRepository<Dossier, Long> {
    List<Dossier> findByOwnerId(Long userId);

    @Query("FROM Dossier d WHERE d.generatedNumDossier like %?1%")
    Page<Dossier> findByNumDossierLike(String numDossier, Pageable pageable);

    @Query("FROM Dossier d WHERE d.generatedNumDossier like %?1% AND d.status = ?2")
    Page<Dossier> findByNumDossierLikeAndStatus(String numDossier, DossierStatus dossierStatus
            , Pageable pageable);

    @Query("FROM Dossier d WHERE d.generatedNumDossier like %?1% AND d.createDate >= ?2")
    Page<Dossier> findByNumDossierLikeAndDateCreation(String numDossier
            ,Date startDate, Pageable pageable);

    @Query("FROM Dossier d WHERE d.generatedNumDossier like %?1% AND d.status = ?2 AND d.createDate >= ?3")
    Page<Dossier> findByNumDossierLikeAndStatusAndDateCreation(String numDossier, DossierStatus dossierStatus
            ,Date startDate, Pageable pageable);

    @Query("FROM Dossier d WHERE d.generatedNumDossier like %?1% AND d.status = ?2 AND d.candidat.firstname like %?3% "
            + "OR d.candidat.lastname like %?3%")
    List<Dossier> findDossierByGeneratedNumDossierLikeAndStatusEqualsAndCandidatNomLike(String numDossier,
            DossierStatus status, String nomCandidat);
}
