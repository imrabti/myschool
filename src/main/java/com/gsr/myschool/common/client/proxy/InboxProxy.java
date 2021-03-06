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

package com.gsr.myschool.common.client.proxy;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import com.gsr.myschool.common.shared.type.InboxMessageStatus;
import com.gsr.myschool.server.business.InboxMessage;

import java.util.Date;

@ProxyFor(InboxMessage.class)
public interface InboxProxy extends ValueProxy {
    Long getId();

    void setId(Long newId);

    UserProxy getParentUser();

    void setParentUser(UserProxy newUser);

    String getSubject();

    void setSubject(String newSubject);

    String getContent();

    void setContent(String newContent);

    Date getMsgDate();

    void setMsgDate(Date newMsgDate);

    InboxMessageStatus getMsgStatus();

    void setMsgStatus(InboxMessageStatus newMsgStatus);

    String getRawContent();

    void setRawContent(String rawContent);
}
