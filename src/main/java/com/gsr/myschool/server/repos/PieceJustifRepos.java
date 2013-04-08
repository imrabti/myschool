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

import com.gsr.myschool.server.business.core.PieceJustif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PieceJustifRepos extends JpaRepository<PieceJustif, Long> {
    @Query("SELECT m FROM PieceJustif m, PieceJustifDuNE e WHERE e.niveauEtude.id = :id AND e.pieceJustif.id = m.id")
    List<PieceJustif> findByNiveauEtude(@Param("id") Long niveauEtude);

    PieceJustif findByNomEquals(String nom);
}
