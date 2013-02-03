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

package com.gsr.myschool.server.service.impl;

import com.gsr.myschool.common.shared.type.InboxMessageStatus;
import com.gsr.myschool.server.business.InboxMessage;
import com.gsr.myschool.server.repos.InboxMessageRepos;
import com.gsr.myschool.server.service.InboxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InboxServiceImpl implements InboxService {
    @Autowired
    private InboxMessageRepos inboxMessageRepos;

    @Override
    public void updateInboxMessages(List<InboxMessage> messages) {
        inboxMessageRepos.save(messages);
    }

    @Override
    public void updateInboxMessages(List<InboxMessage> messages, InboxMessageStatus status) {
        for (InboxMessage msg : messages) {
            msg.setMsgStatus(status);
        }
        inboxMessageRepos.save(messages);
    }

    @Override
    public void updateInboxMessage(InboxMessage message) {
        inboxMessageRepos.save(message);
    }

    @Override
    public void deleteInboxMessages(List<InboxMessage> messages) {
        inboxMessageRepos.delete(messages);
    }

    @Override
    public List<InboxMessage> findAllInboxMessage(Long userId) {
        return inboxMessageRepos.findByParentUser_id(userId);
    }

    @Override
    public Integer countAllUnreadInboxMessages(Long userId) {
        return inboxMessageRepos.findByParentUser_idAndMsgStatus(userId,
                InboxMessageStatus.UNREAD).size();
    }
}
