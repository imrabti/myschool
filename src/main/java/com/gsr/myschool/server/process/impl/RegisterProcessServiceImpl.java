package com.gsr.myschool.server.process.impl;

import com.gsr.myschool.common.client.util.Base64;
import com.gsr.myschool.common.shared.dto.EmailDTO;
import com.gsr.myschool.common.shared.type.EmailType;
import com.gsr.myschool.server.business.User;
import com.gsr.myschool.server.process.RegisterProcessService;
import com.gsr.myschool.server.repos.UserRepos;
import com.gsr.myschool.server.service.EmailService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public void register(User user) throws Exception {
        String token = Base64.encode((new Date()).toString());

        Map<String, String> params = new HashMap<String, String>();
        params.put("lastname", user.getLastName());
        params.put("firstname", user.getFirstName());
        params.put("link", "mylink/?token=" + token);

        EmailDTO email = emailService.populateEmail(EmailType.REGISTRATION, user.getEmail(), "todefine@myschool.com", params, "", "");

        Map<String, Object> processParams = new HashMap<String, Object>();
        processParams.put("token", token);
        processParams.put("email", email);
        processParams.put("userId", user.getId());

        runtimeService.startProcessInstanceByKey("register", processParams);
    }

    @Override
    public void register(User user, String link) throws Exception {
        String token = Base64.encode((new Date()).toString());

        Map<String, String> params = new HashMap<String, String>();
        params.put("lastname", user.getLastName());
        params.put("firstname", user.getFirstName());
        params.put("link", link+"?token=" + token);

        EmailDTO email = emailService.populateEmail(EmailType.REGISTRATION, user.getEmail(), "todefine@myschool.com", params, "", "");

        Map<String, Object> processParams = new HashMap<String, Object>();
        processParams.put("token", token);
        processParams.put("email", email);
        processParams.put("userId", user.getId());

        runtimeService.startProcessInstanceByKey("register", processParams);
    }

    @Override
    public void activateAccount(String token) throws Exception {
        Task task = taskService.createTaskQuery().taskAssignee(token).singleResult();
        if (task == null) throw new Exception();

        Long userId = (Long) runtimeService.getVariable(task.getExecutionId(), "userId");
        User user = userRepos.findOne(userId);
        user.setActive(true);
        userRepos.save(user);

        taskService.complete(task.getId());
    }

    @Override
    public void deleteAccount(Long id) {
        userRepos.delete(id);
    }

    @Override
    public EmailDTO getRelanceMail(Long id, String token, EmailDTO email) throws Exception {
        User user = userRepos.findOne(id);
        Map<String, String> params = new HashMap<String, String>();
        params.put("lastname", user.getLastName());
        params.put("firstname", user.getFirstName());
        params.put("link", "mylink/?token=" + token);

        email = emailService.populateEmail(EmailType.RELANCE, email.getTo(), email.getFrom(), params, "", "");
        return email;
    }

    @Override
    public EmailDTO getActivationMail(Long id, EmailDTO email) throws Exception {
        User user = userRepos.findOne(id);
        Map<String, String> params = new HashMap<String, String>();
        params.put("lastname", user.getLastName());
        params.put("firstname", user.getFirstName());

        email = emailService.populateEmail(EmailType.ACTIVATION, email.getTo(), email.getFrom(), params, "", "");
        return email;
    }
}
