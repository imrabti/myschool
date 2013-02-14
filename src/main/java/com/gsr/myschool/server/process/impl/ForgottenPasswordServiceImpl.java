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

import com.gsr.myschool.common.client.util.Base64;
import com.gsr.myschool.common.shared.dto.EmailDTO;
import com.gsr.myschool.common.shared.type.EmailType;
import com.gsr.myschool.common.shared.type.Gender;
import com.gsr.myschool.server.business.User;
import com.gsr.myschool.server.process.ForgottenPasswordService;
import com.gsr.myschool.server.repos.UserRepos;
import com.gsr.myschool.server.service.EmailService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ForgottenPasswordServiceImpl implements ForgottenPasswordService {
    @Autowired
    private UserRepos userRepos;
    @Autowired
    private EmailService emailService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Value("${mailserver.sender}")
    private String sender;

    @Override
    public void startProcessWithValidEmail(String email, String link) throws Exception {
        String token = Base64.encode(Long.toString((new Date()).getTime()));
        token = token.replace("=", "E");

        User user = userRepos.findByEmail(email);

        Map<String, String> params = new HashMap<String, String>();
        params.put("gender", user.getGender() == Gender.FEMALE ? "Madame" : "Monsieur");
        params.put("firstname",user.getFirstName());
        params.put("lastname",user.getLastName());
        params.put("email", email);
        params.put("link", link+ ";token=" +token);

        EmailDTO emailDTO = emailService.populateEmail(EmailType.FORGOTTEN_PASSWORD,
                email, sender, params, "", "");

        Map<String, Object> processParams = new HashMap<String, Object>();
        processParams.put("token", token);
        processParams.put("email", emailDTO);

        runtimeService.startProcessInstanceByKey("forgottenPassword", processParams);
    }

    @Override
    public void startProcess(String email) {
        runtimeService.startProcessInstanceByKey("forgottenPassword");
    }

    @Override
    public Map<String, String> getTaskAndUserId(String token) throws Exception {
        Task task = taskService.createTaskQuery().taskAssignee(token).singleResult();
        if (task == null) throw new Exception();

        EmailDTO email = (EmailDTO) runtimeService.getVariable(task.getExecutionId(), "email");

        Map<String, String> result = new HashMap<String, String>();
        result.put(email.getTo(), task.getId());

        return result;
    }

    @Override
    public void completeTask(String taskId) {
        taskService.complete(taskId);
    }
}
