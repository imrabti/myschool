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

import com.gsr.myschool.common.shared.dto.EmailDTO;
import com.gsr.myschool.common.shared.type.EmailType;
import com.gsr.myschool.common.shared.type.InboxMessageStatus;
import com.gsr.myschool.server.business.InboxMessage;
import com.gsr.myschool.server.business.User;
import com.gsr.myschool.server.process.RegisterProcessNestedService;
import com.gsr.myschool.server.repos.InboxMessageRepos;
import com.gsr.myschool.server.repos.UserRepos;
import com.gsr.myschool.server.service.EmailPreparatorService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class RegisterProcessNestedServiceImpl implements RegisterProcessNestedService {
    private static Log log = LogFactory.getLog(RegisterProcessNestedServiceImpl.class);

    @Autowired
    private UserRepos userRepos;
    @Autowired
    private EmailPreparatorService emailService;
    @Autowired
    private InboxMessageRepos inboxMessageRepos;

    @Override
    public void deleteAccount(Long id) {
        userRepos.delete(id);
    }

    @Override
    public EmailDTO getRelanceMail(Long id, String link, EmailDTO email) throws Exception {

        User user = userRepos.findOne(id);
        if (user != null) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("gender", user.getGender().toString());
            params.put("lastname", user.getLastName());
            params.put("firstname", user.getFirstName());
            params.put("link", link);
            email = emailService.populateEmail(EmailType.RELANCE, user.getEmail(), email.getFrom(), params, "", "");
        } else {
            log.debug("No user with ID " + id + " Found");
        }

        return email;

    }

    @Override
    public void sendRelanceMail(Long id, String link, EmailDTO email) {
        User user = userRepos.findOne(id);
        if (user != null) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("gender", user.getGender().toString());
            params.put("lastname", user.getLastName());
            params.put("firstname", user.getFirstName());
            params.put("link", link);
            try {
                email = emailService.populateEmail(EmailType.RELANCE, user.getEmail(), email.getFrom(), params, "", "");
                emailService.prepare(email);
            } catch (Exception e) {
                log.error("Error sending email relance for user " + id);
            }
        } else {
            log.debug("No user with ID " + id + " Found");
        }
    }

    @Override
    public EmailDTO getActivationMail(Long id, EmailDTO email) throws Exception {
        User user = userRepos.findOne(id);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("gender", user.getGender().toString());
        params.put("lastname", user.getLastName());
        params.put("firstname", user.getFirstName());

        email = emailService.populateEmail(EmailType.ACTIVATION, user.getEmail(), email.getFrom(), params, "", "");

        InboxMessage message = new InboxMessage();
        message.setParentUser(user);
        message.setSubject(email.getSubject());
        message.setContent(email.getMessage());
        message.setMsgDate(new Date());
        message.setMsgStatus(InboxMessageStatus.UNREAD);
        inboxMessageRepos.save(message);

        return email;
    }

    @Override
    public void sendActivationMail(Long id, EmailDTO email) {
        User user = userRepos.findOne(id);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("gender", user.getGender().toString());
        params.put("lastname", user.getLastName());
        params.put("firstname", user.getFirstName());

        try {
            email = emailService.populateEmail(EmailType.ACTIVATION, user.getEmail(), email.getFrom(), params, "", "");
            emailService.prepare(email);

            InboxMessage message = new InboxMessage();
            message.setParentUser(user);
            message.setSubject(email.getSubject());
            message.setContent(email.getMessage());
            message.setMsgDate(new Date());
            message.setMsgStatus(InboxMessageStatus.UNREAD);
            inboxMessageRepos.save(message);
        } catch (Exception e) {
            log.error("Error sending email relance for user " + id);
        }
    }

    @Override
    public void sendActivationMailAgain(EmailDTO email) {
        try {
            emailService.prepare(email);
        } catch (Exception e) {
            log.error("Error sending email activation again");
        }
    }
}
