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
import com.gsr.myschool.common.shared.dto.DossierConvocationDTO;
import com.gsr.myschool.common.shared.dto.DossierFilterDTO;
import com.gsr.myschool.common.shared.dto.DossierMultiple;
import com.gsr.myschool.common.shared.dto.PagedDossiers;
import com.gsr.myschool.common.shared.dto.PiecejustifDTO;
import com.gsr.myschool.common.shared.type.DossierStatus;
import com.gsr.myschool.common.shared.type.ValueTypeCode;
import com.gsr.myschool.server.business.Dossier;
import com.gsr.myschool.server.business.InfoParent;
import com.gsr.myschool.server.business.User;
import com.gsr.myschool.server.business.core.PieceJustif;
import com.gsr.myschool.server.business.core.SessionExamen;
import com.gsr.myschool.server.business.valuelist.ValueList;
import com.gsr.myschool.server.process.ValidationProcessService;
import com.gsr.myschool.server.process.impl.ValidationProcessServiceImpl.ValidationTask;
import com.gsr.myschool.server.repos.DossierRepos;
import com.gsr.myschool.server.repos.DossierSessionRepos;
import com.gsr.myschool.server.repos.FraterieRepos;
import com.gsr.myschool.server.repos.InfoParentRepos;
import com.gsr.myschool.server.repos.PieceJustifRepos;
import com.gsr.myschool.server.repos.SessionExamenRepos;
import com.gsr.myschool.server.repos.UserRepos;
import com.gsr.myschool.server.repos.ValueListRepos;
import com.gsr.myschool.server.repos.spec.DossierSpec;
import com.gsr.myschool.server.service.DossierService;
import com.gsr.myschool.server.util.DateUtils;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DossierServiceImpl implements DossierService {
    @Autowired
    private DossierRepos dossierRepos;
    @Autowired
    private DossierSessionRepos dossierSessionRepos;
    @Autowired
    private FraterieRepos fraterieRepos;
    @Autowired
    private PieceJustifRepos pieceJustifRepos;
    @Autowired
    private UserRepos userRepos;
    @Autowired
    private ValueListRepos valueListRepos;
    @Autowired
    private InfoParentRepos infoParentRepos;
    @Autowired
    private SessionExamenRepos sessionExamenRepos;
    @Autowired
    private ValidationProcessService validationProcessService;

    @Override
    public Boolean receive(Dossier dossier) {
        Task task = validationProcessService.getDossierToReceive(dossier.getId());
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
            validationProcessService.receiveDossier(task, receivedDossier, piecejustifDTOs);
        } else {
            validationProcessService.receiveDossier(task, receivedDossier);
        }

        receivedDossier.setStatus(DossierStatus.RECEIVED);
        dossierRepos.save(receivedDossier);

        return true;
    }

    @Override
    public List<PiecejustifDTO> getPieceJustifFromProcessValidation(Dossier dossier) {
        return validationProcessService.getPieceJustifFromProcess(dossier.getId(), ValidationTask.VALIDATION);
    }

    @Override
    public List<PiecejustifDTO> getPieceJustifFromProcessStandby(Dossier dossier) {
        return validationProcessService.getPieceJustifFromProcess(dossier.getId(), ValidationTask.RECEPTION);
    }

    @Override
    public Boolean verify(Long dossierId, List<String> notChecked) {
        Task task = validationProcessService.getDossierToValidate(dossierId);

        Map<Long, PiecejustifDTO> pieceNotAvailable = new HashMap<Long, PiecejustifDTO>();
        for (String motif : notChecked) {
            Long pieceId = Long.parseLong(motif.split("#")[0]);
            PiecejustifDTO piece = PiecejustifDTO.mapper(pieceJustifRepos.findOne(pieceId));
            piece.setMotif(motif.split("#")[1]);
            piece.setAvailable(false);
            pieceNotAvailable.put(pieceId, piece);
        }

        if (pieceNotAvailable.isEmpty()) {
            Dossier verifiedDossier = dossierRepos.findOne(dossierId);

            // piece all existing
            validationProcessService.acceptDossier(task, verifiedDossier);

            verifiedDossier.setStatus(DossierStatus.ACCEPTED_FOR_STUDY);
            dossierRepos.save(verifiedDossier);
        } else {
            Dossier dossier = dossierRepos.findOne(dossierId);
            List<PiecejustifDTO> piecejustifDTOs = validationProcessService.getPieceJustifFromProcess(dossier.getId(),
                    ValidationTask.VALIDATION);
            for (PiecejustifDTO piece : piecejustifDTOs) {
                if (pieceNotAvailable.containsKey(piece.getId())) {
                    piece.setAvailable(false);
                    piece.setMotif(pieceNotAvailable.get(piece.getId()).getMotif());
                } else {
                    piece.setAvailable(true);
                    piece.setMotif("");
                }
            }

            Dossier verifiedDossier = dossierRepos.findOne(dossierId);

            // there is at least one piece not available
            validationProcessService.rejectDossier(task, verifiedDossier, piecejustifDTOs);

            verifiedDossier.setStatus(DossierStatus.STANDBY);
            dossierRepos.save(verifiedDossier);
        }
        return true;
    }

    @Override
    public Boolean rejectDossier(Long dossierId, String motif) {
        Task task = validationProcessService.getDossierToAnalyse(dossierId);
        if (task == null) return false;

        Dossier analyzedDossier = dossierRepos.findOne(dossierId);

        analyzedDossier.setStatus(DossierStatus.NOT_ACCEPTED_FOR_TEST);
        analyzedDossier.setMotifRefus(motif);

        dossierRepos.save(analyzedDossier);
        validationProcessService.rejectAnalysedDossier(task, analyzedDossier);
        return true;
    }

    @Override
    public Boolean closeDossier(Dossier dossier, DossierStatus status, String comment) {
        Dossier affectedDossier = dossierRepos.findOne(dossier.getId());

        Task task = validationProcessService.getDossierToAdmission(dossier.getId());
        if (task == null) return false;

        affectedDossier.setStatus(status);
        affectedDossier.setMotifRefus(comment);

        if (DossierStatus.TO_BE_REGISTERED == status) {
            validationProcessService.admitFinalDossier(task, dossier);
        } else {
            validationProcessService.rejectFinalDossier(task, dossier);
        }

        dossierRepos.save(affectedDossier);
        return true;
    }

    @Override
    public Boolean acceptDossier(Dossier dossier) {
        Dossier analyzedDossier = dossierRepos.findOne(dossier.getId());

        if (analyzedDossier.getStatus() == DossierStatus.NOT_ACCEPTED_FOR_TEST) {
            Task task = validationProcessService.getDossierToReAccept(dossier.getId());
            if (task == null) return false;

            analyzedDossier.setStatus(DossierStatus.ACCEPTED_FOR_TEST);

            validationProcessService.acceptAnalysedDossier(task, dossier);
        } else {
            Task task = validationProcessService.getDossierToAnalyse(dossier.getId());
            if (task == null) return false;

            analyzedDossier.setStatus(DossierStatus.ACCEPTED_FOR_TEST);

            validationProcessService.acceptAnalysedDossier(task, dossier);
        }

        dossierRepos.save(analyzedDossier);
        return true;
    }


    @Override
    @Transactional(readOnly = true)
    public PagedDossiers findAllDossiersByCriteria(DossierFilterDTO filter, Integer pageNumber, Integer length) {
        Specifications<Dossier> spec = Specifications.where(DossierSpec.numDossierLike(filter.getNumDossier()));

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

        if (!Strings.isNullOrEmpty(filter.getFirstnameOrlastname())) {
            spec = spec.and(Specifications.where(DossierSpec.firstnameLike(filter.getFirstnameOrlastname()))
                    .or(DossierSpec.lastnameLike(filter.getFirstnameOrlastname())));
        }

        if (filter.getSession() != null && filter.getSession().getId() != null) {
            spec = spec.and(DossierSpec.sessionEqual(filter.getSession()));
        }

        if (filter.getStatusList() != null && !filter.getStatusList().isEmpty()) {
            spec = spec.and(DossierSpec.statusIn(filter.getStatusList()));
        }

        if (pageNumber != null && length != null) {
            PageRequest page = new PageRequest(pageNumber, length);
            Page resultPage = dossierRepos.findAll(spec, page);

            return new PagedDossiers(resultPage.getContent(), null, (int) resultPage.getTotalElements());
        } else {
            List<Dossier> result = dossierRepos.findAll(spec);

            return new PagedDossiers(result, null, result.size());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PagedDossiers findAllDossiersBySessionAndCriteria(DossierFilterDTO filter, Integer pageNumber, Integer length) {
        Specifications<Dossier> spec = Specifications.where(DossierSpec.numDossierLike(filter.getNumDossier()));

        if (filter.getStatus() != null) {
            spec = spec.and(DossierSpec.dossierStatusIs(filter.getStatus()));
        }

        if (filter.getFiliere() != null) {
            spec = spec.and(DossierSpec.filiereEqual(filter.getFiliere()));
        }

        if (filter.getNiveauEtude() != null) {
            spec = spec.and(DossierSpec.niveauEtudeEqual(filter.getNiveauEtude()));
        }

        if (filter.getGsrFraterie() != null) {
            spec = spec.and(DossierSpec.isGsrFraterie(filter.getGsrFraterie()));
        }

        if (filter.getParentGsr() != null) {
            spec = spec.and(DossierSpec.isParentGsr(filter.getParentGsr()));
        }

        if (!Strings.isNullOrEmpty(filter.getSessionIds())) {
            String[] ids = filter.getSessionIds().split(";");
            List<SessionExamen> sessions = new ArrayList<SessionExamen>();
            for (String id : ids) {
                if (!Strings.isNullOrEmpty(id)) {
                    sessions.add(sessionExamenRepos.findOne(Long.parseLong(id)));
                }
            }

            if (!sessions.isEmpty()) {
                spec = spec.and(DossierSpec.sessionIn(sessions));
            }
        }

        List<DossierConvocationDTO> dossierConvocationDTOs = new ArrayList<DossierConvocationDTO>();

        if (pageNumber != null && length != null) {
            PageRequest page = new PageRequest(pageNumber, length);
            Page resultPage = dossierRepos.findAll(spec, page);

            dossierConvocationDTOs = setConvocationAttrs(dossierConvocationDTOs, resultPage.getContent());

            return new PagedDossiers(null, dossierConvocationDTOs, (int) resultPage.getTotalElements());
        } else {
            List<Dossier> result = dossierRepos.findAll(spec, new Sort(new Sort.Order("candidat.lastname")));

            dossierConvocationDTOs = setConvocationAttrs(dossierConvocationDTOs, result);

            return new PagedDossiers(null, dossierConvocationDTOs, result.size());
        }
    }

    private List<DossierConvocationDTO> setConvocationAttrs(List<DossierConvocationDTO> dossierConvocationDTOs,
            List<Dossier> dossiers) {
        for (Object dossier: dossiers) {
            Dossier dossierConv = (Dossier) dossier;
            DossierConvocationDTO dossierConvocationDTO = new DossierConvocationDTO();
            dossierConvocationDTO.setDossierSession(dossierSessionRepos.findByDossierId(dossierConv.getId()));
            dossierConvocationDTO.setGsrFraterie(fraterieRepos.findByCandidatIdAndEtablissementGsr(
                    dossierConv.getCandidat().getId(), true
            ).size() > 0);
            dossierConvocationDTO.setGsrParent(infoParentRepos.findByDossierIdAndParentGsr(
                    dossierConv.getId(), true
            ).size() > 0);
            dossierConvocationDTOs.add(dossierConvocationDTO);
        }
        return dossierConvocationDTOs;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DossierMultiple> findMultipleDossierByStatus(DossierStatus status) {
        List<DossierMultiple> dossierMultiples = new ArrayList<DossierMultiple>();
        List<User> listUsers = userRepos.findAll();
        String currentAnneeScolaire = DateUtils.currentYear() + "-" + (DateUtils.currentYear() + 1);
        ValueList anneeScolaire = valueListRepos.findByValueAndValueTypeCode(currentAnneeScolaire,
                ValueTypeCode.SCHOOL_YEAR);

        if (anneeScolaire != null) {
            for (User user : listUsers) {
                Integer dossierCount;
                if (status == null) {
                    dossierCount = dossierRepos.findByOwnerIdAndAnneeScolaireId(user.getId(),
                            anneeScolaire.getId()).size();
                } else {
                    dossierCount = dossierRepos.findByOwnerIdAndAnneeScolaireIdAndStatus(user.getId(),
                            anneeScolaire.getId(), status).size();
                }

                if (dossierCount > 1) {
                    List<Dossier> dossiers = dossierRepos.findByOwnerIdOrderByIdDesc(user.getId());
                    for (Dossier item : dossiers) {
                        List<InfoParent> parents = infoParentRepos.findByDossierId(item.getId());
                        dossierMultiples.add(new DossierMultiple(user, item, parents));
                    }
                }
            }
        }

        return dossierMultiples;
    }
}
