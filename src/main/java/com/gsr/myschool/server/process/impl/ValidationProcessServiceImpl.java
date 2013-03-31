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
import com.gsr.myschool.server.service.EmailPreparatorService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private EmailPreparatorService emailService;
    @Autowired
    private InboxMessageRepos inboxMessageRepos;
    @Value("${mailserver.sender}")
    private String sender;

    @Override
    public void startProcess(Dossier dossier) {
        // treatment on Dossier
        dossier.setSubmitDate(new Date());
        dossier.setStatus(DossierStatus.SUBMITTED);
        dossierRepos.save(dossier);

        // Initialise process variables
        Map<String, Object> processParams = new HashMap<String, Object>();
        processParams.put("dueDate", "");

        // start process by key
        runtimeService.startProcessInstanceByKey("validation", dossier.getId().toString(), processParams);
    }

    @Override
    public Task getDossierToReceive(Long dossierId) {
        return taskService.createTaskQuery()
                .processInstanceBusinessKey(dossierId.toString())
                .taskDefinitionKey(ValidationTask.RECEPTION.getValue())
                .singleResult();
    }

    @Override
    public void receiveDossier(Task task, Dossier dossier) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("gender", dossier.getOwner().getGender().toString());
        params.put("lastname", dossier.getOwner().getLastName());
        params.put("nomEnfant", dossier.getCandidat().getLastname());
        params.put("prenomEnfant", dossier.getCandidat().getFirstname());
        params.put("firstname", dossier.getOwner().getFirstName());
        params.put("refdossier", dossier.getGeneratedNumDossier());

        try {
            EmailDTO email = emailService.populateEmail(EmailType.PIECES_RECEIVED,
                    dossier.getOwner().getEmail(), sender, params, "", "");
            emailService.prepare(email);

            InboxMessage message = new InboxMessage();
            message.setParentUser(dossier.getOwner());
            message.setSubject(email.getSubject());
            message.setContent(email.getMessage());
            message.setMsgDate(new Date());
            message.setMsgStatus(InboxMessageStatus.UNREAD);
            inboxMessageRepos.save(message);
        } catch (Exception e) {
            e.printStackTrace();
        }

        taskService.complete(task.getId());
    }

    @Override
    public void receiveDossier(Task task, Dossier dossier, List<PiecejustifDTO> piecejustifDTOs) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("gender", dossier.getOwner().getGender().toString());
        params.put("lastname", dossier.getOwner().getLastName());
        params.put("nomEnfant", dossier.getCandidat().getLastname());
        params.put("prenomEnfant", dossier.getCandidat().getFirstname());
        params.put("firstname", dossier.getOwner().getFirstName());
        params.put("refdossier", dossier.getGeneratedNumDossier());

        try {
            EmailDTO email = emailService.populateEmail(EmailType.DOSSIER_RECEIVED,
                    dossier.getOwner().getEmail(), sender, params, "", "");
            emailService.prepare(email);

            InboxMessage message = new InboxMessage();
            message.setParentUser(dossier.getOwner());
            message.setSubject(email.getSubject());
            message.setContent(email.getMessage());
            message.setMsgDate(new Date());
            message.setMsgStatus(InboxMessageStatus.UNREAD);
            inboxMessageRepos.save(message);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Initialise process variables
        Map<String, Object> processParams = new HashMap<String, Object>();
        processParams.put("piecejustifs", piecejustifDTOs);

        taskService.complete(task.getId(), processParams);
    }

    @Override
    public Task getDossierToValidate(Long dossierId) {
        return taskService.createTaskQuery()
                .processInstanceBusinessKey(dossierId.toString())
                .taskDefinitionKey(ValidationTask.VALIDATION.getValue())
                .singleResult();
    }

    @Override
    public List<PiecejustifDTO> getPieceJustifFromProcess(Long dossierId) {
        Task task = taskService.createTaskQuery()
                .processInstanceBusinessKey(dossierId.toString())
                .taskDefinitionKey(ValidationTask.VALIDATION.getValue())
                .singleResult();
        return (List<PiecejustifDTO>) runtimeService.getVariable(task.getExecutionId(), "piecejustifs");
    }

    @Override
    public void rejectDossier(Task task, Dossier dossier, List<PiecejustifDTO> pieceNonavailable) {
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
            EmailDTO email = emailService.populateEmail(EmailType.DOSSIER_INCOMPLETE,
                    dossier.getOwner().getEmail(), sender,
                    params, "", "");
            emailService.prepare(email);

            InboxMessage message = new InboxMessage();
            message.setParentUser(dossier.getOwner());
            message.setSubject(email.getSubject());
            message.setContent(email.getMessage());
            message.setMsgDate(new Date());
            message.setMsgStatus(InboxMessageStatus.UNREAD);
            inboxMessageRepos.save(message);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Initialise process variables
        Map<String, Object> processParams = new HashMap<String, Object>();
        processParams.put("dossierComplet", false);
        processParams.put("piecejustifs", pieceNonavailable);

        taskService.complete(task.getId(), processParams);
    }

    @Override
    public void acceptDossier(Task task, Dossier dossier) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("gender", dossier.getOwner().getGender().toString());
        params.put("lastname", dossier.getOwner().getLastName());
        params.put("firstname", dossier.getOwner().getFirstName());
        params.put("nomEnfant", dossier.getCandidat().getLastname());
        params.put("prenomEnfant", dossier.getCandidat().getFirstname());
        params.put("refdossier", dossier.getGeneratedNumDossier());
        try {
            EmailDTO email = emailService.populateEmail(EmailType.DOSSIER_COMPLETE,
                    dossier.getOwner().getEmail(), sender,
                    params, "", "");
            emailService.prepare(email);

            InboxMessage message = new InboxMessage();
            message.setParentUser(dossier.getOwner());
            message.setSubject(email.getSubject());
            message.setContent(email.getMessage());
            message.setMsgDate(new Date());
            message.setMsgStatus(InboxMessageStatus.UNREAD);
            inboxMessageRepos.save(message);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Initialise process variables
        Map<String, Object> processParams = new HashMap<String, Object>();
        processParams.put("dossierComplet", true);
        taskService.complete(task.getId(), processParams);
    }

    @Override
    public Task getDossierToAnalyse(Long dossierId) {
        return taskService.createTaskQuery()
                .processInstanceBusinessKey(dossierId.toString())
                .taskDefinitionKey(ValidationTask.ANALYSE.getValue())
                .singleResult();
    }

    @Override
    public Task getDossierToReAccept(Long dossierId) {
        return taskService.createTaskQuery()
                .processInstanceBusinessKey(dossierId.toString())
                .taskDefinitionKey(ValidationTask.CHANGER_STATE.getValue())
                .singleResult();
    }

    @Override
    public void rejectAnalysedDossier(Task task, Dossier dossier) {
        // Initialise process variables
        Map<String, Object> processParams = new HashMap<String, Object>();
        processParams.put("accepte", false);
        taskService.complete(task.getId(), processParams);
    }

    @Override
    public void acceptAnalysedDossier(Task task, Dossier dossier) {
        // Initialise process variables
        Map<String, Object> processParams = new HashMap<String, Object>();
        processParams.put("accepte", true);
        taskService.complete(task.getId(), processParams);
    }

    @Override
    public List<Dossier> getAllAnalysedDossiers() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
