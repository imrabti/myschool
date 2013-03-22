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

import com.google.common.base.Strings;
import com.gsr.myschool.common.shared.dto.EmailDTO;
import com.gsr.myschool.common.shared.dto.PiecejustifDTO;
import com.gsr.myschool.common.shared.type.DossierStatus;
import com.gsr.myschool.common.shared.type.EmailType;
import com.gsr.myschool.common.shared.type.InboxMessageStatus;
import com.gsr.myschool.server.business.Dossier;
import com.gsr.myschool.server.business.InboxMessage;
import com.gsr.myschool.server.process.ValidationProcessService;
import com.gsr.myschool.server.repos.DossierRepos;
import com.gsr.myschool.server.repos.InboxMessageRepos;
import com.gsr.myschool.server.repos.UserRepos;
import com.gsr.myschool.server.service.EmailService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    @Autowired
    private EmailService emailService;
    @Autowired
    private InboxMessageRepos inboxMessageRepos;
    @Autowired
    private UserRepos userRepos;

    @Override
    public void startProcess(Dossier dossier) {
        // treatment on Dossier
        dossier.setSubmitDate(new Date());
        dossier.setStatus(DossierStatus.SUBMITTED);
        dossier = dossierRepos.save(dossier);

        // Initialise process variables
        Map<String, Object> processParams = new HashMap<String, Object>();
        processParams.put("dossierId",dossier.getId());
        processParams.put("email", new EmailDTO());
        processParams.put("dossier", dossier);
        processParams.put("dueDate", "");

        // start process by key
        runtimeService.startProcessInstanceByKey("validation", processParams);
    }

    @Override
    @Deprecated
    public Map<Dossier, Task> getAllNonReceivedDossiers() {
        List<Task> taskList = taskService.createTaskQuery().taskDefinitionKey(ValidationTask.RECEPTION.getValue()).list();
        Map<Dossier, Task> dossierMap = new HashMap<Dossier, Task>();
        for (Task task : taskList) {
            dossierMap.put((Dossier) runtimeService.getVariable(task.getExecutionId(), "dossier"), task);
        }
        return dossierMap;
    }

    @Override
    public Task getAllNonReceivedDossiers(Long dossierId) {
        return taskService.createTaskQuery()
                .processVariableValueEquals("dossierId", dossierId)
                .taskDefinitionKey(ValidationTask.RECEPTION.getValue())
                .singleResult();
    }

    @Override
    public void receiveDossier(Task task) {
        taskService.complete(task.getId());
    }

    @Override
    public void receiveDossier(Task task, List<PiecejustifDTO> piecejustifDTOs) {
        // Initialise process variables
        Map<String, Object> processParams = new HashMap<String, Object>();
        processParams.put("piecejustifs", piecejustifDTOs);

        taskService.complete(task.getId(), processParams);
    }

    @Override
    @Deprecated
    public Map<Dossier, Task> getAllReceivedDossiers() {
        List<Task> taskList = taskService.createTaskQuery().taskDefinitionKey(ValidationTask.VALIDATION.getValue()).list();
        Map<Dossier, Task> dossierMap = new HashMap<Dossier, Task>();
        for (Task task : taskList) {
            dossierMap.put((Dossier) runtimeService.getVariable(task.getExecutionId(), "dossier"), task);
        }
        return dossierMap;
    }

    @Override
    public Task getAllReceivedDossiers(Long dossierId) {
        return taskService.createTaskQuery()
                .processVariableValueEquals("dossierId", dossierId)
                .taskDefinitionKey(ValidationTask.VALIDATION.getValue())
                .singleResult();
    }

    @Override
    public List<PiecejustifDTO> getPiecejustifFromProcess(Dossier dossier) {
        Task task = taskService.createTaskQuery()
                .processVariableValueEquals("dossierId", dossier.getId())
                .taskDefinitionKey(ValidationTask.VALIDATION.getValue())
                .singleResult();
        return (List<PiecejustifDTO>) runtimeService.getVariable(task.getExecutionId(), "piecejustifs");
    }

    @Override
    public void rejectDossier(Task task, List<PiecejustifDTO> pieceNonavailable) {
        EmailDTO email = (EmailDTO) runtimeService.getVariable(task.getExecutionId(), "email");
        Dossier dossier = (Dossier) runtimeService.getVariable(task.getExecutionId(), "dossier");
        Map<String, Object> params = new HashMap<String, Object>();

        List<String> pieces = new ArrayList<String>();
        for (PiecejustifDTO piece : pieceNonavailable) {
            if (piece.getAvailable() == null || !piece.getAvailable()) {
                pieces.add(Strings.isNullOrEmpty(piece.getMotif()) ?
                        piece.getNom() : piece.getNom() + " : " + piece.getMotif());
            }
        }

        params.put("gender", dossier.getOwner().getGender().toString());
        params.put("lastname", dossier.getOwner().getLastName());
        params.put("firstname", dossier.getOwner().getFirstName());
        params.put("nomEnfant", dossier.getCandidat().getLastname());
        params.put("prenomEnfant", dossier.getCandidat().getFirstname());
        params.put("refdossier", dossier.getGeneratedNumDossier());
        params.put("pieceNonavailable", pieces);

        try {
            email = emailService.populateEmail(EmailType.DOSSIER_INCOMPLETE, email.getTo(), email.getFrom(), params, "", "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        InboxMessage message = new InboxMessage();
        message.setParentUser(userRepos.findByEmail(email.getTo()));
        message.setSubject(email.getSubject());
        message.setContent(email.getMessage());
        message.setMsgDate(new Date());
        message.setMsgStatus(InboxMessageStatus.UNREAD);
        inboxMessageRepos.save(message);

        // Initialise process variables
        Map<String, Object> processParams = new HashMap<String, Object>();
        processParams.put("dossierComplet", false);
        processParams.put("email", email);

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
