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
import com.gsr.myschool.server.business.Dossier;
import com.gsr.myschool.server.dto.DataPage;
import com.gsr.myschool.server.dto.DossierFilter;
import com.gsr.myschool.server.dto.PagedDossiers;
import com.gsr.myschool.server.process.ValidationProcessService;
import com.gsr.myschool.server.repos.DossierRepos;
import com.gsr.myschool.server.service.DossierService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DossierServiceImpl implements DossierService {
	@Autowired
	DossierRepos dossierRepos;
    @Autowired
    private ValidationProcessService validationProcessService;

    @Override
    @Transactional(readOnly = true)
    public List<Dossier> findAllDossiersByUser(Long userId) {
        List<Dossier> dossiers = new ArrayList<Dossier>();
        dossiers.addAll(dossierRepos.findByOwnerId(userId));
        return dossiers;
    }

    @Override
    public Boolean receive(Dossier dossier) {
        Map<Dossier, Task> dossiersAndTasks = new HashMap<Dossier, Task>();
        dossiersAndTasks.putAll(validationProcessService.getAllNonReceivedDossiers());
        for (Dossier dossierFromProcess: dossiersAndTasks.keySet()) {
            if (dossier.getId() == dossierFromProcess.getId()) {
                validationProcessService.receiveDossier(dossiersAndTasks.get(dossierFromProcess.getId()));
                break;
            }
        }
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Dossier> findAllDossiersInStatusByCriteria(DossierFilter filter) {
        List<Dossier> dossiers = new ArrayList<Dossier>();
        if (Strings.isNullOrEmpty(filter.getNumDossier())) {
            filter.setNumDossier("%");
        }
        if (Strings.isNullOrEmpty(filter.getNomCandidat())) {
            filter.setNumDossier("%");
        }
        dossiers.addAll(dossierRepos.findDossierByGeneratedNumDossierLikeAndStatusEqualsAndCandidatNomLike(
                filter.getNumDossier(), filter.getStatus(), filter.getNomCandidat()));
        return dossiers;
    }

    @Override
    @Transactional(readOnly = true)
    public PagedDossiers findAllDossiersByCriteria(DossierFilter filter, DataPage dataPageRequest) {
        PageRequest page = new PageRequest(dataPageRequest.getPageNumber(), dataPageRequest.getLength(),
                new Sort(Sort.Direction.DESC, "createDate"));
        if (filter.getNumDossier().equals("%")) {
            Page<Dossier> dossiers = dossierRepos.findByNumDossierLike(filter.getNumDossier(), page);
            return new PagedDossiers(dossiers.getContent(), (int) dossiers.getTotalElements());
        } else {
            if (Strings.isNullOrEmpty(filter.getNumDossier())) {
                filter.setNumDossier("%");
            }
            Page<Dossier> dossiers = dossierRepos.findByNumDossierLikeAndStatusAndDateCreation(
                    filter.getNumDossier(),
                    filter.getStatus(), filter.getDateCreation(), page);
            return new PagedDossiers(dossiers.getContent(), (int) dossiers.getTotalElements());
        }
    }
}
