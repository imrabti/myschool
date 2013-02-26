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
import com.gsr.myschool.common.shared.type.UserStatus;
import com.gsr.myschool.server.business.User;
import com.gsr.myschool.server.process.RegisterProcessService;
import com.gsr.myschool.server.repos.UserRepos;
import com.gsr.myschool.server.service.EmailService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class RegisterProcessServiceImpl implements RegisterProcessService {
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
    public void register(User user) throws Exception {
        String token = Base64.encode((new Date()).toString());

        Map<String, String> params = new HashMap<String, String>();
        params.put("gender", user.getGender().toString());
        params.put("lastname", user.getLastName());
        params.put("firstname", user.getFirstName());
        params.put("link", "mylink/?token=" + token);

        EmailDTO email = emailService.populateEmail(EmailType.REGISTRATION, user.getEmail(), sender, params, "", "");

        Map<String, Object> processParams = new HashMap<String, Object>();
        processParams.put("token", token);
        processParams.put("email", email);
        processParams.put("userId", user.getId());

        runtimeService.startProcessInstanceByKey("register", processParams);
    }

    @Override
    public void register(User user, String link) throws Exception {
        String token = Base64.encode((new Date()).toString());
        token = token.replace("=", "E");
        link = link + ";activate=" + token;

        Map<String, String> params = new HashMap<String, String>();
        params.put("gender", user.getGender().toString());
        params.put("lastname", user.getLastName());
        params.put("firstname", user.getFirstName());
        params.put("link", link);

        EmailDTO email = emailService.populateEmail(EmailType.REGISTRATION, user.getEmail(), sender, params, "", "");

        Map<String, Object> processParams = new HashMap<String, Object>();
        processParams.put("token", token);
        processParams.put("email", email);
        processParams.put("link", link);
        processParams.put("userId", user.getId());

        runtimeService.startProcessInstanceByKey("register", processParams);
    }

    @Override
    public void activateAccount(String token) throws Exception {
        Task task = taskService.createTaskQuery().taskAssignee(token).singleResult();
        if (task == null) throw new Exception();

        Long userId = (Long) runtimeService.getVariable(task.getExecutionId(), "userId");
        User user = userRepos.findOne(userId);
        user.setStatus(UserStatus.ACTIVE);
        userRepos.save(user);

        taskService.complete(task.getId());
    }

    @Override
    public void mailNotReceived(User user) throws Exception {
        Execution execution = runtimeService.createExecutionQuery()
                .signalEventSubscriptionName("mailNotReceived")
                .processVariableValueEquals("userId", user.getId())
                .singleResult();

        if (execution == null) throw new Exception();
        runtimeService.signalEventReceived("mailNotReceived", execution.getId());
    }
}
