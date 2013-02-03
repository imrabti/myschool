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

package com.gsr.myschool.server.process.impl;

import com.gsr.myschool.common.shared.type.DossierStatus;
import com.gsr.myschool.server.business.Dossier;
import com.gsr.myschool.server.process.ValidationProcessService;
import com.gsr.myschool.server.repos.DossierRepos;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ValidationProcessServiceImpl implements ValidationProcessService {
    public enum ValidationTask {
        RECEPTION("receptionTask"),
        VALIDATION("validationTask"),
        ANALYSE("analyseTask"),
        CHANGER_STATE("changeStateTask"),
        AFFECTATION("affectationTask");
        private String value;

        private ValidationTask(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private DossierRepos dossierRepos;

    @Override
    public void startProcess(Dossier dossier) {
        // treatment on Dossier
        dossier.setSubmitDate(new Date());
        dossier.setStatus(DossierStatus.SUBMITTED);
        dossierRepos.save(dossier);

        // Initialise process variables
        Map<String, Object> processParams = new HashMap<String, Object>();
        processParams.put("email", dossier.getOwner().getEmail());
        processParams.put("dossier", dossier);
        processParams.put("dueDate", "");

        // start process by key
        runtimeService.startProcessInstanceByKey("validation", processParams);
    }

    @Override
    public Map<Task, Dossier> getAllNonReceivedDossiers() {
        List<Task> taskList = taskService.createTaskQuery().taskDefinitionKey(ValidationTask.RECEPTION.getValue()).list();
        Map<Task, Dossier> dossierMap = new HashMap<Task, Dossier>();
        for (Task task : taskList) {
            dossierMap.put(task, (Dossier) runtimeService.getVariable(task.getExecutionId(), "dossier"));
        }
        return dossierMap;
    }

    @Override
    public void receiveDossier(Task task) {
        taskService.complete(task.getId());
    }

    @Override
    public Map<Task, Dossier> getAllReceivedDossiers() {
        List<Task> taskList = taskService.createTaskQuery().taskDefinitionKey(ValidationTask.VALIDATION.getValue()).list();
        Map<Task, Dossier> dossierMap = new HashMap<Task, Dossier>();
        for (Task task : taskList) {
            dossierMap.put(task, (Dossier) runtimeService.getVariable(task.getExecutionId(), "dossier"));
        }
        return dossierMap;
    }

    @Override
    public void rejectDossier(Task task) {
        // Initialise process variables
        Map<String, Object> processParams = new HashMap<String, Object>();
        processParams.put("dossierComplet", false);

        taskService.complete(task.getId(), processParams);
    }

    @Override
    public void acceptDossier(Task task) {
        // Initialise process variables
        Map<String, Object> processParams = new HashMap<String, Object>();
        processParams.put("dossierComplet", true);

        taskService.complete(task.getId(), processParams);
    }

    @Override
    public List<Dossier> getAllNonAnalysedDossiers() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void rejectAnalysedDossier(Dossier dossier) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void acceptAnalysedDossier(Dossier dossier) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Dossier> getAllAnalysedDossiers() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
