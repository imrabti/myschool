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

package com.gsr.myschool.server.job.impl;

import com.gsr.myschool.common.shared.type.DossierStatus;
import com.gsr.myschool.server.business.Dossier;
import com.gsr.myschool.server.job.Worker;
import com.gsr.myschool.server.process.ValidationProcessService;
import com.gsr.myschool.server.repos.DossierRepos;
import com.gsr.myschool.server.repos.spec.DossierSpec;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessPatchJobImpl implements Worker {

    private Log logger = LogFactory.getLog("Process Patch Job");

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private DossierRepos dossierRepos;
    @Autowired
    private ValidationProcessService validationProcessService;

    @Override
    public void work() {
        List<Dossier> dossiers = dossierRepos.findAll(DossierSpec.dossierStatusIs(DossierStatus.SUBMITTED));
        int count = 0;
        for (Dossier dossier : dossiers) {

            try {
                ProcessInstance pi = runtimeService.createProcessInstanceQuery()
                        .processDefinitionKey("validation")
                        .processInstanceBusinessKey(dossier.getId().toString()).singleResult();
                if (pi == null) {
                    validationProcessService.startProcess(dossier);
                    count++;
                }

            } catch (Exception e) {
                System.out.println(dossier.getId().toString());
                e.printStackTrace();
            }
        }
        System.out.println("Number of process started = " + count);
        logger.info("Number of process started = " + count);
    }


}
