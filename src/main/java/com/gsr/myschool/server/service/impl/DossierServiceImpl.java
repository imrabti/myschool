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
import com.gsr.myschool.server.business.Dossier;
import com.gsr.myschool.server.process.ValidationProcessService;
import com.gsr.myschool.server.repos.DossierRepos;
import com.gsr.myschool.server.repos.spec.DossierSpec;
import com.gsr.myschool.server.service.DossierService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        Map<Dossier, Task> dossiersAndTasks = validationProcessService.getAllNonReceivedDossiers();
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
    public List<Dossier> findAllDossiersByCriteria(DossierFilterDTO filter) {
        Specifications spec = Specifications.where(DossierSpec.numDossierLike(filter.getNumDossier()));

        if (filter.getStatus() != null) {
            spec = spec.and(DossierSpec.dossierStatusIs(filter.getStatus()));
        }

        if (filter.getCreated() != null) {
            spec = spec.and(DossierSpec.dossierCreatedEqual(filter.getCreated()));
        }

        if (!Strings.isNullOrEmpty(filter.getFirstnameOrlastname())) {
            spec = spec.and(DossierSpec.firstnameLike(filter.getFirstnameOrlastname()))
                    .or(DossierSpec.lastnameLike(filter.getFirstnameOrlastname()));
        }

        return dossierRepos.findAll(spec);
    }
}
