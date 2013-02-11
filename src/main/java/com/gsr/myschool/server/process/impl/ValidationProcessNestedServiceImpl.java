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
import com.gsr.myschool.server.business.Dossier;
import com.gsr.myschool.server.business.InboxMessage;
import com.gsr.myschool.server.process.ValidationProcessNestedService;
import com.gsr.myschool.server.repos.DossierRepos;
import com.gsr.myschool.server.repos.InboxMessageRepos;
import com.gsr.myschool.server.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ValidationProcessNestedServiceImpl implements ValidationProcessNestedService {
    @Autowired
    private EmailService emailService;
    @Autowired
    private InboxMessageRepos inboxMessageRepos;
    @Value("${mailserver.sender}")
    private String sender;

    @Override
    public EmailDTO getReceivedDossierMail(Dossier dossier) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("lastname", dossier.getOwner().getLastName());
        params.put("firstname", dossier.getOwner().getFirstName());
        params.put("refdossier", dossier.getGeneratedNumDossier());

        EmailDTO email = emailService.populateEmail(EmailType.DOSSIER_RECEIVED,
                dossier.getOwner().getEmail(), sender, params, "", "");

        InboxMessage message = new InboxMessage();
        message.setParentUser(dossier.getOwner());
        message.setSubject(email.getSubject());
        message.setContent(email.getMessage());
        message.setMsgDate(new Date());
        message.setMsgStatus(InboxMessageStatus.UNREAD);
        inboxMessageRepos.save(message);

        return email;
    }
}
