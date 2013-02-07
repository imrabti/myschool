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

package com.gsr.myschool.server.service;

import com.gsr.myschool.common.shared.type.InboxMessageStatus;
import com.gsr.myschool.server.business.InboxMessage;

import java.util.List;
import java.util.Set;

public interface InboxService {
    void updateInboxMessages(List<InboxMessage> messages);

    void updateInboxMessages(List<InboxMessage> messages, InboxMessageStatus status);

    void updateInboxMessage(InboxMessage message);

    void deleteInboxMessages(List<InboxMessage> messages);

    List<InboxMessage> findAllInboxMessage(Long userId);

    Integer countAllUnreadInboxMessages(Long userId);
}
