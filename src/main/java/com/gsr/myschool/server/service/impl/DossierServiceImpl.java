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

package com.gsr.myschool.server.service.impl;

import com.google.common.base.Strings;
import com.gsr.myschool.common.shared.dto.DossierFilterDTO;
import com.gsr.myschool.common.shared.dto.PagedDossiers;
import com.gsr.myschool.common.shared.dto.PiecejustifDTO;
import com.gsr.myschool.common.shared.type.DossierStatus;
import com.gsr.myschool.server.business.Dossier;
import com.gsr.myschool.server.business.core.PieceJustif;
import com.gsr.myschool.server.business.core.PieceJustifDuNE;
import com.gsr.myschool.server.process.ValidationProcessService;
import com.gsr.myschool.server.repos.DossierRepos;
import com.gsr.myschool.server.repos.PieceJustifDuNERepos;
import com.gsr.myschool.server.repos.PieceJustifRepos;
import com.gsr.myschool.server.repos.spec.DossierSpec;
import com.gsr.myschool.server.service.DossierService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DossierServiceImpl implements DossierService {
    @Autowired
    private DossierRepos dossierRepos;
    @Autowired
    private PieceJustifRepos pieceJustifRepos;
    @Autowired
    private PieceJustifDuNERepos pieceJustifDuNERepos;
    @Autowired
    private ValidationProcessService validationProcessService;

    @Override
    public Boolean receive(Dossier dossier) {
        Task task = validationProcessService.getAllNonReceivedDossiers(dossier.getId());
        Dossier receivedDossier = dossierRepos.findOne(dossier.getId());

        if (receivedDossier.getStatus() != DossierStatus.STANDBY) {
            // NiveauEtude always not null
            List<PieceJustif> pieceJustifs = pieceJustifRepos.findByNiveauEtude(
                    receivedDossier.getNiveauEtude().getId());

            // create piecejustif DTOs
            List<PiecejustifDTO> piecejustifDTOs = new ArrayList<PiecejustifDTO>();
            for (PieceJustif pieceJustif : pieceJustifs) {
                piecejustifDTOs.add(PiecejustifDTO.mapper(pieceJustif));
            }
            validationProcessService.receiveDossier(task, piecejustifDTOs);
        } else {
            validationProcessService.receiveDossier(task);
        }

        receivedDossier.setStatus(DossierStatus.RECEIVED);
        dossierRepos.save(receivedDossier);

        return true;
    }

    @Override
    public List<PiecejustifDTO> getPiecejustifFromProcess(Dossier dossier) {
        return validationProcessService.getPiecejustifFromProcess(dossier);
    }

    @Override
    public Boolean verify(Long dossierId, List<PiecejustifDTO> piecejustifDTOs) {
        Task task = validationProcessService.getAllReceivedDossiers(dossierId);

        List<PiecejustifDTO> pieceNonavailable = new ArrayList<PiecejustifDTO>();
        for (PiecejustifDTO piece : piecejustifDTOs) {
            if (piece.getAvailable() == null || !piece.getAvailable()) {
                pieceNonavailable.add(piece);
            }
        }

        if (pieceNonavailable.isEmpty()) {
            // piece all existing
            validationProcessService.acceptDossier(task);

            Dossier verifiedDossier = dossierRepos.findOne(dossierId);
            verifiedDossier.setStatus(DossierStatus.ACCEPTED_FOR_STUDY);
            dossierRepos.save(verifiedDossier);
        } else {
            // there is at least one piece not available
            validationProcessService.rejectDossier(task, piecejustifDTOs);

            Dossier verifiedDossier = dossierRepos.findOne(dossierId);
            verifiedDossier.setStatus(DossierStatus.STANDBY);
            dossierRepos.save(verifiedDossier);
        }
        return true;
    }

    @Override
    public Integer findPiecesByNiveauEtude(Long level) {
        List<PieceJustifDuNE> piecesList = pieceJustifDuNERepos.findByNiveauEtudeId(level);
        return piecesList.size();
    }

    @Override
    @Transactional(readOnly = true)
    public PagedDossiers findAllDossiersByCriteria(DossierFilterDTO filter, Integer pageNumber, Integer length) {
        Specifications<Dossier> spec = Specifications.where(DossierSpec.firstnameLike(filter.getFirstnameOrlastname()))
                .or(DossierSpec.lastnameLike(filter.getFirstnameOrlastname()));

        if (filter.getStatus() != null) {
            spec = spec.and(DossierSpec.dossierStatusIs(filter.getStatus()));
        }

        if (filter.getDateTill() != null) {
            spec = spec.and(DossierSpec.dossierCreatedLower(filter.getDateTill()));
        }

        if (filter.getDateFrom() != null) {
            spec = spec.and(DossierSpec.dossierCreatedGreater(filter.getDateFrom()));
        }

        if (filter.getFiliere() != null) {
            spec = spec.and(DossierSpec.filiereEqual(filter.getFiliere()));
        }

        if (filter.getNiveauEtude() != null) {
            spec = spec.and(DossierSpec.niveauEtudeEqual(filter.getNiveauEtude()));
        }

        if (filter.getGsrFraterie() != null && filter.getGsrFraterie()) {
            spec = spec.and(DossierSpec.isGsrFraterie(filter.getGsrFraterie()));
        }

        if (filter.getParentGsr() != null && filter.getParentGsr()) {
            spec = spec.and(DossierSpec.isParentGsr(filter.getParentGsr()));
        }

        if (!Strings.isNullOrEmpty(filter.getNumDossier())) {
            spec = spec.and(DossierSpec.numDossierLike(filter.getNumDossier()));
        }

        if (pageNumber != null && length != null) {
            PageRequest page = new PageRequest(pageNumber, length);
            Page resultPage = dossierRepos.findAll(spec, page);

            return new PagedDossiers(resultPage.getContent(), (int) resultPage.getTotalElements());
        } else {
            List<Dossier> result = dossierRepos.findAll(spec);

            return new PagedDossiers(result, result.size());
        }
    }
}
