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
import com.gsr.myschool.server.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class RegisterProcessNestedServiceImpl implements RegisterProcessNestedService {
    @Autowired
    private UserRepos userRepos;
    @Autowired
    private EmailService emailService;
    @Autowired
    private InboxMessageRepos inboxMessageRepos;

    @Override
    public void deleteAccount(Long id) {
        userRepos.delete(id);
    }

    @Override
    public EmailDTO getRelanceMail(Long id, String link, EmailDTO email) throws Exception {
        User user = userRepos.findOne(id);
        Map<String, String> params = new HashMap<String, String>();
        params.put("lastname", user.getLastName());
        params.put("firstname", user.getFirstName());
        params.put("link", link);

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

        InboxMessage message = new InboxMessage();
        message.setParentUser(user);
        message.setSubject(email.getSubject());
        message.setContent(email.getMessage());
        message.setMsgDate(new Date());
        message.setMsgStatus(InboxMessageStatus.UNREAD);
        inboxMessageRepos.save(message);

        return email;
    }
}
